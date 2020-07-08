package com.lujian.eureka_admin.service.impl;


import com.lujian.eureka_admin.entity.Result;
import com.lujian.eureka_admin.entity.User;
import com.lujian.eureka_admin.mapper.UserMapper;
import com.lujian.eureka_admin.service.UserService;
import com.lujian.eureka_admin.util.JavaTool;
import com.lujian.eureka_admin.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    @Override
    public Result<?> insertUserMessage(User user) {
        User u = userMapper.queryUserMessageByTel(user);
        if (u != null) {
            return Result.error(501, "手机账号已注册");
        } else {
            String currentTime = Utils.getCurrent();
            user.setCreattime(currentTime);
            if (user.getPassword() != null && !user.getPassword().equals("")) {
                user.setPassword(JavaTool.string2MD5(user.getPassword()));
            }
            int radom = (int) (1 + Math.random() * (10 - 1 + 1));
            String pic = "/image/user/" + radom + ".jpg";
            user.setUpic(pic);
            int row = userMapper.insertUserMessage(user);
            if (row > 0) {
                return Result.success("注册成功");
            } else {
                return Result.error(500, "注册失败");
            }
        }

    }

    @Override
    public Result<?> updateUserMessage(User user) {
        User u = userMapper.queryUserMessageByTel(user);
        if (u != null) {
            if (user.getPassword() != null && !user.getPassword().equals("")) {
                user.setPassword(JavaTool.string2MD5(user.getPassword()));
            }
            int row = userMapper.updateUserMessage(user);
            if (row > 0) {
                return Result.success("修改成功");
            } else {
                return Result.error(500, "修改失败");
            }
        } else {
            return Result.error(501, "手机号不存在");
        }

    }

    @Override
    public Result<?> updateUserMessageByUid(User user) {
        int row = userMapper.updateUserMessage(user);
        if (row > 0) {
            return Result.success(user);
        } else {
            return Result.error(500, "服务器错误");
        }
    }

    @Override
    public Result<?> loginUserByTelOrPassWord(User user, String code) {
        User u = userMapper.queryUserMessageByTel(user);
        if (u != null) {//账号存在
            if (code != null && !code.equals("")) { //验证码登录
                String checkCode = stringRedisTemplate.opsForValue().get(user.getUtelphone());
                if (checkCode.equals(code)) {
                    u.setPassword("");
                    return Result.success(u);
                } else {
                    return Result.error(500, "验证码不正确");
                }
            } else {//账号密码登录
                if (JavaTool.string2MD5(user.getPassword()).equals(u.getPassword())) {
                    int frequency = u.getFrequency();
                    user.setFrequency(frequency + 1);
                    user.setPassword("");
                    userMapper.updateUserMessage(user);
                    u.setFrequency(frequency);
                    u.setPassword("");
                    return Result.success(u);
                } else {
                    return Result.error(502, "密码不正确");
                }
            }

        } else {
            return Result.error(501, "手机号不存在");
        }
    }

    @Override
    public List<User> queryUserByUidAndPid(int uid, int pid) {
        return userMapper.queryUserByUidAndPid(uid, pid);
    }

    @Override
    public Result<?> queryUserByUidOrTel(User user) {
        return Result.success(userMapper.queryUserByUidOrTel(user));
    }

    @Override
    public Result<?> queryImageUrls() {
        return Result.success(userMapper.queryImageUrls());
    }

}
