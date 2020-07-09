package com.lujian.euerka_diary.service.impl;

import com.lujian.euerka_diary.entity.Material;
import com.lujian.euerka_diary.mapper.MaterialMapper;
import com.lujian.euerka_diary.service.MaterialService;
import com.lujian.euerka_diary.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MaterialServicelmpl implements MaterialService {

    @Autowired
    private MaterialMapper materialMapper;

    @Override
    public int insertMaterial(List<Material> list, int pid) {
        // TODO Auto-generated method stub
        materialMapper.deleteMaterial(pid);
        int row = materialMapper.insertMaterial(list);
        return row;
    }

    @Override
    public List<Material> queryMaterials(Material material, int type) throws Exception {
        // TODO Auto-generated method stub
        List<Material> materials = materialMapper.queryMaterials(material, type);
        for (Material mater : materials) {
            String mttime = mater.getMttime();
            String timeStemp = Utils.timeToStamp(mttime);// 时间错
            String datechinese = Utils.timeStampDateChinese(timeStemp, null);
            String currentymd = Utils.getCurrentymd();
            if (mater.getMtstatus() == 0) {
                Map<String, Object> map = Utils.getDistanceDays(mttime, currentymd);
                mater.setRemindcomment(map.get("content").toString());
                mater.setRemindstatus(Integer.parseInt(map.get("status").toString()));
            }
            mater.setMttimechinese(datechinese);
        }
        return materials;
    }

    @Override
    public int deleteAllMaterial(int pid) {
        return 0;
    }

}
