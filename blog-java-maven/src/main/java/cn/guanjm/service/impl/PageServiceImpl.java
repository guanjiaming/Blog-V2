package cn.guanjm.service.impl;

import cn.guanjm.common.enums.ExceptionEnum;
import cn.guanjm.common.exception.UmException;
import cn.guanjm.dao.ArticleDao;
import cn.guanjm.dao.TagDao;
import cn.guanjm.entity.Article;
import cn.guanjm.entity.Tag;
import cn.guanjm.service.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public Map<String, Object> loadModel(Long id) {
        Map<String, Object> model = new HashMap<>();

        Article article = articleDao.queryArticleDetail(id);

        if(article == null) {
            throw new UmException(ExceptionEnum.ARTICLE_NOT_FOUND);
        }

        List<Long> tagIds = articleDao.getTagIdsByAid(id);

        List<Tag> tagList = new ArrayList<>();

        for (Long tid : tagIds) {
            Tag tag = tagDao.queryTagById(tid);
            if(tag != null) {
                tagList.add(tag);
            }
        }

        model.put("id", article.getId());
        model.put("title", article.getTitle());
        model.put("tags", tagList);
        model.put("time", article.getCreateTime());
        model.put("content", article.getContent());
        model.put("views", article.getViews());

        return model;
    }

    @Override
    public void createHtml(Long id){
        // 获取上下文
        Context context = new Context();
        // 把数据加载到上下文
        context.setVariables(loadModel(id));

//        File dest = new File("/Users/guanjiaming/Documents/project/blog2.0/blog-front-detail/", id + ".html");
        File dest = new File("/usr/local/webserver/nginx/html/blog/details/", id + ".html");

        if(dest.exists()) {
            dest.delete();
        }

        try(PrintWriter writer = new PrintWriter(dest, "UTF-8")) {
            templateEngine.process("detail", context, writer);
        } catch (Exception e){
            log.error("生成静态页异常");
        }
    }

    @Override
    public void deleteHtml(Long id){
//        File dest = new File("/Users/guanjiaming/Documents/project/blog2.0/blog-front-detail/", id + ".html");
        File dest = new File("/usr/local/webserver/nginx/html/blog/details", id + ".html");
        if(dest.exists()) {
            dest.delete();
        }
    }
}
