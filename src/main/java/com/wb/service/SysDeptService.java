package com.wb.service;

import com.wb.dao.SysDeptMapper;
import com.wb.exception.ParamException;
import com.wb.model.SysDept;
import com.wb.param.DeptParam;
import com.wb.util.BeanValidator;
import com.wb.util.LevelUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class SysDeptService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    //部门的创建
    public void save(DeptParam deptParam){
        BeanValidator.check(deptParam); //字段校验
        if(checkExist(deptParam.getParentId(), deptParam.getName(), deptParam.getId())){
            throw new ParamException("同一层级下存在相同的部门名称");
        }else {
            SysDept sysDept = SysDept.builder()
                    .name(deptParam.getName())
                    .parentId(deptParam.getParentId())
                    .seq(deptParam.getSeq())
                    .remark(deptParam.getRemark())
                    .build();
            sysDept.setLevel(LevelUtil.calculateLevel(getLevel(deptParam.getParentId()), deptParam.getId()));
            sysDept.setOperator("System"); //TODO
            sysDept.setOperateIp("127.0.0.1");
            sysDept.setOperateTime(new Date());
            sysDeptMapper.insertSelective(sysDept); //
        }
    }

    //部门名是否重复
    private boolean checkExist(Integer parentId, String deptName, Integer deptId){
        //TODO
        return true;
    }

    //获取部门层级
    private String getLevel(Integer deptId){
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(deptId); //通过deptId在数据库中查询是否存在此部门
        if(sysDept == null){
            return null;
        }else {
            return sysDept.getLevel(); //如果部门存在,返回部门层级
        }
    }

}
