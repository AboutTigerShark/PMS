package com.wb.service;

import com.google.common.base.Preconditions;
import com.wb.common.RequestHolder;
import com.wb.dao.SysAclModuleMapper;
import com.wb.exception.ParamException;
import com.wb.model.SysAclModule;
import com.wb.param.AclModuleParam;
import com.wb.util.BeanValidator;
import com.wb.util.IpUtil;
import com.wb.util.LevelUtil;

import javax.annotation.Resource;
import java.util.Date;

public class SysAclModuleService {

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    public void save(AclModuleParam aclModuleParam){
        BeanValidator.check(aclModuleParam);
        if(checkExist(aclModuleParam.getParentId(), aclModuleParam.getName(), aclModuleParam.getId())){
            throw new ParamException("同一蹭几下存在相同权限模块");
        }
        SysAclModule aclModule = SysAclModule.builder()
                .name(aclModuleParam.getName())
                .parentId(aclModuleParam.getParentId())
                .seq(aclModuleParam.getSeq())
                .status(aclModuleParam.getStatus())
                .remark(aclModuleParam.getRemark())
                .build();
        aclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
        aclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        aclModule.setOperateTime(new Date());
        sysAclModuleMapper.insertSelective(aclModule);
    }


    public void update(AclModuleParam aclModuleParam){
        BeanValidator.check(aclModuleParam);
        if (checkExist(aclModuleParam.getParentId(), aclModuleParam.getName(), aclModuleParam.getId())){
            throw new ParamException("同一层级下存在相同权限模块");
        }
        SysAclModule before_update = sysAclModuleMapper.selectByPrimaryKey(aclModuleParam.getId());
        Preconditions.checkNotNull(before_update, "待更新的权限模块不存在");

        SysAclModule after_update = SysAclModule.builder()
                .id(aclModuleParam.getId())
                .name(aclModuleParam.getName())
                .parentId(aclModuleParam.getParentId())
                .seq(aclModuleParam.getSeq())
                .status(aclModuleParam.getStatus())
                .remark(aclModuleParam.getRemark())
                .build();
        after_update.setLevel(LevelUtil.calculateLevel(getLevel(aclModuleParam.getParentId()), aclModuleParam.getParentId()));
        after_update.setOperator(RequestHolder.getCurrentUser().getUsername());
        after_update.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after_update.setOperateTime(new Date());
    }

    public boolean checkExist(Integer parentId, String aclModuleName, Integer Id){
        return sysAclModuleMapper.countByNameAndParentId(parentId, aclModuleName, Id) > 0;
    }

    private String getLevel(Integer aclModuleId) {
        SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        if (aclModule == null) {
            return null;
        }
        return aclModule.getLevel();
    }

//    public void delete(int aclModuleId) {
//        SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
//        Preconditions.checkNotNull(aclModule, "待删除的权限模块不存在，无法删除");
//        if(sysAclModuleMapper.countByParentId(aclModule.getId()) > 0) {
//            throw new ParamException("当前模块下面有子模块，无法删除");
//        }
//        if (sysAclMapper.countByAclModuleId(aclModule.getId()) > 0) {
//            throw new ParamException("当前模块下面有用户，无法删除");
//        }
//        sysAclModuleMapper.deleteByPrimaryKey(aclModuleId);
//    }
}
