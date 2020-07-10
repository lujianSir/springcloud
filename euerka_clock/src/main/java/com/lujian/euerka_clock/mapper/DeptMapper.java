package com.lujian.euerka_clock.mapper;


import com.lujian.euerka_clock.entity.DeptUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptMapper {

    //通过部门ID查询对应的人员
    List<DeptUser> queryUserByDeptId(int deptid);

}
