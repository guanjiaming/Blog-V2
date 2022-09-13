package cn.guanjm.web;

import cn.guanjm.common.vo.PageResult;
import cn.guanjm.common.vo.ResponseResult;
import cn.guanjm.entity.Tag;
import cn.guanjm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("apis")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("tags")
    public ResponseEntity<PageResult<Tag>> queryTagList() {
        return ResponseEntity.ok(tagService.queryTagList());
    }

    /**
     * 查询标签 通过标签名
     * @param name 标签名
     * @return 标签对象
     */
    @GetMapping("tag")
    public ResponseEntity<ResponseResult<Tag>> queryTagByName(@RequestParam("name") String name){
        Tag tag = tagService.queryTagByName(name);
        ResponseResult<Tag> responseResult = new ResponseResult<>(tag);
        return ResponseEntity.ok(responseResult);
    }

    /**
     * 新增标签
     * @param tag 新增的标签名
     */
    @PostMapping("tag")
    public ResponseEntity<Void> saveTag(@RequestBody() Tag tag) {
        tagService.saveTag(tag.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改标签
     * @return
     */
    @PutMapping("tag")
    public ResponseEntity<Void> updateTag(@RequestBody() Tag tag) {
        tagService.updateTag(tag);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除标签
     * @param id 要删除标签的id
     * @return
     */
    @DeleteMapping("tag/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id") Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
