package com.surpass.ws.endpoint;

import com.surpass.bean.AddUserRes;
import com.surpass.entity.User;
import com.surpass.service.UserService;
import com.surpass.ws.bean.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.annotation.Resource;

/**
 * Endpoint: 相当于Controller
 * <p>
 * Created by surpass.wei@gmail.com on 2017/8/14.
 */
@Endpoint
public class UserEndpoint {
    @Resource
    private UserService userService;

    private static final String NAMESPACE_URI = "http://www.zer.com/ws";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddUserRequest")
    @ResponsePayload
    public AddUserResponse addUser(@RequestPayload AddUserRequest request) {
        AddUserResponse response = new AddUserResponse();

        AddUserRes addUserRes = userService.addUser(request.getUsername(), request.getPassword(), request.getNickname());

        response.setSuccess(addUserRes.isSuccess());
        response.setMsg(addUserRes.getMsg());
        if (addUserRes.getUserId() != null) {
            response.setUserId(addUserRes.getUserId());
        }
        response.setEncryptPassword(addUserRes.getEncryptPassword());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CheckUsernameRequest")
    @ResponsePayload
    //@PreAuthorize("hasRole('APP')")   //  可以对endPoint的访问设置权限
    public CheckUsernameResponse checkUsername(@RequestPayload CheckUsernameRequest request) {
        CheckUsernameResponse response = new CheckUsernameResponse();

        boolean isLegal = userService.userNameIsLegal(request.getUsername());
        boolean isExist = userService.userNameIsExist(request.getUsername());
        if (!isLegal) {
            response.setMsg("用户名非法（应当为字母开头，4~15个字符）");
            response.setSuccess(false);
            return response;
        } else if (isExist) {
            response.setMsg("用户名已存在");
            response.setSuccess(false);
            return response;
        }
        response.setSuccess(true);
        response.setMsg("用户名可用");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdatePasswordRequest")
    @ResponsePayload
    public UpdatePasswordResponse updatePassword(@RequestPayload UpdatePasswordRequest request) {
        UpdatePasswordResponse response = new UpdatePasswordResponse();

        User user = userService.updatePassword(request.getUsername(), request.getPassword());
        if (user == null) {
            response.setSuccess(false);
            response.setMsg("用户名不存在");
        } else {
            response.setSuccess(true);
            response.setMsg("更新密码成功");
            response.setEncryptPassword(user.getPassword());
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateNicknameRequest")
    @ResponsePayload
    public UpdateNicknameResponse updateNickname(@RequestPayload UpdateNicknameRequest request) {
        UpdateNicknameResponse response = new UpdateNicknameResponse();
        User user = userService.updateNickname(request.getUsername(), request.getNickname());
        if (user == null) {
            response.setSuccess(false);
            response.setMsg("用户名不存在");
        } else {
            response.setSuccess(true);
            response.setMsg("更新昵称成功");
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateLoginTimeRequest")
    @ResponsePayload
    public UpdateLoginTimeResponse updateNickname(@RequestPayload UpdateLoginTimeRequest request) {
        UpdateLoginTimeResponse response = new UpdateLoginTimeResponse();
        User user = userService.updateLoginTime(request.getUsername());
        if (user == null) {
            response.setSuccess(false);
            response.setMsg("用户名不存在");
        } else {
            response.setSuccess(true);
            response.setMsg("更新成功");
        }
        return response;
    }
}
