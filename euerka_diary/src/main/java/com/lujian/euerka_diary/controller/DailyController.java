package com.lujian.euerka_diary.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lujian.euerka_diary.entity.*;
import com.lujian.euerka_diary.service.DailyService;
import com.lujian.euerka_diary.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 日报相关的接口
 *
 * @author lujian
 */
@RestController
@RequestMapping("/app/daily")
public class DailyController {

    @Autowired
    private DailyService dailyService;

    /**
     * 添加或者修改日报信息
     *
     * @param daily
     * @return
     */
    @RequestMapping(value = "/insertOrUpdateDaily", method = RequestMethod.POST)
    public Result<?> insertOrUpdateDaily(Daily daily) {
        int row = dailyService.insertOrUpdateDaily(daily);
        if (row > 0) {
            return Result.success("操作成功");
        } else {
            return Result.error(500, "服务端错误");
        }
    }

    /**
     * 查询每一天的日报
     *
     * @param daily
     * @param dtime
     * @return
     */
    @RequestMapping(value = "/queryEveryDay", method = RequestMethod.POST)
    public Result<?> queryEveryDay(Daily daily, String dtime) {
        String[] dtimes = null;
        if (dtime != null && !dtime.equals("")) {
            dtimes = dtime.split(";");
            for (int i = 0; i < dtimes.length; i++) {
                String str = dtimes[i];
                str = Utils.timeStampDateChinese(str, null);
                dtimes[i] = str;
            }
        }
        List<EveryDay> list = dailyService.queryEveryDay(daily, dtimes);
        for (int i = 0; i < list.size(); i++) {
            List<Daily> dailys = list.get(i).getDailyList();
            for (int j = 0; j < dailys.size(); j++) {
                Daily dai = dailys.get(j);
                dai = updateDicsAndDvoideos(dai);
            }
        }
        return Result.success(list);
    }

    /**
     * 查询当前日期每个项目下面有多少日报
     *
     * @param daily
     * @return
     */
    @RequestMapping(value = "/queryProject", method = RequestMethod.POST)
    public Result<?> queryProject(Daily daily) {
        List<ProjectInfo> list = dailyService.queryProject(daily);
        for (int i = 0; i < list.size(); i++) {
            List<Daily> dailys = list.get(i).getDailyList();
            for (int j = 0; j < dailys.size(); j++) {
                Daily dai = dailys.get(j);
                dai = updateDicsAndDvoideos(dai);
            }
        }
        return Result.success(list);
    }

    /**
     * 模糊查询以及单个查询
     *
     * @param daily
     * @return
     */
    @RequestMapping(value = "/queryDaily", method = RequestMethod.POST)
    public Result<?> queryDaily(Daily daily) {
        List<Daily> list = dailyService.queryDaily(daily);
        return Result.success(list);
    }

    /**
     * 单个查询返回点赞
     *
     * @param daily
     * @return
     */
    @RequestMapping(value = "/queryDailyThumb", method = RequestMethod.POST)
    public Result<?> queryDailyThumb(Daily daily) {
        List<Daily> list = dailyService.queryDailyThumb(daily);
        for (int i = 0; i < list.size(); i++) {
            Daily dai = list.get(i);
            dai = updateDicsAndDvoideos(dai);
        }
        return Result.success(list);
    }

    /**
     * 添加或者删除点赞
     *
     * @param thumb
     * @return
     */
    @RequestMapping(value = "/insertThumb", method = RequestMethod.POST)
    public Result<?> insertThumb(Thumb thumb) {
        int row = dailyService.insertThumb(thumb);
        return Result.success(row);
    }

    /**
     * 添加评论
     *
     * @param comment
     * @return
     */
    @RequestMapping(value = "/insertComment", method = RequestMethod.POST)
    public Result<?> insertComment(Comment comment) {
        int row = dailyService.insertComment(comment);
        return Result.success(row);
    }

    /**
     * 查询是否是自己的评论
     *
     * @param comment
     * @return
     */
    @RequestMapping(value = "/queryComment", method = RequestMethod.POST)
    public Result<?> queryComment(Comment comment) {
        Comment c = dailyService.queryComment(comment);
        return Result.success(c);
    }

