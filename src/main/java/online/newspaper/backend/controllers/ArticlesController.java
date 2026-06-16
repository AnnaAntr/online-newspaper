package online.newspaper.backend.controllers;

import online.newspaper.backend.dto.ArticlePageResponse;
import online.newspaper.backend.dto.ArticleResponse;
import online.newspaper.backend.models.Article;
import online.newspaper.backend.models.Person;
import online.newspaper.backend.security.PersonDetails;
import online.newspaper.backend.services.ArticleService;
import online.newspaper.backend.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/news")
public class ArticlesController {

    private final ArticleService articleService;
    private final LikeService likeService;

    @Autowired
    public ArticlesController(ArticleService articleService, LikeService likeService) {
        this.articleService = articleService;
        this.likeService = likeService;
    }

    // одна статья по id
    @GetMapping("/{articleId}")
    public ResponseEntity<?> getOneArticle(@PathVariable("articleId") int articleId, Authentication authentication) {
        Article foundArticle = articleService.findArticle(articleId);

        if (foundArticle == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Статья не найдена");

        ArticleResponse response = new ArticleResponse(foundArticle);

        if (authentication != null && authentication.isAuthenticated()) {
            PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();
            Person currentUser = userDetails.getPerson();

            response.setLikedByCurrentUser(likeService.isLikedByUser(articleId, currentUser.getId()));
        }

        return ResponseEntity.ok(response);
    }

    // статьи за последние 24 часа
    @GetMapping("/main")
    public ResponseEntity<ArticlePageResponse> getFreshArticles(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "5") int size,
                                                                Authentication authentication) {

        Page<Article> articlePage = articleService.findFreshArticlesWithPagination(page, size);

        Person currentUser = null;
        if (authentication != null && authentication.isAuthenticated()) {
            PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();
            currentUser = userDetails.getPerson();
        }
        int userId = currentUser != null ? currentUser.getId() : -1;

        List<ArticleResponse> articleResponse = articlePage.getContent().stream().map(article -> convertToArticleResponse(article, userId)).collect(Collectors.toList());

        ArticlePageResponse response = new ArticlePageResponse();
        response.setArticles(articleResponse);
        response.setCurrentPage(articlePage.getNumber());
        response.setTotalPages(articlePage.getTotalPages());
        response.setTotalArticles(articlePage.getTotalElements());
        response.setHasNext(articlePage.hasNext());

        return ResponseEntity.ok(response);
    }

    // архив статей
    @GetMapping("/archive")
    public ResponseEntity<ArticlePageResponse> getArchiveArticles(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "5") int size,
                                                                  Authentication authentication) {

        Page<Article> articlePage = articleService.findArchiveArticlesWithPagination(page, size);

        Person currentUser = null;
        if (authentication != null && authentication.isAuthenticated()) {
            PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();
            currentUser = userDetails.getPerson();
        }
        int userId = currentUser != null ? currentUser.getId() : -1;

        List<ArticleResponse> articleResponse = articlePage.getContent().stream().map(article -> convertToArticleResponse(article, userId)).collect(Collectors.toList());

        ArticlePageResponse response = new ArticlePageResponse();
        response.setArticles(articleResponse);
        response.setCurrentPage(articlePage.getNumber());
        response.setTotalPages(articlePage.getTotalPages());
        response.setTotalArticles(articlePage.getTotalElements());
        response.setHasNext(articlePage.hasNext());

        return ResponseEntity.ok(response);
    }

    // функция для конвертации объекта статьи в DTO
    private ArticleResponse convertToArticleResponse(Article article, int userId) {
        ArticleResponse articleResponse = new ArticleResponse(article);

        if (userId != -1)
            articleResponse.setLikedByCurrentUser(likeService.isLikedByUser(article.getId(), userId));

        return articleResponse;
    }
}
