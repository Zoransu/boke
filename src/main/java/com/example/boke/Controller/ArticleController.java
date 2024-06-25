package com.example.boke.Controller;

import com.example.boke.Dto.ArticleDetailsDto;
import com.example.boke.Dto.ArticleDto;
import com.example.boke.Service.ArticleService;
import com.example.boke.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/Article")
@Api(tags = "文章管理")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //发布文章
    @PostMapping("/create")
    @ApiOperation(value = "发布文章", notes = "发布一篇新文章")
    public Result createArticle(@RequestBody @ApiParam(value = "文章信息", required = true)ArticleDto articleDto, HttpServletRequest request){
        try {
            // 从请求属性中获取用户ID
            Long userId = (Long) request.getAttribute("userId");
            if (userId == null) {
                return Result.error("用户未登录或会话已过期");
            }
            articleDto.setUserId(userId);
            articleService.createArticle(articleDto);
            return Result.success();
        }catch (Exception e){
            log.error("发布文章失败: {}", e.getMessage());
            return Result.error("发布失败：" + e.getMessage());
        }
    }

    //获取单个文章详细页（文章，发布者，评论等信息）
    @GetMapping("/{articleId}")
    @ApiOperation(value = "获取文章详细信息", notes = "根据文章ID获取文章详细信息")
    public Result getArticleDetails(@PathVariable @ApiParam(value = "文章ID", required = true)Long articleId) {
        log.info("查看文章:  {}",articleId);
        ArticleDetailsDto articleDetails = articleService.getArticleDetails(articleId);
        return Result.success(articleDetails);
    }

    @GetMapping("/out/getHotTen")
    @ApiOperation(value = "获取最热的十篇文章", notes = "获取最热的十篇文章")
    public Result getHotTen(){
        ArrayList<ArticleDetailsDto> list=articleService.getHotTen();
        log.info("查看最热前十文章: {}",list);
        return Result.success(list);
    }

    //分页查询，每页4条（主页）
    @ApiOperation(value = "分页查询文章", notes = "分页查询文章，每页4条，作用于主页面进行分页查询表")
    @GetMapping("/out/getArticles")
    public Result getArticles(@RequestParam(defaultValue = "1") @ApiParam(value = "页码", defaultValue = "1")int page){
        ArrayList<ArticleDetailsDto> list=articleService.getArticles(page);
        log.info("查看第{}页的文章：{}",page,list);
        return Result.success(list);
    }

    @ApiOperation(value = "查询所有文章个数" ,notes = "查询文章个数，用于分页查询动态设置页数")
    @GetMapping("/out/getAllSize")
    public Result getSize(){
        Integer size=articleService.getSize();
        log.info("文章个数：{}",size);
        return Result.success(size);
    }

    //带某些标签的所有文章(标签用 , 进行分割)
    @ApiOperation(value = "获取带标签的分页文章", notes = "根据标签获取分页文章，标签用逗号分割")
    @PostMapping("/out/getLabels")
    public Result getLabels(@RequestParam("labels") @ApiParam(value = "标签列表，用逗号分割", required = true)String labels,
                            @RequestParam(defaultValue = "1") @ApiParam(value = "页码", defaultValue = "1")int page){
        List<String> labelList = Arrays.asList(labels.split(","));
        List<ArticleDetailsDto> articles= articleService.getArticlesByLabels(labelList,page);
        log.info("查看带这些标签{}的所有文章中的第{}页",labelList,page);
        return Result.success(articles);
    }

    @ApiOperation(value = "获取带标签的分页文章的个数", notes = "根据标签获取分页文章个数，用于分页查询")
    @GetMapping("/out/getLabelsSize")
    public Result getLabelsSize(@RequestParam("labels") @ApiParam(value = "标签列表，用逗号分割", required = true)String labels){
        List<String> labelList = Arrays.asList(labels.split(","));
        int size=articleService.getLabelsSize(labelList);
        log.info("查看带这些标签{}的文章个数{}",labelList,size);
        return Result.success(size);
    }

    @ApiOperation(value ="删除文章" ,notes = "删除文章以及其标签、评论")
    @DeleteMapping("/delete/{articleId}")
    public Result deleteArticle(@PathVariable("articleId") Long articleId){
            articleService.deleteArticle(articleId);
            log.info("删除成功:{}",articleId);
            return Result.success("删除成功");
    }

    @ApiOperation(value ="通过用户id获取文章" ,notes = "根据userId获取该用户指定页数的文章")
    @PostMapping("/getArticleByUserId")
    public Result getArticleByUserId(@RequestParam("userId") @ApiParam("指定用户id")Long userId,
                                     @RequestParam(defaultValue = "1") @ApiParam(value = "页码", defaultValue = "1")int page){
        List<ArticleDetailsDto> list=articleService.getArticleByUserId(userId,page);
        log.info("获取用户id为{}，的第{}文章信息{}",userId,page,list);
        return Result.success(list);
    }

    @ApiOperation(value ="通过用户id获取文章个数" ,notes = "根据userId获取该用户指定页数的文章个数，用于分页查询")
    @GetMapping("/getArticleByUserIdSize")
    public Result getArticleByUserIdSize(@RequestParam("userId") @ApiParam("指定用户id")Long userId){
        int size=articleService.getArticleByUserIdSize(userId);
        log.info("获取用户id为{}，的所有文章个数{}",userId,size);
        return Result.success(size);
    }

    @ApiOperation(value ="获取当前登录用户的分页文章" ,notes = "获取当前登录用户的分页文章的详细信息")
    @GetMapping("/getMyArticles")
    public Result getMyArticles(@RequestParam(defaultValue = "1") @ApiParam(value = "页码", defaultValue = "1")int page,
                                HttpServletRequest request){
        try {
            // 从请求属性中获取用户ID
            Long userId = (Long) request.getAttribute("userId");
            if (userId == null) {
                return Result.error("用户未登录或会话已过期");
            }
            List<ArticleDetailsDto> list=articleService.getArticleByUserId(userId,page);
            log.info("获取用户id为{}，的第{}文章信息{}",userId,page,list);
            return Result.success(list);
        }catch (Exception e) {
            log.error("获取文章失败: {}", e.getMessage());
            return Result.error("获取失败：" + e.getMessage());
        }
    }

    @ApiOperation(value ="获取当前登录用户的文章个数" ,notes = "获取当前登录用户的文章的个数，用于分页查询")
    @GetMapping("/getMyArticlesSize")
    public Result getMyArticlesSize(HttpServletRequest request){
        try {
            Long userId = (Long) request.getAttribute("userId");
            if (userId == null) {
                return Result.error("用户未登录或会话已过期");
            }
            int size=articleService.getArticleByUserIdSize(userId);
            log.info("获取用户id为{}，的文章数量{}",userId,size);
            return Result.success(size);
        }catch (Exception e) {
            log.error("获取失败: {}", e.getMessage());
            return Result.error("获取失败：" + e.getMessage());
        }
    }

    //带某些标签的所有文章(标签用 , 进行分割)
    @ApiOperation(value = "获取当前用户带标签的分页文章", notes = "根据标签获取当前用户分页文章，标签用逗号分割")
    @PostMapping("/getMyLabels")
    public Result getMyLabels(
            @RequestParam("labels") @ApiParam(value = "标签列表，用逗号分割", required = true)String labels,
            @RequestParam(defaultValue = "1") @ApiParam(value = "页码", defaultValue = "1")int page,
            HttpServletRequest request){
        try {
            Long userId = (Long) request.getAttribute("userId");
            if (userId == null) {
                return Result.error("用户未登录或会话已过期");
            }
            List<String> labelList = Arrays.asList(labels.split(","));
            List<ArticleDetailsDto> articles= articleService.getMyArticlesByLabels(labelList,userId,page);
            log.info("查看带这些标签{}的第{}页的文章：{}",labelList,page,articles);
            return Result.success(articles);
        }catch (Exception e) {
            log.error("获取文章失败: {}", e.getMessage());
            return Result.error("获取失败：" + e.getMessage());
        }
    }

    @ApiOperation(value = "获取当前用户带标签的文章个数", notes = "获取当前用户带标签的文章个数，用于分页查询")
    @GetMapping("/getMyLabelsSize")
    public Result getMyLabelsSize(@RequestParam("labels") @ApiParam(value = "标签列表，用逗号分割", required = true)String labels,
                                  HttpServletRequest request){
        try {
            Long userId = (Long) request.getAttribute("userId");
            if (userId == null) {
                return Result.error("用户未登录或会话已过期");
            }
            List<String> labelList = Arrays.asList(labels.split(","));
            int size=articleService.getMyLabelsSize(labelList,userId);
            log.info("当前用户带标签{}的文章个数{}",labelList,size);
            return Result.success(size);
        }catch (Exception e) {
            log.error("获取失败: {}", e.getMessage());
            return Result.error("获取失败：" + e.getMessage());
        }
    }

    @ApiOperation(value = "主页模糊搜索" ,notes = "主页搜索带关键词的文章")
    @PostMapping("/out/getArticlesBySearch")
    public Result getArticlesBySearch(
            @RequestParam("keyword") @ApiParam(value = "模糊搜索关键词", required = true)String keyword,
            @RequestParam(defaultValue = "1") @ApiParam(value = "页码", defaultValue = "1")int page
            ){
        List<ArticleDetailsDto> articles=articleService.getArticlesBySearch(keyword,page);
        log.info("搜索关键词{}",keyword);
        return Result.success(articles);
    }

    @ApiOperation(value = "主页模糊搜索文章个数" ,notes = "主页搜索带关键词的文章个数，用于分页查询")
    @PostMapping("/out/getArticlesBySearchSize")
    public Result getArticlesBySearchSize(
            @RequestParam("keyword") @ApiParam(value = "模糊搜索关键词", required = true)String keyword){
        int size=articleService.getArticlesBySearchSize(keyword);
        log.info("搜索关键词 {} 的文章个数{}",keyword,size);
        return Result.success(size);
    }

    @ApiOperation(value = "当前用户，自己模糊搜索" ,notes = "当前用户搜索带关键词的文章")
    @PostMapping("/getMyArticlesBySearch")
    public Result getMyArticlesBySearch(
            @RequestParam("keyword") @ApiParam(value = "模糊搜索关键词", required = true)String keyword,
            @RequestParam(defaultValue = "1") @ApiParam(value = "页码", defaultValue = "1")int page,
            HttpServletRequest request){
        try {
            Long userId = (Long) request.getAttribute("userId");
            if (userId == null) {
                return Result.error("用户未登录或会话已过期");
            }
            List<ArticleDetailsDto> articles=articleService.getMyArticlesBySearch(keyword,userId,page);
            log.info("搜索关键词：{}",keyword);
            return Result.success(articles);
        }catch (Exception e) {
            log.error("获取文章失败: {}", e.getMessage());
            return Result.error("获取失败：" + e.getMessage());
        }
    }

    @ApiOperation(value = "当前用户，自己模糊搜索文章的个数" ,notes = "当前用户搜索带关键词的文章的个数，用于分页")
    @PostMapping("/getMyArticlesBySearchSize")
    public Result getMyArticlesBySearchSize(
            @RequestParam("keyword") @ApiParam(value = "模糊搜索关键词", required = true)String keyword,
            HttpServletRequest request){
        try {
            Long userId = (Long) request.getAttribute("userId");
            if (userId == null) {
                return Result.error("用户未登录或会话已过期");
            }
            int size= articleService.getMyArticlesBySearchSize(keyword,userId);
            log.info("搜索关键词：{},个数为：{}",keyword,size);
            return Result.success(size);
        }catch (Exception e) {
            log.error("获取失败: {}", e.getMessage());
            return Result.error("获取失败：" + e.getMessage());
        }
    }

    @ApiOperation(value = "获取所有文章标题" ,notes = "获取所有文章标题，用于模糊查询")
    @GetMapping("/out/getAllTitle")
    public Result getAllTitle(){
        List<String> articlesTitle=articleService.getAllTitle();
        log.info("获取所有文章标题{}",articlesTitle);
        return Result.success(articlesTitle);
    }

    @ApiOperation(value = "修改点赞" ,notes = "点赞数量修改")
    @GetMapping("/updateLike")
    public Result updateLike(@RequestParam Long articleid ,@RequestParam int like){
        articleService.updateLike(articleid,like);
        log.info("文章{}  点赞更新：{}",articleid,like);
        return Result.success();
    }
}
