package com.example.boke.Mapper;

import com.example.boke.Dto.ArticleDetailsDto;
import com.example.boke.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ArticleMapper {
    void createArticle(Article article);

    void assignLabelToArticle(Long articleId, String labelName);

    ArticleDetailsDto findArticleDetailsById(Long articleId);

    ArrayList<ArticleDetailsDto> getHotTen();

    ArrayList<ArticleDetailsDto> getArticles(@Param("offset") int offset);

    List<ArticleDetailsDto> getArticlesByLabels(@Param("labelList")List<String> labelList,int offset);

    void deleteArticle(Long articleId);

    List<ArticleDetailsDto> getArticleByUserId(Long userId,int offset);

    List<ArticleDetailsDto> getMyArticlesByLabels(@Param("labelList")List<String> labelList, Long userId,int offset);

    List<ArticleDetailsDto> getArticlesBySearch(String keyword,int offset);

    List<ArticleDetailsDto> getMyArticlesBySearch(String keyword, Long userId,int offset);

    List<String> getAllTitle();

    Integer getSize();

    void updateLike(Long articleId, int like);

    int getMyArticlesBySearchSize(String keyword, Long userId);

    int getLabelsSize(List<String> labelList);

    int getArticleByUserIdSize(Long userId);

    int getMyLabelsSize(List<String> labelList, Long userId);

    int getArticlesBySearchSize(String keyword);
}
