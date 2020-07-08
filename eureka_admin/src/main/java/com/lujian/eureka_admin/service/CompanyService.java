package com.lujian.eureka_admin.service;


import com.lujian.eureka_admin.entity.Company;
import com.lujian.eureka_admin.entity.Result;

import java.util.List;

public interface CompanyService {

    // 通过用户ID或者公司ID查询所有的公司信息
    List<Company> queryCompanyByUidOrCid(Company company);

    // 添加或者修改公司信息
    Result<?> insertOrUpdateCompany(Company company);

    // 通过企业的ID删除企业
    int deleteCompanyByCid(int cid);

}
