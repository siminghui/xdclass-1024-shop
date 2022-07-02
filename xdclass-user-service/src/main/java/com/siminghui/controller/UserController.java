package com.siminghui.controller;


import com.siminghui.enums.BizCodeEnum;
import com.siminghui.service.FileService;
import com.siminghui.util.JsonData;
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

}

