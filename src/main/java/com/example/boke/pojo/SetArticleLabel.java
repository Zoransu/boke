package com.example.boke.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetArticleLabel {
    private Long articleId;           // 文章ID
    private Long labelId;             // 标签ID
}
