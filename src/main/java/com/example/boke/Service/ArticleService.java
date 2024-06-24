package com.example.boke.Service;

import com.example.boke.Dto.ArticleDetailsDto;
import com.example.boke.Dto.ArticleDto;

import java.util.ArrayList;
import java.util.List;

public interface ArticleService {
    void createArticle(ArticleDto articleDto);

    ArticleDetailsDto getArticleDetails(Long articleId);

    ArrayList<ArticleDetailsDto> getLastTen();

    ArrayList<ArticleDetailsDto> getHotTen();

    ArrayList<ArticleDetailsDto> getArticles(int page, int size);

    List<ArticleDetailsDto> getArticlesByLabels(List<String> labelList);

    void deleteArticle(Long articleId);

    List<ArticleDetailsDto> getArticleByUserId(Long userId);

    List<ArticleDetailsDto> getMyArticlesByLabels(List<String> labelList, Long userId);

    List<ArticleDetailsDto> getArticlesBySearch(String keyword);

    List<ArticleDetailsDto> getMyArticlesBySearch(String keyword, Long userId);
}
