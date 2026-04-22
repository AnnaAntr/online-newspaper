package online.newspaper.backend.controllers;

import online.newspaper.backend.models.Article;
import online.newspaper.backend.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
public class ArticlesController {

    private final ArticleService articleService;

    @Autowired
    public ArticlesController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{id}")
    public Article getOneArticle(@PathVariable("id") int id) {
        return articleService.findArticle(id);
    }

    @GetMapping("/main")
    public List<Article> getFreshArticles() {
        return articleService.findFreshArticles();
    }

    @GetMapping("/archive")
    public List<Article> getArchiveArticles() {
        return articleService.findArchiveArticles();
    }

}
