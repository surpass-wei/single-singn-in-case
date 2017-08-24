package com.surpass.controller;


import com.surpass.bean.AddUserRes;
import com.surpass.bean.CommonRes;
import com.surpass.entity.User;
import com.surpass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理
 * <p>
 * Created by surpass.wei@gmail.com on 2017/8/3.
 */
@RestController
public class UserController {
    private final UserService userService;

    @Value("${default.user.password}")
    private String defaultPassword;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "users/{username}/is-usable")
    public CommonRes checkUsername(@PathVariable String username) {
        CommonRes commonRes = new CommonRes();
        boolean matches = userService.userNameIsLegal(username);
        boolean isExist = userService.userNameIsExist(username);
        if (!matches) {
            commonRes.setSuccess(false);
            commonRes.setMsg("用户名不符合规范");
            return commonRes;
        }
        if (isExist) {
            commonRes.setSuccess(false);
            commonRes.setMsg("用户名已存在");
            return commonRes;
        }
        commonRes.setSuccess(true);
        commonRes.setMsg("用户名可用");
        return commonRes;
    }

    @RequestMapping(value = "users", method = RequestMethod.POST)
    public CommonRes create(@RequestParam String username,
                            @RequestParam String nickname,
                            @RequestParam(required = false) String password) {
        CommonRes commonRes = new CommonRes();
        AddUserRes addUserRes = userService.addUser(username, password, nickname);
        commonRes.setSuccess(addUserRes.isSuccess());
        commonRes.setMsg(addUserRes.getMsg());
        commonRes.getData().put("userId", addUserRes.getUserId());
        return commonRes;
    }

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public CommonRes get(String search, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC)
            Pageable pageable) {
        CommonRes commonRes = new CommonRes();
        Page<User> pageInfo = userService.findByPage(search, pageable);

        commonRes.setSuccess(true);
        commonRes.getData().put("data", pageInfo.getContent());
        commonRes.getData().put("total", pageInfo.getTotalElements());
        return commonRes;
    }

    @RequestMapping(value = "users/{id}/reset", method = RequestMethod.GET)
    public CommonRes restUser(@PathVariable Long id) {
        CommonRes commonRes = new CommonRes();
        boolean isOk = userService.resetUser(id);
        commonRes.setSuccess(isOk);
        if (isOk) {
            commonRes.setMsg("重置成功");
        } else {
            commonRes.setMsg("重置失败");
        }
        return commonRes;
    }

    @RequestMapping(value = "users/{id}/freeze/{isFreeze}", method = RequestMethod.GET)
    public CommonRes freezeUser(@PathVariable Long id,
                                @PathVariable Boolean isFreeze) {
        CommonRes commonRes = new CommonRes();
        boolean isOk = userService.freezeUser(id, isFreeze);
        commonRes.setSuccess(isOk);
        if (isOk) {
            commonRes.setMsg("冻结成功");
        } else {
            commonRes.setMsg("冻结失败");
        }
        return commonRes;
    }
}
