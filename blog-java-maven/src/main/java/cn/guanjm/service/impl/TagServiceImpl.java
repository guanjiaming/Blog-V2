package cn.guanjm.service.impl;

import cn.guanjm.common.enums.ExceptionEnum;
import cn.guanjm.common.exception.UmException;
import cn.guanjm.common.vo.PageResult;
import cn.guanjm.dao.TagDao;
import cn.guanjm.entity.Tag;
import cn.guanjm.service.TagService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    /**
     * 查询tag列表 不分页
     * @return
     */
    @Override
    public PageResult<Tag> queryTagList() {
        List<Tag> tags = tagDao.queryTagList();

        if(CollectionUtils.isEmpty(tags)) {
            throw new UmException(ExceptionEnum.TAG_NOTFOUND);
        }

        PageInfo<Tag> pageInfo = new PageInfo<>(tags);
        return new PageResult<> (pageInfo.getTotal(), tags);
    }

    /**
     * 保存
     * @param name
     */
    @Override
    public void saveTag(String name) {
        Tag existTag = tagDao.queryTagByName(name);
        if(existTag != null) {
            throw new UmException(ExceptionEnum.TAG_NAME_EXIST);
        }
        Tag tag = new Tag();
        tag.setName(name);
        tag.setUserId(88L);
        tag.setCreateTime(new Date());
        tag.setUpdateTime(new Date());
        System.out.println(tag);
        int count = tagDao.saveTag(tag);
        if(count != 1) {
            throw new UmException(ExceptionEnum.TAG_INSERT_FAILED);
        }
    }

    /**
     * 查询tag
     * @param name
     * @return
     */
    @Override
    public Tag queryTagByName(String name) {
        Tag tag = tagDao.queryTagByName(name);
        if(tag == null) {
            throw new UmException(ExceptionEnum.TAG_NOTFOUND);
        }
        return tag;
    }

    /**
     * 修改tag名
     * @param oldTag
     */
    @Override
    public void updateTag(Tag oldTag) {
        Tag existTag = tagDao.queryTagByName(oldTag.getName());
        if(existTag != null) {
            throw new UmException(ExceptionEnum.TAG_NAME_EXIST);
        }

        Tag tag = new Tag();
        tag.setId(oldTag.getId());
        tag.setName(oldTag.getName());
        // 查询操作用户
        tag.setUserId(88L);
        int i = tagDao.updateTag(tag);
        if(i != 1) {
            throw new UmException(ExceptionEnum.TAG_UPDATE_FAILED);
        }
    }

    @Override
    public void deleteTag(Long id) {
        tagDao.deleteTag(id);
    }

}