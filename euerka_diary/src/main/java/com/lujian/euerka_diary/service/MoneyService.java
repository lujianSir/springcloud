package com.lujian.euerka_diary.service;

import com.lujian.euerka_diary.entity.CollectionMoney;

import java.util.List;

public interface MoneyService {

    // 添加收款
    int insertCollectionMoney(List<CollectionMoney> list, int pid);

    // 通过项目的ID查询所有的收款信息
    List<CollectionMoney> queryCollectionMoneys(CollectionMoney collectionMoney, int type) throws Exception;

    //根据PID删除所有
    int deletAllMoney(int pid);
}
