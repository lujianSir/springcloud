package com.lujian.euerka_diary.service;

import com.lujian.euerka_diary.entity.Material;

import java.util.List;

public interface MaterialService {

    // 添加材料
    int insertMaterial(List<Material> list, int pid);

    // 通过项目的ID查询所有的材料信息
    List<Material> queryMaterials(Material material, int type) throws Exception;

    int deleteAllMaterial(int pid);
}
