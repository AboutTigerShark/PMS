package com.wb.service;

import com.google.common.base.Preconditions;
import com.wb.dao.SysUserMapper;
import com.wb.exception.ParamException;
import com.wb.model.SysUser;
import com.wb.param.UserParam;
import com.wb.util.BeanValidator;
import com.wb.util.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    public void save(UserParam userParam){
        BeanValidator.check(userParam);
        if(checkTelephoneExist(userParam.getTelephone(), userParam.getId())){
            throw new ParamException("电话已被占用");
        }
        if(checkEmailExist(userParam.getMail(), userParam.getId())){
            throw new ParamException("邮箱已被占用");
        }
//        String password = PassworldUtil.randomPassword(); //随机密码
        String password = "12345678";
        String encryptedPassword = MD5Util.encrypt(password);
        SysUser sysUser = SysUser.builder()
                .username(userParam.getUsername())
                .telephone(userParam.getTelephone())
                .mail(userParam.getMail())
                .password(encryptedPassword)
                .deptId(userParam.getDeptId())
                .status(userParam.getStatus())
                .remark(userParam.getRemark())
                .build();
        sysUser.setOperator("System"); //TODO
        sysUser.setOperateIp("127.0.0.1");
        sysUser.setOperateTime(new Date());
        sysUserMapper.insertSelective(sysUser);

    }

    public void update(UserParam userParam){
        BeanValidator.check(userParam);
        if(checkTelephoneExist(userParam.getTelephone(), userParam.getId())) {
            throw new ParamException("电话已被占用");
        }
        if(checkEmailExist(userParam.getMail(), userParam.getId())) {
            throw new ParamException("邮箱已被占用");
        }
        SysUser before_update = sysUserMapper.selectByPrimaryKey(userParam.getId()); //更新前
        Preconditions.checkNotNull(before_update, "不存在待更新的用户");
        SysUser after_update = SysUser.builder()
                .id(userParam.getId())
                .username(userParam.getUsername())
                .telephone(userParam.getTelephone())
                .mail(userParam.getMail())
                .deptId(userParam.getDeptId())
                .status(userParam.getStatus())
                .remark(userParam.getRemark())
                .build();
        after_update.setOperator("System"); //TODO
        after_update.setOperateIp("127.0.0.1");
        after_update.setOperateTime(new Date());
        sysUserMapper.updateByPrimaryKeySelective(after_update);
    }


    public boolean checkTelephoneExist(String telephone, Integer userId){
        return sysUserMapper.countByTelephone(telephone, userId) > 0;
    }

    public boolean checkEmailExist(String email, Integer userId){
        return sysUserMapper.countByMail(email, userId) > 0;
    }

    public SysUser findByKeyWord(String keyword){
        return sysUserMapper.findByKeyword(keyword);
    }

}
