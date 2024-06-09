package com.example.boke.Controller;

import com.example.boke.Dto.LabelsCloudDto;
import com.example.boke.Service.LabelsService;
import com.example.boke.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/Labels")
@Api(tags = "标签管理")
public class LabelsController {
    @Autowired
    private LabelsService labelsService;

    @GetMapping("/LabelsCloud")
    @ApiOperation(value = "获取标签词云", notes = "获取标签词云信息")
    public Result LabelsCloud(){
        ArrayList<LabelsCloudDto> list=labelsService.LabelsCloud();
        log.info("标签词云{}",list);
        return Result.success(list);
    }


}
