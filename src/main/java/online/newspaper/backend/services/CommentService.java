package online.newspaper.backend.services;

import online.newspaper.backend.models.Article;
import online.newspaper.backend.models.Comment;
import online.newspaper.backend.repositories.ArticlesRepository;
import online.newspaper.backend.repositories.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentService {

    private final CommentsRepository commentsRepository;
    private final ArticlesRepository articlesRepository;

    @Autowired
    public CommentService(CommentsRepository commentsRepository, ArticlesRepository articlesRepository) {
        this.commentsRepository = commentsRepository;
        this.articlesRepository = articlesRepository;
    }

//    public List<Comment> findALlCommentsForArticle(int articleId) {
//        return commentsRepository.findByArticleIdOrderByCreatedAtDesc(articleId);
//    }

    public Page<Comment> findALlCommentsForArticle(int articleId, Pageable pageable) {
        return commentsRepository.findByArticleIdOrderByCreatedAtDesc(articleId, pageable);
    }

    public int getCommentsCountForArticle(int articleId) {
        return commentsRepository.countByArticleId(articleId);
    }

    public Comment saveCommentForArticle(int articleId, Comment comment) {
        Article foundArticleReference = articlesRepository.getReferenceById(articleId);
        comment.setArticle(foundArticleReference);

        return commentsRepository.save(comment);
    }
}
