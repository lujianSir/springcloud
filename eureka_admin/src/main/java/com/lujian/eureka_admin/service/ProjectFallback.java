package com.lujian.eureka_admin.service;

import com.lujian.eureka_admin.entity.Project;

import java.util.List;

public class ProjectFallback implements ProjectFeign{

    @Override
    public List<Project> queryProject(int cid) {
        String msg="服务调用失败";
        System.out.println(msg);
        return null;
    }
}
