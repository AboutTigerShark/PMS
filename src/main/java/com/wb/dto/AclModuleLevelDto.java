package com.wb.dto;

import com.google.common.collect.Lists;
import com.wb.model.SysAclModule;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
@ToString
public class AclModuleLevelDto extends SysAclModule { //继承后便有了SysAclModule的基本属性


    private List<AclModuleLevelDto> aclModuleList = Lists.newArrayList();

//    private List<AclDto> aclList = Lists.newArrayList();

    public static AclModuleLevelDto adapt(SysAclModule sysAclModule) {
        AclModuleLevelDto dto = new AclModuleLevelDto();
        BeanUtils.copyProperties(sysAclModule, dto);  //copyProperties:复制源对象到目标对像
        return dto;
    }
}



