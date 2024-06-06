package com.example.boke.Service.ServiceImpl;

import com.example.boke.Dto.LabelsCloudDto;
import com.example.boke.Mapper.LabelsMapper;
import com.example.boke.Service.LabelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LabelsServiceImpl implements LabelsService {

    @Autowired
    private LabelsMapper labelsMapper;
    @Override
    public ArrayList<LabelsCloudDto> LabelsCloud() {
        return labelsMapper.LabelsCloud();
    }
}
