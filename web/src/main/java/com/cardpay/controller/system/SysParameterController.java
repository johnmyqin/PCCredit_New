package com.cardpay.controller.system;

import com.cardpay.basic.base.model.ResultTo;
import com.cardpay.basic.common.annotation.SystemControllerLog;
import com.cardpay.basic.common.enums.ResultEnum;
import com.cardpay.basic.common.log.LogTemplate;
import com.cardpay.basic.util.datatable.DataTablePage;
import com.cardpay.controller.base.BaseController;
import com.cardpay.core.shiro.common.ShiroKit;
import com.cardpay.mgt.system.model.TSysParameter;
import com.cardpay.mgt.system.model.vo.TSysParameterVo;
import com.cardpay.mgt.system.service.TSysParameterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * 系统参数配置Controller
 *
 * @author chenkai
 *         createTime 2016-12-2016/12/27 10:02
 */
@Controller
@RequestMapping("/system")
@Api(value = "/system", description = "系统参数配置")
public class SysParameterController extends BaseController<TSysParameter> {

    private static final String SYSTEM_UPDATE_PAGE = "/system/update";

    private static final String SYSTEM_INDEX_PAGE = "/system/index";

    private static final String SYSTEM_ADD_PAGE = "/system/add";

    @Autowired
    private TSysParameterService tSysParameterService;

    @Autowired
    private static LogTemplate logger;

    /**
     * 跳转系统参数配置主页
     *
     * @return 统参数配置主页
     */
    @GetMapping("/index")
    @SystemControllerLog(description = "跳转系统参数配置主页")
    @ApiOperation(value = "跳转系统参数配置主页", notes = "系统参数配置主页", httpMethod = "GET")
    public ModelAndView index() {
        return new ModelAndView(SYSTEM_INDEX_PAGE);
    }

    /**
     * 跳转编辑系统参数配置页面
     *
     * @param id 主键
     * @return 编辑系统参数配置页面
     */
    @GetMapping("/{id}")
    @SystemControllerLog(description = "跳转编辑系统参数配置页面")
    @ApiOperation(value = "跳转编辑系统参数配置页面", notes = "编辑系统参数配置页面, 返回参数 tSysParameter", httpMethod = "GET")
    public ModelAndView returnUpdate(@ApiParam(value = "主键", required = true) @PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView(SYSTEM_UPDATE_PAGE);
        TSysParameter tSysParameter = tSysParameterService.selectByPrimaryKey(id);
        modelAndView.addObject("tSysParameter", tSysParameter);
        return modelAndView;
    }

    /**
     * 获取所有系统参数配置
     *
     * @return 所有系统参数配置.
     */
    @GetMapping
    @ResponseBody
    @SystemControllerLog(description = "获取所有系统参数配置")
    @ApiOperation(value = "获取所有系统参数配置", notes = "所有系统参数配置", httpMethod = "GET")
    public DataTablePage queryAll() {
        return dataTablePage("queryAll");
    }

    /**
     * 更新系统参数配置
     *
     * @param tSysParameter 系统参数实体信息
     * @return 数据库变记录
     */
    @PutMapping
    @ResponseBody
    @SystemControllerLog(description = "更新系统参数配置")
    @ApiOperation(value = "更新系统参数配置", notes = "更新系统参数配置", httpMethod = "PUT")
    public ResultTo update(@ApiParam("系统参数实体信息") @ModelAttribute TSysParameter tSysParameter) {
        tSysParameter.setModifyBy(ShiroKit.getUserId());
        tSysParameter.setModifyTime(new Date());
        Integer count = tSysParameterService.updateSelectiveByPrimaryKey(tSysParameter);
        logger.info("更新系统参数配置", "用户id:" + ShiroKit.getUserId() + ",更新了参数" + tSysParameter);
        return count != 0 ? new ResultTo().setData(count) : new ResultTo(ResultEnum.SERVICE_ERROR);
    }

    /**
     * 添加系统参数配置
     *
     * @param tSysParameter 系统参数实体信息
     * @return 主键
     */
    @PostMapping
    @ResponseBody
    @SystemControllerLog(description = "添加系统参数配置")
    @ApiOperation(value = "新增系统参数配置", notes = "新增系统参数配置", httpMethod = "POST")
    public ResultTo insert(@ApiParam("系统参数实体信息") @ModelAttribute TSysParameter tSysParameter) {
        tSysParameter.setCreateBy(ShiroKit.getUserId());
        Integer count = tSysParameterService.insertSelective(tSysParameter);
        logger.info("新增系统参数配置", "用户id:" + ShiroKit.getUserId() + ",添加了参数" + tSysParameter);
        return count != 0 ? new ResultTo().setData(tSysParameter.getId()) : new ResultTo(ResultEnum.SERVICE_ERROR);
    }

    /**
     * 跳转添加页面
     *
     * @return 跳转添加页面
     */
    @GetMapping("/toAdd")
    @SystemControllerLog(description = "跳转添加页面")
    @ApiOperation(value = "跳转添加页面", notes = "跳转添加页面", httpMethod = "GET")
    public ModelAndView toAdd() {
        return new ModelAndView(SYSTEM_ADD_PAGE);
    }

}
