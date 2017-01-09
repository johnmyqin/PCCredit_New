package com.cardpay.controller.application;

import com.cardpay.basic.base.model.ResultTo;
import com.cardpay.basic.common.enums.ResultEnum;
import com.cardpay.mgt.application.model.TApplicationInvestPicture;
import com.cardpay.mgt.application.service.TApplicationInvestPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 调查图片表Controller
 *
 * @author chenkai
 *         createTime 2017-01-2017/1/9 11:38
 */
@RestController
@RequestMapping("/api/applicationInvestPicture")
public class ApplicationInvestPictureController {
    @Autowired
    private TApplicationInvestPictureService tApplicationInvestPictureService;

    /**
     * 进件相关图片上传
     *
     * @param files                     图片
     * @param tApplicationInvestPicture 相关参数
     * @return 数据库变记录
     */
    @PostMapping
    public ResultTo upLoads(@RequestPart MultipartFile[] files, TApplicationInvestPicture tApplicationInvestPicture) {
        int flag = tApplicationInvestPictureService.insertFile(files, tApplicationInvestPicture);
        return flag != 0 ? new ResultTo().setData(flag) : new ResultTo(ResultEnum.SERVICE_ERROR);
    }

    /**
     * 判断图片是否能继续下一步
     *
     * @param applicationId 进件id
     * @return true/false
     */
    @GetMapping("ifFileNext")
    public ResultTo fileIfNext(int applicationId) {
        boolean mark = tApplicationInvestPictureService.fileIfNext(applicationId);
        return new ResultTo().setData(mark);
    }
}
