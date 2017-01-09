package com.cardpay.controller.user;

import com.cardpay.basic.base.model.ResultTo;
import com.cardpay.basic.common.enums.ResultEnum;
import com.cardpay.basic.common.log.LogTemplate;
import com.cardpay.basic.util.ErrorMessageUtil;
import com.cardpay.basic.util.IDcardUtil;
import com.cardpay.basic.util.datatable.DataTablePage;
import com.cardpay.controller.base.BaseController;
import com.cardpay.core.shiro.common.PasswordUtil;
import com.cardpay.core.shiro.common.ShiroKit;
import com.cardpay.mgt.user.model.User;
import com.cardpay.mgt.user.model.vo.UserUpdateVo;
import com.cardpay.mgt.user.service.RoleService;
import com.cardpay.mgt.user.service.UserService;
import io.swagger.annotations.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户控制层
 *
 * @author rankai
 *         create 2016-12-2016/12/21 10:22
 */
@RestController
@RequestMapping("/api/user")
@Api(value = "/api/user", description = "用户控制层")
public class UserController extends BaseController<User> {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 用户分页
     *
     * @return 分页后的数据
     */
    @PostMapping("/pageList")
    @ApiOperation(value = "用户分页数据", httpMethod = "POST")
    public DataTablePage pageList() {
        return dataTablePage("userPageList");
    }

    /**
     * 更新用户状态
     *
     * @param user User对象
     * @return 成功或失败
     */
    @PutMapping("/status")
    @ApiOperation(value = "用户异步更新", httpMethod = "GET")
    public ResultTo updateStatus(User user) {
        if (userService.updateSelectiveByPrimaryKey(user) > 0) {
            return new ResultTo();
        }
        return new ResultTo(ResultEnum.OPERATION_FAILED);
    }

    /**
     * 重置用户密码(管理员重置)
     *
     * @param user User对象
     * @return 成功或失败
     */
    @PutMapping("/reset")
    @ApiOperation(value = "用户异步更新", httpMethod = "GET")
    public ResultTo update(User user) {
        user.setPassword(ShiroKit.DEFAULT_PASSWORD);
        PasswordUtil.encryptPassword(user);
        if (userService.updateSelectiveByPrimaryKey(user) > 0) {
            return new ResultTo();
        }
        return new ResultTo(ResultEnum.OPERATION_FAILED);
    }

    /**
     * 增加用户页面跳转
     *
     * @return 增加用户页面
     */
    @GetMapping(value = "/add")
    @ApiResponses(value = {@ApiResponse(code = 405, message = "请求类型异常"), @ApiResponse(code = 500, message = "服务器异常")})
    public ResultTo addUserPage() {
        return new ResultTo().setData(roleService.selectAll());
    }

    /**
     * 增加用实现
     *
     * @param user   User对象
     * @param result 错误信息
     * @param orgId  机构ID
     * @return 成功或失败
     */
    @PostMapping
    @ApiOperation(value = "增加用实现", httpMethod = "POST")
    public ResultTo addUser(User user, BindingResult result, @RequestParam("orgId") Integer orgId,
                            @RequestParam("roleId") Integer roleId) {
        if (orgId == null || roleId == null) {
            LogTemplate.info(this.getClass(), "orgId", orgId);
            LogTemplate.info(this.getClass(), "roleId", roleId);
            return new ResultTo(ResultEnum.PARAM_ERROR);
        }
        Map<String, String> map = new HashedMap();
        if (ErrorMessageUtil.setValidErrorMessage(map, result)) {
            return new ResultTo(ResultEnum.PARAM_ERROR).setData(map);
        }
        if (userService.addUser(user, orgId, roleId)) {
            return new ResultTo();
        }
        return new ResultTo(ResultEnum.OPERATION_FAILED);
    }

    /**
     * 编辑用户页面跳转
     *
     * @param userId 用户ID
     * @return 编辑用户页面
     */
    @RequestMapping(value = "/{userId}/updateUser", method = RequestMethod.GET)
    @ApiOperation(value = "编辑用户页面跳转", httpMethod = "GET")
    public ResultTo updateUserPage(@ApiParam("用户ID") @PathVariable("userId") Integer userId) {
        UserUpdateVo userUpdateVo = userService.selectUserUpdateVo(userId);
        return new ResultTo().setData(userUpdateVo);
    }

    /**
     * 编辑用户页面实现
     *
     * @param user  User对象
     * @param orgId 机构Id
     * @return 成功或失败
     */
    @PutMapping
    @ApiOperation(value = "编辑用户实现", httpMethod = "PUT")
    public ResultTo updateUser(User user, BindingResult result, @RequestParam(value = "orgId", required = false) String orgId,
                               @RequestParam(value = "roleId", required = false) String roleId) {
        LogTemplate.debug(this.getClass(), "orgId", orgId);
        LogTemplate.debug(this.getClass(), "roleId", roleId);
        Map<String, String> map = new HashedMap();
        if (ErrorMessageUtil.setValidErrorMessage(map, result)) {
            return new ResultTo(ResultEnum.PARAM_ERROR).setData(map);
        }
        String[] orgIds = new String[0], roleIds = new String[0];
        if (orgId != null) {
            orgIds = orgId.split(",");
            if (orgIds.length != 2) {
                return new ResultTo(ResultEnum.PARAM_ERROR);
            }
        }
        if (roleId != null) {
            roleIds = roleId.split(",");
            if (roleIds.length != 2) {
                return new ResultTo(ResultEnum.PARAM_ERROR);
            }
        }
        if (userService.updateUser(user, orgIds, roleIds)) {
            return new ResultTo();
        }
        return new ResultTo(ResultEnum.OPERATION_FAILED);
    }

