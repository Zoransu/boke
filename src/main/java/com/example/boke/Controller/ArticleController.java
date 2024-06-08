package com.example.boke.Controller;

import com.example.boke.Dto.ArticleDetailsDto;
import com.example.boke.Dto.ArticleDto;
import com.example.boke.Service.ArticleService;
import com.example.boke.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/Article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //发布文章
    @PostMapping("/create")
    public Result createArticle(@RequestBody ArticleDto articleDto, HttpServletRequest request){
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
    public Result getArticleDetails(@PathVariable Long articleId) {
        log.info("查看文章:  {}",articleId);
        ArticleDetailsDto articleDetails = articleService.getArticleDetails(articleId);
        return Result.success(articleDetails);
    }

    @GetMapping("/getLastTen")
    public Result getLastTen(){
        ArrayList<ArticleDetailsDto> list=articleService.getLastTen();
        log.info("查看最近前十发布的文章: {}",list);
        return Result.success(list);
    }


    @GetMapping("/getHotTen")
    public Result getHotTen(){
        ArrayList<ArticleDetailsDto> list=articleService.getHotTen();
        log.info("查看最热前十文章: {}",list);
        return Result.success(list);
    }

    //分页查询，每页5条（主页）
    @GetMapping("/getArticles")
    public Result getArticles(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "5") int size){
        ArrayList<ArticleDetailsDto> list=articleService.getArticles(page,size);
        log.info("查看第{}页的文章：{}",page,list);
        return Result.success(list);
    }

    //带某些标签的所有文章(标签用 , 进行分割)
    @GetMapping("/getLabels")
    public Result getLabels(@RequestParam("labels") String labels){
        List<String> labelList = Arrays.asList(labels.split(","));
        List<ArticleDetailsDto> articles= articleService.getArticlesByLabels(labelList);
        log.info("查看带这些标签{}的所有文章：{}",labelList,articles);
        return Result.success(articles);
    }


//    //修改国际标准ISO 8601
//    public static Date ChangeData(Date data){
//        LocalDateTime localDateTime = data.toInstant()
//                .atZone(ZoneId.systemDefault())
//                .toLocalDateTime();  // Date转换为LocalDateTime
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String formattedDate = localDateTime.format(formatter);  // 格式化LocalDateTime
//        OffsetDateTime odt = OffsetDateTime.parse(formattedDate);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");
//        LocalDateTime time = LocalDateTime.parse(formattedDate, formatter);
//        // 将LocalDateTime转换回Date
//        Date from = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
//        return from;
//    }
}
