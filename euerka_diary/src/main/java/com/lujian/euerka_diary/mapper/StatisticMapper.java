package com.lujian.euerka_diary.mapper;

import com.lujian.euerka_diary.entity.Daily;
import com.lujian.euerka_diary.entity.DailyStatistic;
import com.lujian.euerka_diary.entity.Statistic;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticMapper {

	// 查询统计日期
	List<Statistic> queryWeacher(Daily daily);

	// 统计人数
	List<Statistic> queryAttendancetody(Daily daily);

	// 项目统计
	List<DailyStatistic> queryDailyStatistic(Daily daily);

	// 查询机器
	List<Daily> queryDailyEquipment(Daily daily);

	// 个人统计
	List<DailyStatistic> queryDailyStatisticByUid(Daily daily);

	// 个人日报查询
	List<Daily> queryDailyByUid(Daily daily);
}
