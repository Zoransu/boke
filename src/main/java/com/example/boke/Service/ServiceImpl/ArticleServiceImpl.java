package com.example.boke.Service.ServiceImpl;

import com.example.boke.Dto.ArticleDetailsDto;
import com.example.boke.Dto.ArticleDto;
import com.example.boke.Mapper.ArticleMapper;
import com.example.boke.Service.ArticleService;
import com.example.boke.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void createArticle(ArticleDto articleDto) {
        Article article = new Article(null, articleDto.getUserId(), articleDto.getTitle(),
                articleDto.getContent(), 0L, new Date(), 0L);
        articleMapper.createArticle(article);

        if (articleDto.getLabelNames() != null && !articleDto.getLabelNames().isEmpty()) {
            for (String labelName : articleDto.getLabelNames()) {
                articleMapper.assignLabelToArticle(article.getArticleId(), labelName);
            }
        }
    }

    @Override
    public ArticleDetailsDto getArticleDetails(Long articleId) {
        return articleMapper.findArticleDetailsById(articleId);
    }

    @Override
    public ArrayList<ArticleDetailsDto> getHotTen() {
        return articleMapper.getHotTen();
    }

    @Override
    public ArrayList<ArticleDetailsDto> getArticles(int page) {
        int offset = (page - 1) * 4;
        return articleMapper.getArticles(offset);
    }

    @Override
    public List<ArticleDetailsDto> getArticlesByLabels(List<String> labelList,int page) {
        int offset = (page - 1) * 4;
        return articleMapper.getArticlesByLabels(labelList,offset);
    }

    @Override
    public void deleteArticle(Long articleId) {
        articleMapper.deleteArticle(articleId);
    }

    @Override
    public List<ArticleDetailsDto> getArticleByUserId(Long userId,int page) {
        int offset = (page - 1) * 4;
        return articleMapper.getArticleByUserId(userId,offset);
    }

    @Override
    public List<ArticleDetailsDto> getMyArticlesByLabels(List<String> labelList, Long userId,int page) {
        int offset = (page - 1) * 4;
        return articleMapper.getMyArticlesByLabels(labelList,userId,offset);
    }

    @Override
    public List<ArticleDetailsDto> getArticlesBySearch(String keyword,int page) {
        int offset = (page - 1) * 4;
        return articleMapper.getArticlesBySearch(keyword,offset);
    }

    @Override
    public List<ArticleDetailsDto> getMyArticlesBySearch(String keyword, Long userId,int page) {
        int offset = (page - 1) * 4;
        return articleMapper.getMyArticlesBySearch(keyword,userId,offset);
    }

    @Override
    public List<String> getAllTitle() {
        return articleMapper.getAllTitle();
    }

    @Override
    public Integer getSize() {
        return articleMapper.getSize();
    }

    @Override
    public void updateLike(Long articleid, int like) {
        articleMapper.updateLike(articleid,like);
    }

}
