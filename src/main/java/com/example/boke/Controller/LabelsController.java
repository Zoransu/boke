package com.example.boke.Controller;

import com.example.boke.Dto.LabelsCloudDto;
import com.example.boke.Service.LabelsService;
import com.example.boke.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/Labels")
public class LabelsController {
    @Autowired
    private LabelsService labelsService;

    @GetMapping("/LabelsCloud")
    public Result LabelsCloud(){
        ArrayList<LabelsCloudDto> list=labelsService.LabelsCloud();
        return Result.success(list);
    }


}
