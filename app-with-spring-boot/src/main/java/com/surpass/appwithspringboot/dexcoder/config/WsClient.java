package com.surpass.appwithspringboot.dexcoder.config;

import com.surpass.appwithspringboot.dexcoder.ws.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

/**
 * web service 客户端
 * <p>
 * Created by surpass.wei@gmail.com on 2017/8/14.
 */
public class WsClient extends WebServiceGatewaySupport {
    private String wsUserUri;

    /**
     * @param wsUserUri // 用户相关操作的 schema uri
     */
    public WsClient(String wsUserUri) {
        this.wsUserUri = wsUserUri;
    }

    /**
     * 添加用户
     *
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @return
     */
    public AddUserResponse addUser(String username, String password, String nickname) {
        AddUserRequest request = new AddUserRequest();
        request.setUsername(username);
        request.setPassword(password);
        request.setNickname(nickname);
        return (AddUserResponse) getWebServiceTemplate().marshalSendAndReceive(
                wsUserUri, request);
    }

    /**
     * 检查用户名是否可用
     *
     * @param username 用户名
     * @return
     */
    public CheckUsernameResponse checkUsername(String username) {
        CheckUsernameRequest request = new CheckUsernameRequest();
        request.setUsername(username);
        return (CheckUsernameResponse) getWebServiceTemplate().marshalSendAndReceive(
                wsUserUri, request);
    }

    /**
     * 更新用户密码
     *
     * @param username 用户名
     * @param pwd
     * @return
     */
    public UpdatePasswordResponse updatePassword(String username, String pwd) {
        UpdatePasswordRequest request = new UpdatePasswordRequest();
        request.setUsername(username);
        request.setPassword(pwd);
        return (UpdatePasswordResponse) getWebServiceTemplate().marshalSendAndReceive(
                wsUserUri, request);
    }

    /**
     * 更新用户昵称
     *
     * @param username 用户名
     * @param nickname 昵称
     * @return
     */
    public UpdateNicknameResponse updateNickname(String username, String nickname) {
        UpdateNicknameRequest request = new UpdateNicknameRequest();
        request.setUsername(username);
        request.setNickname(nickname);
        return (UpdateNicknameResponse) getWebServiceTemplate().marshalSendAndReceive(
                wsUserUri, request);
    }

    /**
     * 更新用户登录时间
     *
     * @param username 用户名
     * @return
     */
    public UpdateLoginTimeResponse updateLoginTime(String username) throws Exception {
        UpdateLoginTimeRequest request = new UpdateLoginTimeRequest();
        request.setUsername(username);
        return (UpdateLoginTimeResponse) getWebServiceTemplate().marshalSendAndReceive(
                wsUserUri, request);
    }
}
