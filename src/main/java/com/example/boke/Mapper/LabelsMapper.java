package com.example.boke.Mapper;

import com.example.boke.Dto.LabelsCloudDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface LabelsMapper {

    ArrayList<LabelsCloudDto> LabelsCloud();
}
