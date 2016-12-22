package com.cardpay.controller.customer;

import com.cardpay.basic.base.model.ResultTo;
import com.cardpay.basic.base.model.SelectModel;
import com.cardpay.basic.common.annotation.SystemControllerLog;
import com.cardpay.basic.common.constant.ConstantEnum;
import com.cardpay.basic.common.enums.ResultEnum;
import com.cardpay.basic.common.interceptor.mapper.ReturnMapParam;
import com.cardpay.controller.base.BaseController;
import com.cardpay.core.shiro.common.ShiroKit;
import com.cardpay.mgt.customer.model.TCustomerBasic;
import com.cardpay.mgt.customer.service.TCustomerBasicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.Model;
import oracle.net.aso.i;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户controller
 *
 * @author chenkai
 */
@Api(value = "/customerBasic", description = "客户基本信息")
@Controller
@RequestMapping("/customerBasic")
public class CustomerBasicController extends BaseController<TCustomerBasic> {
    @Autowired
    private TCustomerBasicService customerBasicService;

    /**
     * 获取潜在客户列表
     *
     * @return 潜在客户列表
     */
    @ResponseBody
    @GetMapping("/prospectiveCustomers")
    @SystemControllerLog
    @ApiOperation(value = "查询潜在客户列表", notes = "潜在客户列表", httpMethod = "GET")
    public ResultTo getProspectiveCustomers() {
        List<TCustomerBasic> customerBasics = customerBasicService.getProspectiveCustomers(ShiroKit.getUserId());
        return new ResultTo().setData(customerBasics);
    }

    /**
     * 验证证件号码是否已经存在
     *
     * @param identityCard 证件号码
     * @return true/false
     */
    @ResponseBody
    @GetMapping("/idCardExist")
    @SystemControllerLog
    @ApiOperation(value = "证件号码验重", notes = "证件号码验重", httpMethod = "GET")
    public ResultTo validate(@ApiParam(value = "证件号码", required = true) int identityCard) {
        boolean idCardExist = customerBasicService.isIdCardExist(identityCard);
        return idCardExist ? new ResultTo() : new ResultTo(ResultEnum.SERVICE_ERROR);
    }

    /**
     * 更新客户基本信息
     *
     * @param tCustomerBasic 客户基本信息
     * @return 数据库变更数量
     */
    @ResponseBody
    @PutMapping("")
    @SystemControllerLog
    @ApiOperation(value = "更新客户基本信息", notes = "更新客户基本信息", httpMethod = "PUT")
    public ResultTo update(@ApiParam(value = "客户基本信息", required = true) @ModelAttribute TCustomerBasic tCustomerBasic) {
        int count = updateAndCompareBean(tCustomerBasic, "customerBasic", "客户基本信息");
        return count != 0 ? new ResultTo().setData(count) : new ResultTo(ResultEnum.SERVICE_ERROR);
    }

    /**
     * 新建客戶经理
     *
     * @param tCustomerBasic 客户基本信息
     * @return 数据库变更记录
     */
    @ResponseBody
    @PostMapping("")
    @SystemControllerLog(description = "新建客戶经理")
    @ApiOperation(value = "新建客戶", notes = "新建客戶经理", httpMethod = "POST")
    public ResultTo newCustomer(@ApiParam(value = "客户基本信息", required = true) @ModelAttribute TCustomerBasic tCustomerBasic) {
        Integer count = customerBasicService.insertSelective(tCustomerBasic);
        return count != 0 ? new ResultTo().setData(count) : new ResultTo(ResultEnum.SERVICE_ERROR);
    }

    /**
     * 跳转新建客户经理页面
     *
     * @return 客户经理新建页面
     */
    @GetMapping("/new")
    @SystemControllerLog(description = "跳转新建客户经理页面")
    @ApiOperation(value = "跳转客户经理新建页面", notes = "客户经理新建页面", httpMethod = "GET")
    public ModelAndView returnNewCustomer() {
        ModelAndView modelAndView = new ModelAndView("/customer/new");
        Map<String, List<SelectModel>> dropDownList = new HashMap<>();
        List<SelectModel> cert = customerBasicService.getCert();
        List<SelectModel> educationDegree = customerBasicService.getEducationDegree();
        List<SelectModel> marriageStatus = customerBasicService.getMarriageStatus();
        dropDownList.put("cert", cert);
        dropDownList.put("educationDegree", educationDegree);
        dropDownList.put("marriageStatus", marriageStatus);
        modelAndView.addObject("dropDownList", dropDownList);
        return modelAndView;
    }

    /**
     * 跳转查询客户页面
     *
     * @return 客户列表
     */
    @GetMapping("/success")
    @SystemControllerLog(description = "跳转查询客户页面")
    @ApiOperation(value = "跳转查询客户页面", notes = "查询客户页面", httpMethod = "GET")
    public ModelAndView returnCustomerList() {
        return new ModelAndView("customer/index");
    }

    /**
     * 按条件查询客户经理信息
     *
     * @param name     客户名称
     * @param IdNumber 客户证件号码
     * @return 客户信息
     */
    @ResponseBody
    @GetMapping("/condition")
    @SystemControllerLog(description = "按条件查询客户经理信息")
    @ApiOperation(value = "按条件查询客户经理信息", notes = "查询客户经理信息", httpMethod = "GET")
    public ResultTo queryCondition(@ApiParam("客户名称") @RequestParam String name
            , @ApiParam("客户证件号码") @RequestParam String IdNumber) {
        TCustomerBasic tCustomerBasic = new TCustomerBasic();
        tCustomerBasic.setCname(name);
        tCustomerBasic.setCertificateNumber(IdNumber);
        tCustomerBasic.setCustomerManagerId(ShiroKit.getUserId());
        List<TCustomerBasic> tCustomerBasics = customerBasicService.queryCustomerByCondition(tCustomerBasic);
        return new ResultTo().setData(tCustomerBasics);
    }

    /**
     * 客户更新页面跳转
     *
     * @param customerId 客户id
     * @return 更新页面
     */
    @GetMapping("/{id}")
    @SystemControllerLog(description = "客户更新页面跳转")
    @ApiOperation(value = "按id查询客户基本信息", notes = "查询客户经理信息 返回参数名称:tCustomerBasic", httpMethod = "GET")
    public ModelAndView returnUpdate(@ApiParam(value = "客户id", required = true)@PathVariable("id") int customerId) {
        ModelAndView modelAndView = new ModelAndView("/customer/update");
        TCustomerBasic tCustomerBasic = customerBasicService.selectByPrimaryKey(customerId);
        modelAndView.addObject("tCustomerBasic", tCustomerBasic);
        return modelAndView;
    }

    /**
     * 批量删除用户
     *
     * @param customerIds 客户id(,分割)
     * @return 删除
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    @ApiOperation(value = "批量删除用户", notes = "改变用户状态将用户设为不可用", httpMethod = "DELETE")
    public ResultTo deleteCustomer(@ApiParam("客户id(,分割)") @PathVariable("id") String customerIds) {
        List<Integer> ids = new ArrayList<>();
        String[] split = customerIds.split(",");
        for (String id : split) {
            int customerId = Integer.parseInt(id);
            ids.add(customerId);
        }

        Map<String, Object> map = new HashedMap();
        map.put("status", ConstantEnum.CustomerStatus.STATUS3.getName());
        map.put("customerIds", ids);
        map.put("managerId", ShiroKit.getUserId());
        int count = customerBasicService.updateStatus(map);
        return count != 0 ? new ResultTo().setData(count) : new ResultTo(ResultEnum.SERVICE_ERROR);
    }

}