    /**
     * 用户修改密码
     *
     * @param oldPassword 原始密码
     * @param newPassword 新密码
     * @return 成功或失败
     */
    @PostMapping(value = "/updatePassword")
    @ApiOperation(value = "修改密码", httpMethod = "POST")
    public ResultTo updatePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        LogTemplate.debug(this.getClass(), "oldPassword", oldPassword);
        LogTemplate.debug(this.getClass(), "newPassword", PasswordUtil.encryptPassword(newPassword));
        if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)) {
            return new ResultTo(ResultEnum.REQUIRED_PARAMETER_EMPTY);
        }
        return userService.updatePassword(oldPassword, newPassword);
    }

    /**
     * 根据登录名查询用户
     *
     * @param userName 用户名
     * @return 不存在返回null, 存在返回用户Id
     */
    @RequestMapping(value = "/anon/resetPassword/{userName}", method = RequestMethod.GET)
    @ApiOperation(value = "根据登录名查询用户", httpMethod = "GET", notes = "不存在返回null, 存在返回用户Id")
    public ResultTo isHaveLoginName(@PathVariable("userName") String userName) {
        LogTemplate.debug(this.getClass(), "userName", userName);
        User user = new User();
        user.setUsername(userName);
        User userOne = userService.selectOne(user);
        return new ResultTo().setData(userOne == null ? null : userOne.getId());
    }

    /**
     * 发送验证码
     *
     * @param address 用户Email或Phone
     * @return 成功或失败
     */
    @PostMapping(value = "/anon/resetPassword/sendCode")
    @ApiOperation(value = "发送验证码", httpMethod = "POST")
    public ResultTo sendCode(@RequestParam("address") String address) {
        LogTemplate.debug(this.getClass(), "address", address);
        return userService.sendCode(address);
    }

    /**
     * 验证验证码
     *
     * @param userId 用户ID
     * @param code   验证码
     * @return 成功或失败
     */
    @PostMapping(value = "/anon/resetPassword", params = "userId")
    @ApiOperation(value = "验证验证码", httpMethod = "POST")
    public ResultTo checkedCode(@RequestParam("userId") Integer userId, @RequestParam("code") String code) {
        LogTemplate.debug(this.getClass(), "userId", userId);
        LogTemplate.debug(this.getClass(), "code", code);
        return userService.checkedCode(userId, code);
    }

    /**
     * 重置密码
     *
     * @param checkedCode Api接口验证
     * @param password    密码
     * @return 成功或失败
     */
    @RequestMapping(value = "/anon/resetPassword/", method = RequestMethod.POST, params = "checkedCode")
    @ApiOperation(value = "重置密码", httpMethod = "POST")
    public ResultTo resetPassword(@RequestParam("checkedCode") String checkedCode, @RequestParam("password") String password) {
        LogTemplate.debug(this.getClass(), "checkedCode", checkedCode);
        LogTemplate.debug(this.getClass(), "password", password);
        return userService.resetPassword(checkedCode, password);
    }

    /**
     * 用户身份证验重
     *
     * @param idCard 身份证号码
     * @return 存在返回用户ID, 不存在返回null
     */
    @PostMapping("/isIdCard")
    @ApiOperation(value = "用户身份证号码验重", httpMethod = "POST")
    public ResultTo isIdCard(@RequestParam("idCard") String idCard) {
        LogTemplate.debug(this.getClass(), "idCard", idCard);
        User user = new User();
        user.setIdCardNumber(idCard);
        if (!IDcardUtil.verify(idCard)) {
            return new ResultTo(ResultEnum.ID_CARD_ERROR);
        }
        User selectUser = userService.selectOne(user);
        return new ResultTo().setData(selectUser == null ? null : selectUser.getId());
    }

    /**
     * 用户手机号验重
     *
     * @param phone 手机号
     * @return 存在返回用户ID, 不存在返回null
     */
    @PostMapping("/isPhone")
    @ApiOperation(value = "用户手机号验重", httpMethod = "POST")
    public ResultTo isPhone(@RequestParam("phone") String phone) {
        LogTemplate.debug(this.getClass(), "phone", phone);
        User user = new User();
        user.setPhone(phone);
        User selectUser = userService.selectOne(user);
        return new ResultTo().setData(selectUser == null ? null : selectUser.getId());
    }

    /**
     * 用户Email验重
     *
     * @param email E-Mail
     * @return 存在返回用户ID, 不存在返回null
     */
    @PostMapping("/isEmail")
    @ApiOperation(value = "用户Email验重", httpMethod = "POST")
    public ResultTo isEmail(@RequestParam("email") String email) {
        LogTemplate.debug(this.getClass(), "email", email);
        User user = new User();
        user.setEmail(email);
        User selectUser = userService.selectOne(user);
        return new ResultTo().setData(selectUser == null ? null : selectUser.getId());
    }
}
