package online.newspaper.backend.controllers;

import online.newspaper.backend.dto.CommentPageResponse;
import online.newspaper.backend.dto.CommentRequest;
import online.newspaper.backend.dto.CommentResponse;
import online.newspaper.backend.models.Comment;
import online.newspaper.backend.models.Person;
import online.newspaper.backend.security.PersonDetails;
import online.newspaper.backend.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    private final CommentService commentService;

    @Autowired
    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

//    @GetMapping("/{articleId}")
//    public ResponseEntity<List<CommentResponse>> getArticleComments(@PathVariable("articleId") int articleId) {
//        List<Comment> comments = commentService.findALlCommentsForArticle(articleId);
//        List<CommentResponse> response = comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());
//
//        return ResponseEntity.ok(response);
//
//    }

    @GetMapping("/{articleId}")
    public ResponseEntity<CommentPageResponse> getArticleComments(@PathVariable("articleId") int articleId,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentsPage = commentService.findALlCommentsForArticle(articleId, pageable);

        List<CommentResponse> comments = commentsPage.getContent().stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());

        CommentPageResponse response = new CommentPageResponse();
        response.setComments(comments);
        response.setCurrentPage(commentsPage.getNumber());
        response.setTotalPages(commentsPage.getTotalPages());
        response.setTotalComments(commentsPage.getTotalElements());
        response.setHasNext(commentsPage.hasNext());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{articleId}/count")
    public ResponseEntity<Map<String, Integer>> getCommentsCountForArticle(@PathVariable("articleId") int articleId) {
        int commentsCount = commentService.getCommentsCountForArticle(articleId);

        return ResponseEntity.ok(Map.of("count", commentsCount));
    }

    @PostMapping("/{articleId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createCommentForArticle(@PathVariable("articleId") int articleId,
                                                     @Valid @RequestBody CommentRequest commentRequest,
                                                     Authentication authentication) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person currentUser = personDetails.getPerson();

        Comment comment = new Comment();
        comment.setContent(commentRequest.getText());
        comment.setCreatedAt(new Date());
        comment.setAuthor(currentUser);

        Comment savedComment = commentService.saveCommentForArticle(articleId, comment);

        return ResponseEntity.ok(new CommentResponse(savedComment));
    }
}
