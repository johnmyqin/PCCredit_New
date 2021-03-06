package com.cardpay.mgt.user.service.impl;


import com.cardpay.basic.base.model.ResultTo;
import com.cardpay.basic.base.service.impl.BaseServiceImpl;
import com.cardpay.basic.common.constant.Constant;
import com.cardpay.basic.common.enums.ResultEnum;
import com.cardpay.basic.common.log.LogTemplate;
import com.cardpay.basic.mail.MailSend;
import com.cardpay.basic.redis.RedisClient;
import com.cardpay.basic.redis.enums.RedisKeyPrefixEnum;
import com.cardpay.basic.util.VerifyCodeUtil;
import com.cardpay.core.shiro.common.PasswordUtil;
import com.cardpay.core.shiro.common.ShiroKit;
import com.cardpay.mgt.user.dao.RoleMapper;
import com.cardpay.mgt.user.dao.UserMapper;
import com.cardpay.mgt.user.model.Role;
import com.cardpay.mgt.user.model.User;
import com.cardpay.mgt.user.model.UserAuthority;
import com.cardpay.mgt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 用户Service层实现
 *
 * @author rankai
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private MailSend mailSend;

    @Override
    public Set<String> getUserAuthority(User user) {
        Set<String> set = new HashSet<>();
        List<UserAuthority> list = userMapper.selectByAuthority(user);
        StringBuffer stringBuffer;
        for (UserAuthority userAuthority : list) {
            stringBuffer = new StringBuffer();
            stringBuffer.append(userAuthority.getAuthorityName()).append(":").append(userAuthority.getResoucreName())
                    .append(":").append(userAuthority.getOperationName());
            set.add(stringBuffer.toString());
        }
        for (String str : set) {
            LogTemplate.debug(this.getClass(), "拥有的资源", str);
        }
        return set;
    }

    @Override
    public Set<String> getUserRole(User user) {
        List<Role> roles = roleMapper.selectByUser(user);
        Set<String> set = new HashSet<>();
        for (Role role : roles) {
            set.add(role.getRoleName());
        }
        for (String str : set) {
            LogTemplate.debug(this.getClass(), "拥有的角色", str);
        }
        return set;
    }

    @Override
    public ResultTo updatePassword(String oldPassword, String newPassword) {
        User user = (User) ShiroKit.getPrincipal();
        Integer userId = user.getId();
        if (!user.getPassword().equals(PasswordUtil.encryptPassword(oldPassword))) {
            LogTemplate.debug(this.getClass(), "oldPassword(数据库)", user.getPassword());
            LogTemplate.debug(this.getClass(), "oldPassword(传入)", PasswordUtil.encryptPassword(oldPassword));
            return new ResultTo(ResultEnum.OLD_PASSWORD_ERROR);
        }
        user = new User();
        user.setId(userId);
        user.setPassword(PasswordUtil.encryptPassword(newPassword));
        if (userMapper.updateByPrimaryKeySelective(user) == 0) {
            LogTemplate.info(this.getClass(), "message", "修改密码失败");
            return new ResultTo(ResultEnum.OPERATION_FAILED);
        }
        return new ResultTo();
    }

    @Override
    public ResultTo sendCode(Integer userId, String address) {
        if (Pattern.compile(Constant.REGEX_EMAIL).matcher(address).matches()) {
            LogTemplate.debug(this.getClass(), "message", "匹配到Email类型");
            User user = userMapper.selectByPrimaryKey(userId);
            if (user != null && user.getEmail().equals(address)) {
                String code = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_ONLY, 6, null);
                LogTemplate.debug(this.getClass(), "code", code);
                redisClient.set(RedisKeyPrefixEnum.USER, user.getEmail(), code, Constant.TIME_OUT);
                mailSend.send(user.getEmail(), code);
                return new ResultTo().setData(user.getEmail());
            }
            return new ResultTo(ResultEnum.BOUND_MAILBOX_ERROR);
        }
        if (Pattern.compile(Constant.REGEX_PHONE).matcher(address).matches()) {
            LogTemplate.debug(this.getClass(), "message", "匹配到Phone类型");
            User user = userMapper.selectByPrimaryKey(userId);
            if (user != null && user.getTel().equals(address)) {
                String code = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_ONLY, 6, null);
                LogTemplate.debug(this.getClass(), "code", code);
                redisClient.set(RedisKeyPrefixEnum.USER, user.getEmail(), code, Constant.TIME_OUT);
                //预留手机验证
                return new ResultTo().setData(user.getTel());
            }
            return new ResultTo(ResultEnum.BOUND_PHONE_ERROR);
        }
        return new ResultTo(ResultEnum.NUMBER_ERROR);
    }

    @Override
    public ResultTo checkedCode(String address, String code) {
        Object object = redisClient.get(RedisKeyPrefixEnum.USER, address);
        if (object == null) {
            return new ResultTo(ResultEnum.CAPTCHA_TIMEOUT);
        }
        if (object.toString().equals(code)) {
            String checkedCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_LOWER, 16, null);
            redisClient.set(RedisKeyPrefixEnum.USER, checkedCode, "checkedCode", Constant.API_TIMEOUT);
            LogTemplate.debug(this.getClass(), "checkedCode", checkedCode);
            return new ResultTo().setData(checkedCode);
        }
        return new ResultTo(ResultEnum.CAPTCHA_ERROR);
    }

    @Override
    public ResultTo resetPassword(Integer userId, String checkedCode, String password) {
        Object object = redisClient.get(RedisKeyPrefixEnum.USER, checkedCode);
        if (object == null) {
            return new ResultTo(ResultEnum.API_TIMEOUT);
        }
        User user = new User();
        user.setId(userId);
        user.setPassword(PasswordUtil.encryptPassword(password));
        if (userMapper.updateByPrimaryKeySelective(user) == 0) {
            return new ResultTo(ResultEnum.OPERATION_FAILED);
        }
        return new ResultTo();
    }
}
