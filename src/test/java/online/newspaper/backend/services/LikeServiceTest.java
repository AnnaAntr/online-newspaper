package online.newspaper.backend.services;

import online.newspaper.backend.dto.LikeResponse;
import online.newspaper.backend.models.Article;
import online.newspaper.backend.models.Like;
import online.newspaper.backend.models.Person;
import online.newspaper.backend.repositories.ArticlesRepository;
import online.newspaper.backend.repositories.LikesRepository;
import online.newspaper.backend.repositories.PeopleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {

    @Mock
    private LikesRepository likesRepository;

    @Mock
    private ArticlesRepository articlesRepository;

    @Mock
    private PeopleRepository peopleRepository;

    @InjectMocks
    private LikeService likeService;

    private Article testArticle;
    private Person testUser;
    private Like testLike;

    @BeforeEach
    void setUp() {
        testArticle = new Article();
        testArticle.setId(1);
        testArticle.setTitle("Test article");
        testArticle.setContent("Test content");

        testUser = new Person();
        testUser.setId(1);
        testUser.setName("Ivan");
        testUser.setSurname("Ivanov");
        testUser.setEmail("ivanov@test.com");

        testLike = new Like();
        testLike.setId(1);
        testLike.setArticle(testArticle);
        testLike.setAuthor(testUser);
    }

    @Test
    void getLikesCountForArticle_ShouldReturnCorrectCount() {
        int articleId = 1;
        int expectedCount = 5;

        when(likesRepository.countByArticleId(articleId)).thenReturn(expectedCount);

        int result = likeService.getLikesCountForArticle(articleId);

        assertEquals(expectedCount, result);

        verify(likesRepository, times(1)).countByArticleId(articleId);
    }

    @Test
    void getLikesCountForArticle_WhenNoLikes_ShouldReturnZero() {
        int articleId = 1;
        int expectedCount = 0;

        when(likesRepository.countByArticleId(articleId)).thenReturn(expectedCount);

        int result = likeService.getLikesCountForArticle(articleId);

        assertEquals(expectedCount, result);

        verify(likesRepository, times(1)).countByArticleId(articleId);
    }

    @Test
    void isLikedByUser_WhenLiked_ShouldReturnTrue() {
        int articleId = 1;
        int userId = 1;

        when(likesRepository.findByArticleIdAndAuthorId(articleId, userId)).thenReturn(Optional.of(testLike));

        boolean result = likeService.isLikedByUser(articleId, userId);

        assertTrue(result);

        verify(likesRepository, times(1)).findByArticleIdAndAuthorId(articleId, userId);
    }

    @Test
    void isLikedByUser_WhenNotLiked_ShouldReturnFalse() {
        int articleId = 1;
        int userId = 1;

        when(likesRepository.findByArticleIdAndAuthorId(articleId, userId)).thenReturn(Optional.empty());

        boolean result = likeService.isLikedByUser(articleId, userId);

        assertFalse(result);

        verify(likesRepository, times(1)).findByArticleIdAndAuthorId(articleId, userId);
    }

    @Test
    void switchUserLikeForArticle_ShouldSwitchLikeCorrectly() {
        int articleId = 1;
        int userId = 1;

        when(articlesRepository.findById(articleId)).thenReturn(Optional.of(testArticle));
        when(peopleRepository.findById(userId)).thenReturn(Optional.of(testUser));

        when(likesRepository.findByArticleIdAndAuthorId(articleId, userId)).thenReturn(Optional.empty());
        when(likesRepository.countByArticleId(articleId)).thenReturn(1);

        LikeResponse resultCreate = likeService.switchUserLikeForArticle(articleId, userId);

        assertTrue(resultCreate.isLiked());
        assertEquals(1, resultCreate.getLikesCount());

        verify(likesRepository, times(1)).save(any(Like.class));

        when(likesRepository.findByArticleIdAndAuthorId(articleId, userId)).thenReturn(Optional.of(testLike));
        when(likesRepository.countByArticleId(articleId)).thenReturn(0);

        LikeResponse resultDelete = likeService.switchUserLikeForArticle(articleId, userId);

        assertFalse(resultDelete.isLiked());
        assertEquals(0, resultDelete.getLikesCount());

        verify(likesRepository, times(1)).delete(testLike);
    }
}
