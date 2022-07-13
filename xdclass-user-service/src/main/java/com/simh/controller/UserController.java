package com.simh.controller;


import com.simh.enums.BizCodeEnum;
import com.simh.request.UserLoginRequest;
import com.simh.request.UserRegisterRequest;
import com.simh.service.FileService;
import com.simh.service.UserService;
import com.simh.util.JsonData;
import com.simh.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 十七
 * @since 2022-04-22
 */
@Api(tags = "用户模块")
@RestController
@RequestMapping("/api/user/v1")
public class UserController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    /**
     * 上传用户的头像
     *
     * 默认最大是1M，超过则报错
     * @param file
     * @return
     */
    @ApiOperation("用户头像上传")
    @PostMapping("/upload")
    public JsonData uploadUserImg(@ApiParam(value = "文件上传", required = true)
                                      @RequestPart("file")MultipartFile file) {

        String result = fileService.uploadUserImg(file);

        return result != null? JsonData.buildSuccess(result) : JsonData.buildResult(BizCodeEnum.FILE_UPLOAD_USER_IMG_FAIL);

    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public JsonData register(@ApiParam("用户注册对象") @RequestBody UserRegisterRequest request) {

        JsonData jsonData = userService.register(request);

        return jsonData;
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public JsonData login(@ApiParam("用户登录对象") @RequestBody UserLoginRequest request) {

        JsonData jsonData = userService.login(request);

        return jsonData;
    }

    @ApiOperation("个人信息查询")
    @GetMapping("/detail")
    public JsonData detail() {

        UserVO userVO = userService.findUserDetail();

        return JsonData.buildSuccess(userVO);

    }

}

