package com.cardpay.controller.log;

import com.cardpay.basic.util.datatable.DataTablePage;
import com.cardpay.controller.base.BaseController;
import com.cardpay.mgt.log.model.LoginLog;
import com.cardpay.mgt.user.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 日志controller
 *
 * @author rankai
 * @create 2016-12-2016/12/21 10:22
 */

@Controller
@RequestMapping("/loginLog")
@Api(value = "/loginLog", description = "登陆日志日志操作")
public class LoginLogController extends BaseController<LoginLog, Integer> {

    /**
     * 登陆日志的跳转页面
     *
     * @return 登陆日志的页面
     */
    @ApiResponses({@ApiResponse(code = 405, message = "请求类型错误"), @ApiResponse(code = 500, message = "服务器异常")})
    @ApiOperation(value = "登陆日志的跳转页面", httpMethod = "GET")
    @GetMapping()
    public String loginLogPage() {
        return "/test";
    }

    /**
     * 获取日志分页列表数据
     *
     * @return 日志分页列表数据
     */
    @ApiResponses({@ApiResponse(code = 405, message = "请求类型错误"), @ApiResponse(code = 500, message = "服务器异常")})
    @ApiOperation(value = "获取日志分页列表数据", httpMethod = "GET")
    @GetMapping("/pageList")
    @ResponseBody
    public DataTablePage loginLog() {
        return dataTablePage();
    }

}
