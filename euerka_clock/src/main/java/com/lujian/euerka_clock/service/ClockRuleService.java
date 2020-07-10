package com.lujian.euerka_clock.service;

import com.lujian.euerka_clock.entity.AddressMessage;
import com.lujian.euerka_clock.entity.ClockRule;
import com.lujian.euerka_clock.entity.Result;
import com.lujian.euerka_clock.entity.RuleUser;

public interface ClockRuleService {

    //添加考勤地点信息
    Result<?> insertAddreeMessage(AddressMessage addressMessage);

    //根据ID修改考勤地点的信息
    Result<?> updateAddreeMessageByAmId(AddressMessage addressMessage);

    //根据ID删除考勤地址
    Result<?> deleteAddreeMessageByAmId(AddressMessage addressMessage);

    //根据ID查询考勤地址
    Result<?> queryAddreeMessageByAmId(AddressMessage addressMessage);


    //添加打卡规则
    Result<?> insertClockRule(ClockRule clockRule);

    //查询当前用户的打卡规则
    Result<?> queryClockRuleByUidAndCid(String currentTime, RuleUser ruleUser, String x2, String y2) throws Throwable;
}
