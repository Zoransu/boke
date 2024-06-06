package com.example.boke.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabelsCloudDto {
    private Long labelId;
    private String labelName;
    private int articleCount;
}
