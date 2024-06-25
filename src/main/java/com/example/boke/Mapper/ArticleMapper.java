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

    List<ArticleDetailsDto> getArticlesByLabels(List<String> labelList,int offset);

    void deleteArticle(Long articleId);

    List<ArticleDetailsDto> getArticleByUserId(Long userId,int offset);

    List<ArticleDetailsDto> getMyArticlesByLabels(List<String> labelList, Long userId,int offset);

    List<ArticleDetailsDto> getArticlesBySearch(String keyword,int offset);

    List<ArticleDetailsDto> getMyArticlesBySearch(String keyword, Long userId,int offset);

    List<String> getAllTitle();

    Integer getSize();

    void updateLike(Long articleId, int like);
}
