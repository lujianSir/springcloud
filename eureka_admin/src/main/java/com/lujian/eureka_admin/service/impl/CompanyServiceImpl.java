package com.lujian.eureka_admin.service.impl;


import com.lujian.eureka_admin.entity.Company;
import com.lujian.eureka_admin.entity.Result;
import com.lujian.eureka_admin.mapper.CompanyMapper;
import com.lujian.eureka_admin.service.CompanyService;
import com.lujian.eureka_admin.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public List<Company> queryCompanyByUidOrCid(Company company) {
        // TODO Auto-generated method stub
        return companyMapper.queryCompanyByUidOrCid(company);
    }

    @Override
    public Result<?> insertOrUpdateCompany(Company company) {
        // TODO Auto-generated method stub

        if (company.getCid() > 0) {// 存在 修改
            Company cpany = companyMapper.queryCompanyByCid(company.getCid());
            if (company.getCname().equals(cpany.getCname())) {
                companyMapper.updateCompany(company);
                return Result.success("修改成功");
            } else {
                Company c = companyMapper.queryCompanyByCname(company.getCname());//是否重复
                if (c != null) {
                    return Result.error(500, "名称重复");
                } else {
                    companyMapper.updateCompany(company);
                    return Result.success("修改成功");
                }
            }


        } else {
            Company cpany = companyMapper.queryCompanyByCname(company.getCname());
            if (cpany != null) {
                return Result.error(500, "名称重复");
            } else {
                String currentTime = Utils.getCurrent();
                company.setCreattime(currentTime);
                companyMapper.insertCompany(company);
                return Result.success("添加成功");
            }
        }

    }

    @Override
    public int deleteCompanyByCid(int cid) {
        // TODO Auto-generated method stub
        return companyMapper.deleteCompanyByCid(cid);
    }

}
