package cn.guanjm.service;


import cn.guanjm.common.vo.PageResult;
import cn.guanjm.entity.Article;
import cn.guanjm.vo.ArticleVo;


import java.util.List;

public interface ArticleService {
    PageResult<ArticleVo> queryArticleList(Integer page, Integer rows, String sortBy, Boolean desc, String keywords);

    void saveArticle(String title, String content, List<Long> tagIds, Boolean isRecommend, Boolean isPublish);

    void editArticle(ArticleVo article);

    void deleteArticle(Long id);

    ArticleVo queryArticleDetail(Long id);

    void updateViews(Long id);
}
