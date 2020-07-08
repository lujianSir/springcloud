package com.lujian.eureka_admin.controller;

import com.lujian.eureka_admin.entity.Company;
import com.lujian.eureka_admin.entity.Project;
import com.lujian.eureka_admin.entity.Result;
import com.lujian.eureka_admin.service.CompanyService;
import com.lujian.eureka_admin.service.ProjectFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 公司相关的接口
 *
 * @author lujian
 */
@RestController
@RequestMapping("/app/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ProjectFeign projectFeign;

    /**
     * 通过公司ID或者用户ID查询公司的信息
     *
     * @param company
     * @return
     */
    @RequestMapping(value = "/queryCompanyByUidOrCid", method = RequestMethod.POST)
    public Result<?> queryCompanyByUidOrCid(Company company) {
        List<Company> list = companyService.queryCompanyByUidOrCid(company);
        return Result.success(list);
    }

    /**
     * 添加或者修改公司信息
     *
     * @param company
     * @return
     */
    @RequestMapping(value = "/insertOrUpdateCompany", method = RequestMethod.POST)
    public Result<?> insertOrUpdateCompany(Company company) {
        return companyService.insertOrUpdateCompany(company);
    }

    /**
     * 根据ID删除公司信息
     *
     * @param cid
     * @return
     */
    @RequestMapping(value = "/deleteCompanyByCid", method = RequestMethod.POST)
    public Result<?> deleteCompanyByCid(int cid) {
        Project project = new Project();
        project.setCid(cid);
        List<Project> list = projectFeign.queryProject(cid);
        if (list.size() > 0) {
            return Result.error(500, "有绑定数据");
        } else {
            companyService.deleteCompanyByCid(project.getCid());
            return Result.success("删除成功");
        }
    }
}
