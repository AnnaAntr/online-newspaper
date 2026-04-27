package online.newspaper.backend.controllers;

import online.newspaper.backend.models.Comment;
import online.newspaper.backend.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class CommentsController {

    private final CommentService commentService;

    @Autowired
    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getArticleComments(@PathVariable("id") int id) {
        return commentService.findALlCommentsForArticle(id);
    }

    @GetMapping("/{id}/comments/count")
    public int getCommentsCountForArticle(@PathVariable("id") int id) {
        return commentService.getCommentsCountForArticle(id);
    }

    // TODO if user is authenticated
    @PostMapping("/{id}/comments")
    public Comment createCommentForArticle(@PathVariable("id") int id, @RequestBody Comment comment) {
        return commentService.saveCommentForArticle(id, comment);
    }
}
