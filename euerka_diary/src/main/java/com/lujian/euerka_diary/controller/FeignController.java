package com.lujian.euerka_diary.controller;

import com.lujian.euerka_diary.entity.Project;
import com.lujian.euerka_diary.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * restful风格
 */
@RestController
@RequestMapping("/feign")
public class FeignController {

    @Autowired
    private ProjectService projectService;

    /**
     * 通过cid查询绑定的项目
     * @param cid
     * @return
     */
    @RequestMapping(value = "/queryProject/{cid}",method = RequestMethod.GET)
    public List<Project>  queryProject(@PathVariable("cid") int cid){
        Project project=new Project();
        project.setCid(cid);
        List<Project> list = projectService.queryProject(project);
        return list;
    }
}
