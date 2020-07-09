package com.lujian.euerka_diary.service;

import com.lujian.euerka_diary.entity.Result;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    // 文件信息上传
    String fileUpload(MultipartFile file, int kind);

    // 根据虚拟路径删除文件
    Result<?> fileinfoDelete(String fvirtualurl);
}
