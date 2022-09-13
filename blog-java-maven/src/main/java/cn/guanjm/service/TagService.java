package cn.guanjm.service;

import cn.guanjm.common.vo.PageResult;
import cn.guanjm.entity.Tag;


public interface TagService {
    PageResult<Tag> queryTagList();

    void saveTag(String name);

    Tag queryTagByName(String name);

    void updateTag(Tag tag);

    void deleteTag(Long id);
}
