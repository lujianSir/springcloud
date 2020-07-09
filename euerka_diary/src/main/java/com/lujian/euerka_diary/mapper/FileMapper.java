package com.lujian.euerka_diary.mapper;


import com.lujian.euerka_diary.entity.FileInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMapper {

    // 添加文件信息
    void insertFileInfo(FileInfo fileInfo);

    // 删除文件信息
    void fileinfoDelete(String id);

    // 根据名称查询信息
    FileInfo queryFileInfoByName(String fvirtualurl);

    // 通过ID删除文件信息
    void deleteFileInfoById(int id);
}
