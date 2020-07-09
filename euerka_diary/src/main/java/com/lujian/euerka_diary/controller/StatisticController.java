package com.lujian.euerka_diary.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lujian.euerka_diary.entity.Daily;
import com.lujian.euerka_diary.entity.DailyStatistic;
import com.lujian.euerka_diary.entity.Result;
import com.lujian.euerka_diary.entity.Statistic;
import com.lujian.euerka_diary.service.StatisticService;
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
 * 统计相关的接口
 *
 * @author lujian
 */
@RestController
@RequestMapping("/app/statistic")
public class StatisticController {
    @Autowired
    private StatisticService statisticService;

    /**
     * 天气统计
     *
     * @param daily
     * @return
     */
    @RequestMapping(value = "/queryWeacher", method = RequestMethod.POST)
    public Result<?> queryWeacher(Daily daily) {
        List<Statistic> list = statisticService.queryWeacher(daily);
        return Result.success(list);

    }

    /**
     * 日报统计
     *
     * @param daily
     * @return
     */
    @RequestMapping(value = "/queryAttendancetody", method = RequestMethod.POST)
    public Result<?> queryAttendancetody(Daily daily) {
        List<Statistic> list = statisticService.queryAttendancetody(daily);
        return Result.success(list);

    }

    /**
     * 项目统计
     *
     * @param daily
     * @return
     */
    @RequestMapping(value = "/queryDailyStatistic", method = RequestMethod.POST)
    public Result<?> queryDailyStatistic(Daily daily) {
        List<DailyStatistic> list = statisticService.queryDailyStatistic(daily);
        return Result.success(list);

    }

    /**
     * 查询个人
     *
     * @param daily
     * @return
     */
    @RequestMapping(value = "/queryDailyStatisticByUid", method = RequestMethod.POST)
    public Result<?> queryDailyStatisticByUid(Daily daily) {
        List<DailyStatistic> list = statisticService.queryDailyStatisticByUid(daily);
        return Result.success(list);

    }

    /**
     * 查询日报
     *
     * @param daily
     * @return
     */
    @RequestMapping(value = "/queryDailyByUid", method = RequestMethod.POST)
    public Result<?> queryDailyByUid(Daily daily) {
        List<Daily> dailys = statisticService.queryDailyByUid(daily);
        for (int j = 0; j < dailys.size(); j++) {
            List<String> list1 = new ArrayList<String>();
            String dpic = dailys.get(j).getDpic();
            dpic = StringUtils.strip(dpic, "[]");
            String[] dpics = dpic.split(",");
            for (int m = 0; m < dpics.length; m++) {
                list1.add(dpics[m].replace("\"", ""));
            }
            dailys.get(j).setDpics(list1);

            List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
            String dvoideo = dailys.get(j).getDvoideo();
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

        }
        return Result.success(dailys);

    }
}
