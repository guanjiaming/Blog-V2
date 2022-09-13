package cn.guanjm.service.impl;

import cn.guanjm.common.enums.ExceptionEnum;
import cn.guanjm.common.exception.UmException;
import cn.guanjm.common.vo.PageResult;
import cn.guanjm.dao.ArticleDao;
import cn.guanjm.entity.Article;
import cn.guanjm.service.ArticleService;
import cn.guanjm.vo.ArticleVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 查询 及 搜索博客 列表
     * @param page 第几页
     * @param rows 每页大小
     * @param sortBy 排序字段
     * @param desc 排序类型
     * @param keywords title搜索关键字
     * @return 文章列表
     */
    @Override
    public PageResult<ArticleVo> queryArticleList(Integer page, Integer rows, String sortBy, Boolean desc, String keywords) {

        PageHelper.startPage(page, rows);
        List<Article> articles = articleDao.queryArticleList(page, rows, sortBy, desc, keywords);

        if(CollectionUtils.isEmpty(articles)) {
            throw new UmException(ExceptionEnum.ARTICLE_NOT_FOUND);
        }

        List<ArticleVo> articleVos = new ArrayList<>();

        for (Article article: articles) {
            List<Long> tagIds = articleDao.getTagIdsByAid(article.getId());

            ArticleVo articleVo = articleToArticleVo(article);
            articleVo.setTagIds(tagIds);
            articleVos.add(articleVo);
        }

        PageInfo<ArticleVo> pageInfo = new PageInfo<>(articleVos);
        return new PageResult<>(pageInfo.getTotal(),articleVos);
    }

    @Override
    public ArticleVo queryArticleDetail(Long id){
        Article article = articleDao.queryArticleDetail(id);
        if(article == null) {
            throw new UmException(ExceptionEnum.ARTICLE_NOT_FOUND);
        }

        // 查询当前文章关联的tags
        List<Long> tagIds = articleDao.getTagIdsByAid(article.getId());
        ArticleVo articleVo = articleToArticleVo(article);
        articleVo.setTagIds(tagIds);
        return articleVo;
    }

    /**
     * 保存文章以及中间表
     * @param title 文章标题
     * @param content
     * @param tagIds
     * @param isRecommend
     * @param isPublish
     */
    @Transactional
    @Override
    public void saveArticle(String title, String content, List<Long> tagIds, Boolean isRecommend, Boolean isPublish) {
        Article article = new Article();
        article.setViews(0);
        // todo 拿userid，存进去真正的id
        article.setUserId(88L);
        article.setTitle(title);
        article.setContent(content);
        article.setIsPublish(isPublish);
        article.setIsRecommend(isRecommend);

        int i = articleDao.saveArticle(article);
        if(i != 1) {
            throw new UmException(ExceptionEnum.ARTICLE_SAVE_ERROR);
        }
        // 插入中间表
        for (Long tid : tagIds) {
            i = articleDao.insertArticleIdAndTagId(tid, article.getId());
            if(i != 1) {
                throw new UmException(ExceptionEnum.ARTICLE_SAVE_ERROR);
            }
        }

        // rabbitMq发送消息
        amqpTemplate.convertAndSend("item.insert", article.getId());
    }

    /**
     * 修改文章
     * @param article
     */
    @Transactional
    @Override
    public void editArticle(ArticleVo article) {
        // 先把数据库里文章的标签id都删除
        int count = articleDao.deleteTagIdsByArticleId(article.getId());
        if(count < 1) {
            throw new UmException(ExceptionEnum.ARTICLE_UPDATE_ERROR);
        }

        // 修改文章信息
        Article edit = new Article();
        edit.setId(article.getId());
        edit.setTitle(article.getTitle());
        edit.setContent(article.getContent());
        edit.setIsPublish(article.getIsPublish());
        edit.setIsRecommend(article.getIsRecommend());

        count = articleDao.updateArticle(edit);
        if(count != 1) {
            throw new UmException(ExceptionEnum.ARTICLE_UPDATE_ERROR);
        }

        List<Long> tagIds = article.getTagIds();
        // 插入中间表
        for (Long tid : tagIds) {
            count = articleDao.insertArticleIdAndTagId(tid, article.getId());

            if(count != 1) {
                throw new UmException(ExceptionEnum.ARTICLE_UPDATE_ERROR);
            }
        }

        // rabbitMq发送消息
        amqpTemplate.convertAndSend("item.update", article.getId());

    }


    /**
     * 删除文章
     * @param id
     */
    @Override
    public void deleteArticle(Long id) {
        // 先把数据库里文章的标签id都删除
        int count = articleDao.deleteTagIdsByArticleId(id);
        if(count < 1) {
            throw new UmException(ExceptionEnum.ARTICLE_UPDATE_ERROR);
        }

        // 删除文章
        count = articleDao.deleteArticle(id);
        if(count < 1) {
            throw new UmException(ExceptionEnum.ARTICLE_DELETE_ERROR);
        }

        // rabbitMq发送消息
        amqpTemplate.convertAndSend("item.delete", id);
    }

    @Override
    public void updateViews(Long id) {
        int count = articleDao.updateViews(id);
    }

    // article 和 articleVo转换
    public ArticleVo articleToArticleVo(Article article) {
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(article.getId());
        articleVo.setTitle(article.getTitle());
        articleVo.setContent(article.getContent());
        articleVo.setIsPublish(article.getIsPublish());
        articleVo.setViews(article.getViews());
        articleVo.setIsRecommend(article.getIsRecommend());
        articleVo.setCreateTime(article.getCreateTime());
        return articleVo;
    }
}
