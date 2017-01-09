package com.cardpay.mgt.application.service;

import com.cardpay.basic.base.service.BaseService;
import com.cardpay.mgt.application.model.TApplicationInvestPicture;
import org.springframework.web.multipart.MultipartFile;

/**
 * 调查图片表Service
 *
 * @author chenkai
 *         createTime 2017-01-2017/1/9 11:35
 */
public interface TApplicationInvestPictureService extends BaseService<TApplicationInvestPicture> {

    /**
     * 多文件上传
     * @param files 文件
     * @param tApplicationInvestPicture 文件相关
     * @return 数据库变记录
     */
    int insertFile(MultipartFile[] files, TApplicationInvestPicture tApplicationInvestPicture);

    /**
     * 进件需要上传的图片信息是否已经上传
     * @param applicationId 进件id
     * @return true/false
     */
    boolean fileIfNext(int applicationId);
}
