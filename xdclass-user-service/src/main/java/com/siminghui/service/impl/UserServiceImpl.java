package com.siminghui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.siminghui.enums.BizCodeEnum;
import com.siminghui.enums.SendCodeEnum;
import com.siminghui.mapper.UserMapper;
import com.siminghui.model.LoginUser;
import com.siminghui.model.UserDO;
import com.siminghui.request.UserLoginRequest;
import com.siminghui.request.UserRegisterRequest;
import com.siminghui.service.NotifyService;
import com.siminghui.service.UserService;
import com.siminghui.util.CommonUtil;
import com.siminghui.util.JWTUtil;
import com.siminghui.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: 十七
 * @Date: 2022/7/2 4:38 下午
 * @Description:
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册
     *
     * 1.邮箱验证码验证
     * 2.密码加密
     * 3.账号唯一性检查
     * 4.插入数据库
     * 5.新注册用户福利发放
     * @param request
     * @return
     */
    @Override
    public JsonData register(UserRegisterRequest request) {

        boolean checkCode = false;
        if (StringUtils.isNotBlank(request.getMail())) {
            // 验证验证码
            checkCode = notifyService.checkCode(SendCodeEnum.USER_REGISTER, request.getMail(), request.getCode());
        }
        if (!checkCode) {return JsonData.buildResult(BizCodeEnum.CODE_ERROR);}

        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(request, userDO);

        userDO.setCreateTime(new Date());
        userDO.setSlogan("做人要潇洒一点");

        // 设置密码
        // 生成秘钥 盐
        userDO.setSecret("$1$" + CommonUtil.getStringNumRandom(8));

        // 密码+盐 处理
        String cryptPwd = Md5Crypt.md5Crypt(request.getPwd().getBytes(), userDO.getSecret());
        userDO.setPwd(CommonUtil.MD5(cryptPwd));

        // todo 唯一性检查
        if (checkUnique(userDO.getMail())) {
            int rows = userMapper.insert(userDO);
            log.info("rows:{},注册成功", rows, userDO.toString());

            // 新用户注册成功，初始化信息，发放福利等
            userRegisterInitTask(userDO);
            return JsonData.buildSuccess();
        } else {
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_REPEAT);
        }

    }

    /**
     * 用户登录
     *
     * 1.根据mail找有无这条记录
     * 2.有则用秘钥（盐）+  密码 加密，和数据库密码匹配
     * @param request
     * @return
     */
    @Override
    public JsonData login(UserLoginRequest request) {

        List<UserDO> userDOList = userMapper.selectList(new QueryWrapper<UserDO>().eq("mail", request.getMail()));

        if (Objects.nonNull(userDOList) && userDOList.size() == 1) {
            // 已经注册
            UserDO userDO = userDOList.get(0);
            String crypt = Md5Crypt.md5Crypt(request.getPwd().getBytes(), userDO.getSecret());

            if (Objects.equals(crypt, userDO.getPwd())) {
                // 登陆成功，生成token
                LoginUser loginUser = new LoginUser();
                BeanUtils.copyProperties(userDO, loginUser);
                String token = JWTUtil.geneJsonWebToken(loginUser);
                // 1. UUID -> token , 存储redis并设置过期时间
                // 2.JWT
                return JsonData.buildSuccess(token);
            } else {
                return JsonData.buildResult(BizCodeEnum.ACCOUNT_PWD_ERROR);
            }

        } else {
            // 未注册
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_PWD_ERROR);
        }

    }

    /**
     * 用户唯一性检查
     * @param mail
     * @return
     */
    private boolean checkUnique(String mail) {

        return true;
    }

    /**
     * 用户注册，初始化福利信息 todo
     * @param userDO
     */
    private void userRegisterInitTask(UserDO userDO) {

    }
}
