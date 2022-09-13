package cn.guanjm.dao;

import cn.guanjm.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TagDao {
    List<Tag> queryTagList();

    int saveTag(Tag tag);

    Tag queryTagByName(@Param("name")String name);

    int updateTag(Tag tag);

    void deleteTag(Long id);

    Tag queryTagById(Long id);
}
