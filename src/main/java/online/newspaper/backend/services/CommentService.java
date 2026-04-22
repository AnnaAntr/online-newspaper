package online.newspaper.backend.services;

import online.newspaper.backend.models.Comment;
import online.newspaper.backend.repositories.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentService {

    private final CommentsRepository commentsRepository;

    @Autowired
    public CommentService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    public List<Comment> findALlCommentsForArticle(int article_id) {
        return commentsRepository.findByArticleIdOrderByCreatedAtDesc(article_id);
    }
}
