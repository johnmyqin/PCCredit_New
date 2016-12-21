package com.cardpay.mgt.customer.dao;

import com.cardpay.basic.base.mapper.BasicMapper;
import com.cardpay.basic.common.interceptor.mapper.ReturnMapParam;
import com.cardpay.mgt.customer.model.TCustomerBasic;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 客户基本信息Mapper
 * @author chenkai
 */
public interface TCustomerBasicMapper extends BasicMapper<TCustomerBasic> {

    /**
     * 获取潜在客户列表
     * @param customerManagerId 客户经理id
     * @return 潜在客户列表
     */
    List<TCustomerBasic> getProspectiveCustomers(@Param("customerManagerId") Integer customerManagerId);

    /**
     * 判断身份证号码是否已经存在
     * @param idCard 身份证号码
     * @return 是否存在(大于0 存在, 等于0 不存在)
     */
    Integer isIdCardExist(@Param("idCard") int idCard);

    /**
     * 查询客户经理所属客户
     * @param mapParam managerId
     * @return 客户id, 客户名称
     */
    Map<Integer, String> queryCustomer(ReturnMapParam mapParam);

    /**
     * 更新客户状态
     * @param map 状态信息,客户id
     * @return 数据库变记录
     */
    int updateStatus(Map<String, Object> map);

    /**
     * 查询客户
     * @param managerId 客户经理Id
     * @return 客户列表
     */
    List<TCustomerBasic> queryCustomerList(@Param("managerId") int managerId);
}