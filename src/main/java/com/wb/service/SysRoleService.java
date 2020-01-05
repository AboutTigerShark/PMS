package com.wb.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.wb.common.RequestHolder;
import com.wb.dao.SysRoleMapper;
import com.wb.exception.ParamException;
import com.wb.model.SysRole;
import com.wb.param.RoleParam;
import com.wb.util.BeanValidator;
import com.wb.util.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    public void save(RoleParam roleParam){
        BeanValidator.check(roleParam);
        if (checkExist(roleParam.getName(), roleParam.getId())){
            throw new ParamException("存在相同角色名称");
        }
        SysRole role = SysRole.builder()
                .name(roleParam.getName())
                .status(roleParam.getStatus())
                .type(roleParam.getType())
                .remark(roleParam.getRemark())
                .build();
        role.setOperator(RequestHolder.getCurrentUser().getUsername());
        role.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        role.setOperateTime(new Date());
        sysRoleMapper.insertSelective(role);
//        sysLogService.saveRoleLog(null, role);
    }

    public void update(RoleParam roleParam){
        BeanValidator.check(roleParam);
        if (checkExist(roleParam.getName(), roleParam.getId())){
            throw new ParamException("存在相同角色名称");
        }
        SysRole before_update = sysRoleMapper.selectByPrimaryKey(roleParam.getId());
        Preconditions.checkNotNull(before_update, "待更新的角色不存在");

        SysRole after_update = SysRole.builder()
                .id(roleParam.getId())
                .name(roleParam.getName())
                .status(roleParam.getStatus())
                .type(roleParam.getType())
                .remark(roleParam.getRemark())
                .build();
        after_update.setOperator(RequestHolder.getCurrentUser().getUsername());
        after_update.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after_update.setOperateTime(new Date());
        sysRoleMapper.updateByPrimaryKeySelective(after_update);
//        sysLogService.saveRoleLog(before, after);
    }

    private boolean checkExist(String name, Integer id){
        return sysRoleMapper.countByName(name, id) > 0;
    }

    //获得所有角色
    public List<SysRole> getAll() {
        return sysRoleMapper.getAll();
    }

//    public List<SysRole> getRoleListByUserId(int userId) {
//        List<Integer> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
//        if (CollectionUtils.isEmpty(roleIdList)) {
//            return Lists.newArrayList();
//        }
//        return sysRoleMapper.getByIdList(roleIdList);
//    }

//    public List<SysRole> getRoleListByAclId(int aclId) {
//        List<Integer> roleIdList = sysRoleAclMapper.getRoleIdListByAclId(aclId);
//        if (CollectionUtils.isEmpty(roleIdList)) {
//            return Lists.newArrayList();
//        }
//        return sysRoleMapper.getByIdList(roleIdList);
//    }

}
