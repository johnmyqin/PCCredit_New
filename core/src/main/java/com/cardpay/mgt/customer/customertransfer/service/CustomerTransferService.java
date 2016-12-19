package com.cardpay.mgt.customer.customertransfer.service;

import com.cardpay.basic.base.model.SelectModel;
import com.cardpay.basic.base.service.BaseService;
import com.cardpay.mgt.customer.customertransfer.model.po.TCustomerTransfer;

import java.util.List;

/**
 * 客户移交类
 * @author wangpeng
 */
public interface CustomerTransferService extends BaseService<TCustomerTransfer>{
    /**
     * 获取移交接收意见状态
     * @return 移交接收意见状态列表
     */
    List<SelectModel> getTransferStatus();
}
