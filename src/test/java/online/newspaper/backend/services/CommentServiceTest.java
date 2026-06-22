package online.newspaper.backend.services;

import online.newspaper.backend.repositories.ArticlesRepository;
import online.newspaper.backend.repositories.CommentsRepository;
import online.newspaper.backend.models.Article;
import online.newspaper.backend.models.Person;
import online.newspaper.backend.models.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    
    @Mock
    private CommentsRepository commentsRepository;

    @Mock
    private ArticlesRepository articlesRepository;

    @InjectMocks
    private CommentService commentService;

    private Article testArticle;
    private Person testAuthor;
    private Comment comment1;
    private List<Comment> testComments;

    @BeforeEach
    void setUp() {
        testArticle = new Article();
        testArticle.setId(1);
        testArticle.setTitle("Test article");
        testArticle.setContent("Test content");
        testArticle.setCreatedAt(new Date());

        testAuthor = new Person();
        testAuthor.setId(1);
        testAuthor.setName("Ivan");
        testAuthor.setSurname("Ivanov");
        testAuthor.setEmail("ivanov@test.com");

        comment1 = new Comment();
        comment1.setId(1);
        comment1.setContent("Test comment text 1");
        comment1.setArticle(testArticle);
        comment1.setAuthor(testAuthor);
        comment1.setCreatedAt(new Date());

        Comment comment2 = new Comment();
        comment2.setId(2);
        comment2.setContent("Test comment text 1");
        comment2.setArticle(testArticle);
        comment2.setAuthor(testAuthor);
        comment2.setCreatedAt(new Date());

        testComments = List.of(comment1, comment2);
    }

    // findALlCommentsForArticle ----------------------------------------------------------------------------------------------
    @Test
    void findAllCommentsForArticle_ShouldReturnPageOfComments() {
        int articleId = 1;
        int page = 0;
        int size = 3;
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> expectedPage = new PageImpl<>(testComments, pageable, testComments.size());

        when(commentsRepository.findByArticleIdOrderByCreatedAtDesc(articleId, pageable)).thenReturn(expectedPage);

        Page<Comment> result = commentService.findALlCommentsForArticle(articleId, pageable);

        assertNotNull(result);
        assertEquals(expectedPage.getTotalElements(), result.getTotalElements());
        assertEquals(expectedPage.getContent().size(), result.getContent().size());
        assertEquals(expectedPage.getContent().get(0).getContent(), result.getContent().get(0).getContent());
        assertEquals(expectedPage.getContent().get(0).getAuthor().getName(), result.getContent().get(0).getAuthor().getName());

        verify(commentsRepository, times(1)).findByArticleIdOrderByCreatedAtDesc(articleId, pageable);
    }

    @Test
    void findAllCommentsForArticle_WhenNoComments_ShouldReturnEmptyPage() {
        int articleId = 1;
        int page = 0;
        int size = 3;
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> expectedPage = new PageImpl<>(List.of(), pageable, 0);

        when(commentsRepository.findByArticleIdOrderByCreatedAtDesc(articleId, pageable)).thenReturn(expectedPage);

        Page<Comment> result = commentService.findALlCommentsForArticle(articleId, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.getTotalElements());

        verify(commentsRepository, times(1)).findByArticleIdOrderByCreatedAtDesc(articleId, pageable);
    }

    // getCommentsCountForArticle -------------------------------------------------------------------------
    @Test
    void getCommentsCountForArticle_ShouldReturnCorrectCount() {
        int articleId = 1;
        int expectedCount = 5;

        when(commentsRepository.countByArticleId(articleId)).thenReturn(expectedCount);

        int result = commentService.getCommentsCountForArticle(articleId);

        assertEquals(expectedCount, result);

        verify(commentsRepository, times(1)).countByArticleId(articleId);
    }

    @Test
    void getCommentsCountForArticle_WhenNoComments_ShouldReturnZero() {
        int articleId = 1;
        int expectedCount = 0;

        when(commentsRepository.countByArticleId(articleId)).thenReturn(expectedCount);

        int result = commentService.getCommentsCountForArticle(articleId);

        assertEquals(expectedCount, result);

        verify(commentsRepository, times(1)).countByArticleId(articleId);
    }

    // saveCommentForArticle ------------------------------------------------------------------------
    @Test
    void saveCommentForArticle_ShouldSaveAndReturnComment() {
        Comment commentToSave = new Comment();
        commentToSave.setContent("New comment");
        commentToSave.setAuthor(testAuthor);
        commentToSave.setCreatedAt(new Date());

        Comment savedComment = new Comment();
        savedComment.setId(testComments.size() + 1);
        savedComment.setContent(commentToSave.getContent());
        savedComment.setAuthor(testAuthor);
        savedComment.setArticle(testArticle);
        savedComment.setCreatedAt(new Date());

        when(articlesRepository.getReferenceById(testArticle.getId())).thenReturn(testArticle);
        when(commentsRepository.save(any(Comment.class))).thenReturn(savedComment);

        Comment result = commentService.saveCommentForArticle(testArticle.getId(), commentToSave);

        assertNotNull(result);
        assertEquals(savedComment.getId(), result.getId());
        assertEquals(savedComment.getContent(), result.getContent());
        assertEquals(savedComment.getAuthor().getName(), result.getAuthor().getName());
        assertEquals(testArticle.getId(), result.getArticle().getId());

        verify(commentsRepository, times(1)).save(commentToSave);
    }

    @Test
    void saveCommentForArticle_WhenArticleExists_ShouldSetArticleCorrectly() {
        Comment commentToSave = new Comment();
        commentToSave.setContent("Test comment");
        commentToSave.setAuthor(testAuthor);
        commentToSave.setCreatedAt(new Date());

        Comment savedComment = new Comment();
        savedComment.setId(testComments.size() + 1);
        savedComment.setContent(commentToSave.getContent());
        savedComment.setAuthor(testAuthor);
        savedComment.setArticle(testArticle);
        savedComment.setCreatedAt(new Date());

        when(articlesRepository.getReferenceById(testArticle.getId())).thenReturn(testArticle);
        when(commentsRepository.save(any(Comment.class))).thenReturn(savedComment);

        Comment result = commentService.saveCommentForArticle(testArticle.getId(), commentToSave);

        assertNotNull(result);
        assertEquals(testArticle.getId(), result.getArticle().getId());
        assertEquals(testArticle.getTitle(), result.getArticle().getTitle());

        verify(commentsRepository, times(1)).save(commentToSave);
    }
}
