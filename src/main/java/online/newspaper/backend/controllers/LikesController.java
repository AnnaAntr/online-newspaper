package online.newspaper.backend.controllers;

import online.newspaper.backend.dto.LikeResponse;
import online.newspaper.backend.dto.LikeStatusResponse;
import online.newspaper.backend.models.Person;
import online.newspaper.backend.security.PersonDetails;
import online.newspaper.backend.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/likes")
public class LikesController {

    private final LikeService likeService;

    @Autowired
    public LikesController(LikeService likeService) {
        this.likeService = likeService;
    }

    // количество лайков к статье
    @GetMapping("/{articleId}/count")
    public ResponseEntity<Map<String, Integer>> getLikesCountForArticle(@PathVariable("articleId") int articleId) {
        int count = likeService.getLikesCountForArticle(articleId);

        return ResponseEntity.ok(Map.of("count", count));
    }

    // проверка, ставил ли текущий пользователь лайк
    @GetMapping("/{articleId}/check")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LikeStatusResponse> checkUserLikeForArticle(@PathVariable("articleId") int articleId, Authentication authentication) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person currentUser = personDetails.getPerson();

        boolean isLiked = likeService.isLikedByUser(articleId, currentUser.getId());
        int likesCount = likeService.getLikesCountForArticle(articleId);

        return ResponseEntity.ok(new LikeStatusResponse(isLiked, likesCount));
    }

    // инвертирование лайка пользователя
    @PostMapping("/{articleId}/switchLike")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LikeResponse> switchLikeForArticle(@PathVariable("articleId") int articleId, Authentication authentication) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person currentUser = personDetails.getPerson();

        LikeResponse response = likeService.switchUserLikeForArticle(articleId, currentUser.getId());

        return ResponseEntity.ok(response);
    }
}
