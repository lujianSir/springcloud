package com.lujian.euerka_clock.service.impl;

import com.lujian.euerka_clock.entity.*;
import com.lujian.euerka_clock.mapper.ClockRuleMapper;
import com.lujian.euerka_clock.mapper.DeptMapper;
import com.lujian.euerka_clock.service.ClockRuleService;
import com.lujian.euerka_clock.util.DateUtil;
import com.lujian.euerka_clock.util.LocationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ClockRuleServiceImpl implements ClockRuleService {

    @Autowired
    private ClockRuleMapper clockRuleMapper;

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public Result<?> insertAddreeMessage(AddressMessage addressMessage) {
        int row = clockRuleMapper.insertAddreeMessage(addressMessage);
        if (row > 0) {
            return Result.success(addressMessage);
        } else {
            return Result.error(500, "添加失败");
        }
    }

    @Override
    public Result<?> updateAddreeMessageByAmId(AddressMessage addressMessage) {
        int row = clockRuleMapper.updateAddreeMessageByAmId(addressMessage);
        if (row > 0) {
            return Result.success("修改成功");
        } else {
            return Result.error(500, "修改失败");
        }
    }

    @Override
    public Result<?> deleteAddreeMessageByAmId(AddressMessage addressMessage) {
        int row = clockRuleMapper.deleteAddreeMessageByAmId(addressMessage);
        if (row > 0) {
            return Result.success("删除成功");
        } else {
            return Result.error(500, "删除失败");
        }
    }

    @Override
    public Result<?> queryAddreeMessageByAmId(AddressMessage addressMessage) {
        addressMessage = clockRuleMapper.queryAddreeMessageByAmId(addressMessage);
        return Result.success(addressMessage);
    }

    @Override
    public Result<?> insertClockRule(ClockRule clockRule) {
        int cid = clockRule.getCid();
        List<RuleUser> list;
        String msg = "";
        if (clockRule.getUsers() != null && !clockRule.getUsers().equals("")) {//判断添加企业用户是否已经存在规则
            String[] users = clockRule.getUsers().split(",");
            list = clockRuleMapper.queryRuleUserByUidAndCid(cid, users);
            if (list.size() > 0) {
                msg += "用户：";
                for (int i = 0; i < list.size(); i++) {
                    msg += list.get(i).getUsername() + "已存在" + list.get(i).getCrname();
                    if (i < list.size() - 1) {
                        msg += ",";
                    }
                }
                msg += "，请解除相应的绑定!";
                return Result.error(500, msg);
            }
        }
        if (clockRule.getDeptids() != null && !clockRule.getDeptids().equals("")) {//判断该企业、部门下面的用户是否已经存在打卡规则
            List<String> deptusers = new ArrayList<String>();
            String[] clocks = clockRule.getDeptids().split(",");//获取所有的部门
            for (int i = 0; i < clocks.length; i++) {
                int deptid = Integer.parseInt(clocks[i]);
                List<DeptUser> deptUsers = deptMapper.queryUserByDeptId(deptid);
                if (deptUsers.size() > 0) {
                    for (DeptUser deptUser : deptUsers) {
                        deptusers.add(deptUser.getUid() + "");
                    }
                }
            }
            list = clockRuleMapper.queryRuleUserListByUidAndCid(cid, deptusers);
            if (list.size() > 0) {
                msg += "用户：";
                for (int i = 0; i < list.size(); i++) {
                    msg += list.get(i).getUsername() + "已存在" + list.get(i).getCrname();
                    if (i < list.size() - 1) {
                        msg += ",";
                    }
                }
                msg += "，请解除相应的绑定!";
                return Result.error(501, msg);
            }
            clockRule.setUsers(deptusers.toString());
        }
        int row = clockRuleMapper.insertClockRule(clockRule);
        if (clockRule.getUsers() != null && !clockRule.getUsers().equals("")) {
            String[] users = clockRule.getUsers().split(",");
            List<RuleUser> ruleUsers = new ArrayList<RuleUser>();
            for (int m = 0; m < users.length; m++) {
                RuleUser ruleUser = new RuleUser();
                ruleUser.setUid(Integer.parseInt(users[m]));
                ruleUser.setCid(cid);
                ruleUser.setCrid(clockRule.getCrid());
                ruleUsers.add(ruleUser);
            }
            clockRuleMapper.insertRuleUsers(ruleUsers);
        }
        if (row > 0) {
            return Result.success("添加成功");
        } else {
            return Result.error(500, "添加失败");
        }
    }

    @Override
    public Result<?> queryClockRuleByUidAndCid(String currentTime, RuleUser ruleUser, String x2, String y2) throws Throwable {
        ClockRule clockRule = clockRuleMapper.queryClockRuleByUidAndCid(ruleUser);
        if (clockRule == null) {
            return Result.error(500, "当前没有打卡规则，请联系管理员!");
        }
        String ruledata = clockRule.getRuledata();//获取打卡规则的日期
        int holidaystatus = clockRule.getHolidaystatus(); //法定节假日是否休息  1-休息 2-打卡
        Map<String, String> map = DateUtil.getWeeKMsg(currentTime);
        String currentdata = map.get("data");//当前星期几
        String msg = map.get("msg");// 当前是上班、周末、还是节假日
        if (holidaystatus == 1) {
            if (msg.equals("节假日")) {
                return Result.error(501, "节假日不需要打卡");
            }
        }
        if (ruledata.indexOf(currentdata) == -1) {//不包括  也是不在这个范围内
            return Result.error(502, "周末不需要打卡");
        }
        String amids = clockRule.getAmids();//获取当前打卡的范围
        List<AddressMessage> addressMessages = new ArrayList<AddressMessage>();
        if (amids != null && !amids.equals("")) {
            String[] ids = amids.split(",");
            addressMessages = clockRuleMapper.queryAddressMessageByAmids(ids);
        }
        if (addressMessages.size() > 0) {
            for (int i = 0; i < addressMessages.size(); i++) {
                String x1 = addressMessages.get(i).getAmlatitude();//获取经度
                String y1 = addressMessages.get(i).getAmlongitude();//获取纬度
                boolean flag = LocationUtils.checkDistance(Double.parseDouble(x1), Double.parseDouble(y1), Double.parseDouble(x2), Double.parseDouble(y2), addressMessages.get(i).getAmrange());
                if (flag) {
                    clockRule.setFlag(true);
                    break;
                }
            }
        }
        return Result.success(clockRule);
    }
}
