package com.lujian.euerka_diary.service.impl;


import com.lujian.euerka_diary.entity.CollectionMoney;
import com.lujian.euerka_diary.mapper.MoneyMapper;
import com.lujian.euerka_diary.service.MoneyService;
import com.lujian.euerka_diary.util.ConvertUpMoney;
import com.lujian.euerka_diary.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MoneyServiceImpl implements MoneyService {

    @Autowired
    private MoneyMapper moneyMapper;

    @Override
    public int insertCollectionMoney(List<CollectionMoney> list, int pid) {
        // TODO Auto-generated method stub
        moneyMapper.deleteCollectionMoney(pid);
        int row = moneyMapper.insertCollectionMoney(list);
        return row;
    }

    @Override
    public List<CollectionMoney> queryCollectionMoneys(CollectionMoney collectionMoney, int type) throws Exception {
        // TODO Auto-generated method stub
        List<CollectionMoney> collectionMoneys = moneyMapper.queryCollectionMoneys(collectionMoney, type);
        for (CollectionMoney money : collectionMoneys) {
            String mtime = money.getMtime();
            String timeStemp = Utils.timeToStamp(mtime);// 时间错
            String datechinese = Utils.timeStampDateChinese(timeStemp, null);
            String currentymd = Utils.getCurrentymd();
            Map<String, Object> map = Utils.getDistanceDays(mtime, currentymd);
            money.setMtimechinese(datechinese);
            money.setRemindcomment(map.get("content").toString());
            money.setRemindstatus(Integer.parseInt(map.get("status").toString()));
            money.setMoneychinese(ConvertUpMoney.toChinese(money.getMoney() + ""));
        }
        return collectionMoneys;
    }

    @Override
    public int deletAllMoney(int pid) {
        return moneyMapper.deletAllMoney(pid);
    }

}
