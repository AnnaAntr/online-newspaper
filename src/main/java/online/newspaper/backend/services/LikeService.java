package online.newspaper.backend.services;

import online.newspaper.backend.dto.LikeResponse;
import online.newspaper.backend.models.Article;
import online.newspaper.backend.models.Like;
import online.newspaper.backend.models.Person;
import online.newspaper.backend.repositories.ArticlesRepository;
import online.newspaper.backend.repositories.LikesRepository;
import online.newspaper.backend.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class LikeService {

    private final LikesRepository likesRepository;
    private final ArticlesRepository articlesRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public LikeService(LikesRepository likesRepository, ArticlesRepository articlesRepository, PeopleRepository peopleRepository) {
        this.likesRepository = likesRepository;
        this.articlesRepository = articlesRepository;
        this.peopleRepository = peopleRepository;
    }

    public int getLikesCountForArticle(int articleId) {
        return likesRepository.countByArticleId(articleId);
    }

    public boolean isLikedByUser(int articleId, int userId) {
        return likesRepository.findByArticleIdAndAuthorId(articleId, userId).isPresent();
    }

    public LikeResponse switchUserLikeForArticle(int articleId, int userId) {
        Article article = articlesRepository.findById(articleId).orElse(null);
        Person user = peopleRepository.findById(userId).orElse(null);

        Optional<Like> like = likesRepository.findByArticleIdAndAuthorId(articleId, userId);

        LikeResponse response = new LikeResponse();
        response.setArticleId(articleId);

        if (like.isPresent()) {
            likesRepository.delete(like.get());
            response.setLiked(false);
        }
        else {
            Like createdLike = new Like();
            createdLike.setArticle(article);
            createdLike.setAuthor(user);

            likesRepository.save(createdLike);
            response.setLiked(true);
        }

        response.setLikesCount(likesRepository.countByArticleId(articleId));
        return response;
    }
}
