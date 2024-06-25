package com.example.boke.Service;

import com.example.boke.Dto.ArticleDetailsDto;
import com.example.boke.Dto.ArticleDto;

import java.util.ArrayList;
import java.util.List;

public interface ArticleService {
    void createArticle(ArticleDto articleDto);

    ArticleDetailsDto getArticleDetails(Long articleId);

    ArrayList<ArticleDetailsDto> getHotTen();

    ArrayList<ArticleDetailsDto> getArticles(int page);

    List<ArticleDetailsDto> getArticlesByLabels(List<String> labelList,int page);

    void deleteArticle(Long articleId);

    List<ArticleDetailsDto> getArticleByUserId(Long userId,int page);

    List<ArticleDetailsDto> getMyArticlesByLabels(List<String> labelList, Long userId,int page);

    List<ArticleDetailsDto> getArticlesBySearch(String keyword,int page);

    List<ArticleDetailsDto> getMyArticlesBySearch(String keyword, Long userId,int page);

    List<String> getAllTitle();

    Integer getSize();

    void updateLike(Long articleid, int like);

    int getMyArticlesBySearchSize(String keyword, Long userId);

    int getLabelsSize(List<String> labelList);

    int getArticleByUserIdSize(Long userId);

    int getMyLabelsSize(List<String> labelList, Long userId);

    int getArticlesBySearchSize(String keyword);
}