    /**
     * 通过评论ID删除评论
     *
     * @param comment
     * @return
     */
    @RequestMapping(value = "/deleteCommentByCmidAndCmuid", method = RequestMethod.POST)
    public Result<?> deleteCommentByCmidAndCmuid(Comment comment) {
        int row = dailyService.deleteCommentByCmidAndCmuid(comment);
        return Result.success(row);
    }

    /**
     * 查询每个月的照片以及视频
     *
     * @param daily
     * @param creatMouth
     * @return
     */
    @RequestMapping(value = "/queryEveryDayByMonth", method = RequestMethod.POST)
    public Result<?> queryEveryDayByMonth(Daily daily, String creatMouth) {
        String[] creatMouths = null;
        if (creatMouth != null && !creatMouth.equals("")) {
            creatMouths = creatMouth.split(";");
            for (int i = 0; i < creatMouths.length; i++) {
                String cm = creatMouths[i];
                cm = Utils.timeStampDateChineseshot(cm, null);
                creatMouths[i] = cm;
            }
        }
        List<EveryDay> list = dailyService.queryEveryDayByMonth(daily, creatMouths);
        for (int i = 0; i < list.size(); i++) {
            List<Daily> dailys = list.get(i).getDailyList();
            for (int j = 0; j < dailys.size(); j++) {
                Daily dai = dailys.get(j);
                dai = updateDicsAndDvoideos(dai);
            }
        }
        return Result.success(list);
    }

    /**
     * 查询是否绑定1
     *
     * @param did
     * @param uid
     * @return
     */
    @RequestMapping(value = "/queryDailyByDidAndUid", method = RequestMethod.POST)
    public Result<?> queryDailyByDidAndUid(int did, int uid) {
        Daily daily = dailyService.queryDailyByDidAndUid(did, uid);
        daily = updateDicsAndDvoideos(daily);
        return Result.success(daily);
    }

    /**
     * 删除日报
     *
     * @param did
     * @return
     */
    @RequestMapping(value = "/deleteDailyByDid", method = RequestMethod.POST)
    public Result<?> deleteDailyByDid(int did) {
        int row = dailyService.deleteDailyByDid(did);
        return Result.success(row);
    }

    /**
     * 修改状态
     *
     * @return
     */
    public Daily updateDicsAndDvoideos(Daily daily) {
        List<String> list1 = new ArrayList<String>();
        String dpic = daily.getDpic();
        dpic = StringUtils.strip(dpic, "[]");
        if (dpic != null && !dpic.equals("")) {
            String[] dpics = dpic.split(",");
            for (int m = 0; m < dpics.length; m++) {
                list1.add(dpics[m].replace("\"", ""));
            }
            daily.setDpics(list1);
        }

        List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
        String dvoideo = daily.getDvoideo();
        if (dvoideo != null && !dvoideo.equals("")) {
            JSONArray jsonArray = JSONArray.parseArray(new String(dvoideo));
            for (int n = 0; n < jsonArray.size(); n++) {
                Map<String, Object> m = new HashMap<String, Object>();
                JSONObject o = (JSONObject) jsonArray.get(n);
                Map<String, Object> map = o;
                for (Entry<String, Object> entry : map.entrySet()) {
                    m.put(entry.getKey(), entry.getValue());
                }
                list2.add(m);
            }
            daily.setDvoideos(list2);
        }

        List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();
        String equipments = daily.getEquipments();
        if (equipments != null && !equipments.equals("")) {
            JSONArray jsonArrayequipment = JSONArray.parseArray(new String(equipments));
            for (int p = 0; p < jsonArrayequipment.size(); p++) {
                Map<String, Object> m = new HashMap<String, Object>();
                JSONObject o = (JSONObject) jsonArrayequipment.get(p);
                Map<String, Object> map = o;
                for (Entry<String, Object> entry : map.entrySet()) {
                    m.put(entry.getKey(), entry.getValue());
                }
                list3.add(m);
            }
            daily.setListequipments(list3);
        }

        return daily;
    }
}
