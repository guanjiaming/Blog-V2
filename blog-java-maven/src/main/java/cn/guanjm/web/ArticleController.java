package cn.guanjm.web;

import cn.guanjm.common.vo.PageResult;
import cn.guanjm.entity.Article;
import cn.guanjm.vo.ArticleVo;

import cn.guanjm.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/apis")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    /**
     * 查询 及 搜索博客列表
     * @param page 第几页
     * @param rows 每页大小
     * @param sortBy 排序字段
     * @param desc 排序类型
     * @param keywords title搜索关键字
     * @return 文章列表
     */
    @GetMapping("/queryArticleList")
    public ResponseEntity<PageResult<ArticleVo>> queryArticleList(
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "rows", defaultValue = "8") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", required = false) Boolean desc,
            @RequestParam(value = "keywords", required = false) String keywords
    ) {
        return ResponseEntity.ok(articleService.queryArticleList(page, rows, sortBy, desc, keywords));
    }

    @GetMapping("/article/detail/{id}")
    public ResponseEntity<ArticleVo> queryArticleDetail(@PathVariable("id") Long id) {
        return ResponseEntity.ok(articleService.queryArticleDetail(id));
    }

    /**
     * 保存文章
     * @return response
     */
    @PostMapping("/article/save")
    public ResponseEntity<Void> saveArticle(@RequestBody() ArticleVo article) {
        articleService.saveArticle(article.getTitle(), article.getContent(), article.getTagIds(), article.getIsRecommend(), article.getIsPublish());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 编辑文章
     * @return response
     */
    @PutMapping("/article/edit")
    public ResponseEntity<Void> editArticle(@RequestBody() ArticleVo article) {
        articleService.editArticle(article);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @DeleteMapping("/article/delete/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id")Long id){
        articleService.deleteArticle(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/article/addviews/{id}")
    public ResponseEntity<Void> updateViews(@PathVariable("id")Long id) {
        articleService.updateViews(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
