package com.example.boke.Service;

import com.example.boke.Dto.ArticleDetailsDto;
import com.example.boke.Dto.ArticleDto;

import java.util.ArrayList;

public interface ArticleService {
    void createArticle(ArticleDto articleDto);

    ArticleDetailsDto getArticleDetails(Long articleId);

    ArrayList<ArticleDetailsDto> getLastTen();

    ArrayList<ArticleDetailsDto> getHotTen();

    ArrayList<ArticleDetailsDto> getArticles(int page, int size);
}
