package online.newspaper.backend.controllers;

import online.newspaper.backend.models.Article;
import online.newspaper.backend.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class ArticlesController {

    private final ArticleService articleService;

    @Autowired
    public ArticlesController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // TODO response entity
    @GetMapping("/{id}")
    public Article getOneArticle(@PathVariable("id") int id) {
        return articleService.findArticle(id);
    }

    @GetMapping("/main")
    public Page<Article> getFreshArticles(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int size) {
        return articleService.findFreshArticlesWithPagination(page, size);
    }

    @GetMapping("/archive")
    public Page<Article> getArchiveArticles(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "5") int size) {
        return articleService.findArchiveArticlesWithPagination(page, size);
    }

}
