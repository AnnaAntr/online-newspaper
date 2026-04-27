package online.newspaper.backend.services;

import online.newspaper.backend.repositories.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LikeService {

    private final LikesRepository likesRepository;

    @Autowired
    public LikeService(LikesRepository likesRepository) {
        this.likesRepository = likesRepository;
    }

    public int getLikesCountForArticle(int articleId) {
        return likesRepository.countByArticleId(articleId);
    }
}
