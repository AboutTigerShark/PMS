package com.wb.service;

import com.google.common.base.Preconditions;
import com.wb.dao.SysDeptMapper;
import com.wb.exception.ParamException;
import com.wb.model.SysDept;
import com.wb.param.DeptParam;
import com.wb.util.BeanValidator;
import com.wb.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

    //部门的修改
    public void update(DeptParam deptParam){
        BeanValidator.check(deptParam); //字段校验
        if(checkExist(deptParam.getParentId(), deptParam.getName(), deptParam.getId())){
            throw new ParamException("同一层级下存在相同的部门名称");
        }else {
            SysDept before_update = sysDeptMapper.selectByPrimaryKey(deptParam.getId());
            Preconditions.checkNotNull(before_update);
            SysDept after_update = SysDept.builder()
                    .id(deptParam.getId())
                    .name(deptParam.getName())
                    .parentId(deptParam.getParentId())
                    .seq(deptParam.getSeq())
                    .remark(deptParam.getRemark())
                    .build();
            after_update.setLevel(LevelUtil.calculateLevel(getLevel(deptParam.getParentId()), deptParam.getParentId()));
            after_update.setOperator("System-update"); //TODO
            after_update.setOperateIp("127.0.0.1");
            after_update.setOperateTime(new Date());
            updateWithChild(before_update, after_update); //连同子部门一并修改
        }
    }

    //子部门的修改
    @Transactional //事务,要么都成功,要么多失败
    public void updateWithChild(SysDept before_update, SysDept after_update){
        String newLevelPrefix = after_update.getLevel();
        String oldLevelPrefix = before_update.getLevel();
        //判断是否需要更新子部门
        if(!after_update.getLevel().equals(before_update.getLevel())){
            List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(before_update.getLevel());
            if(CollectionUtils.isNotEmpty(deptList)){
                for(SysDept sysDept : deptList){
                    String level = sysDept.getLevel();
                    if(level.indexOf(oldLevelPrefix) == 0){  //indexOf:返回在字符串第一次出现的索引
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length()); //substring
                        sysDept.setLevel(level);
                    }
                }
            }
        }
        sysDeptMapper.updateByPrimaryKey(after_update);
    }

    //部门名是否重复
    private boolean checkExist(Integer parentId, String deptName, Integer deptId){
        return sysDeptMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;
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
