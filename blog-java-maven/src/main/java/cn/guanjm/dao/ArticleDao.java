package cn.guanjm.dao;

import cn.guanjm.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleDao {
    List<Article> queryArticleList(@Param("page") Integer page,
                                   @Param("rows") Integer rows,
                                   @Param("sortBy") String sortBy,
                                   @Param("desc") Boolean desc,
                                   @Param("keywords") String keywords);

    int saveArticle(Article article);

    int updateArticle(Article edit);

    // 删除文章
    int deleteArticle(@Param("id") Long id);

    // 通过id查询文章
    Article queryArticleDetail(@Param("id") Long id);

    // 查询当前文章关联的tags
    List<Long> getTagIdsByAid(@Param("id") Long id);

    // 新增当前文章关联的tags
    int insertArticleIdAndTagId(@Param("tid") Long tid, @Param("aid") Long aid);

    // 删除关联的tag
    int deleteTagIdsByArticleId(@Param("articleId") Long articleId);

    int updateViews(@Param("id") Long id);
}
