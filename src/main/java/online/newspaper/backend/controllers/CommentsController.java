package online.newspaper.backend.controllers;

import online.newspaper.backend.models.Comment;
import online.newspaper.backend.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    private final CommentService commentService;

    @Autowired
    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{articleId}")
    public List<Comment> getArticleComments(@PathVariable("articleId") int articleId) {
        return commentService.findALlCommentsForArticle(articleId);
    }

    @GetMapping("/{articleId}/count")
    public int getCommentsCountForArticle(@PathVariable("v") int articleId) {
        return commentService.getCommentsCountForArticle(articleId);
    }

    // TODO if user is authenticated
    @PostMapping("/{articleId}")
    public Comment createCommentForArticle(@PathVariable("articleId") int articleId, @RequestBody Comment comment) {
        return commentService.saveCommentForArticle(articleId, comment);
    }
}
