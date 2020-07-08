package com.lujian.eureka_admin.service;

import com.lujian.eureka_admin.entity.Project;
import com.lujian.eureka_admin.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.List;

@Component
@FeignClient(value = "EUREKA-DIARY", fallback= ProjectFallback.class)
public interface ProjectFeign {
    /**
     * 项目多种条件查询
     * @param
     * @return
     */
    @RequestMapping(value = "/feign/queryProject/{cid}",method = RequestMethod.GET)
    List<Project>  queryProject(@PathVariable("cid") int cid);

}
