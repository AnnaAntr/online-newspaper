package online.newspaper.backend.controllers;

import online.newspaper.backend.models.Like;
import online.newspaper.backend.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
public class LikesController {

    private final LikeService likeService;

    @Autowired
    public LikesController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/{id}/likes/count")
    public int getLikesCountForArticle(@PathVariable("id") int id) {
        return likeService.getLikesCountForArticle(id);
    }

    // TODO
//    @GetMapping("/{id}/likes/check")
//    public boolean checkUserLikeForArticle(@PathVariable("id") int id) {
//
//    }

//    @PostMapping("/{id}/likes")
//    public Like toggleLikeForArticle(@PathVariable("id") int id) {
//        // check if user auth
//    }
}
