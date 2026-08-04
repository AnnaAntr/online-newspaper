package online.newspaper.backend.services;

import online.newspaper.backend.models.Article;
import online.newspaper.backend.repositories.ArticlesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @Mock
    private ArticlesRepository articlesRepository;

    @InjectMocks
    private ArticleService articleService;

    private Article article1;
    private List<Article> testArticles;

    @BeforeEach
    void setUp() {
        article1 = new Article();
        article1.setId(1);
        article1.setTitle("Test article 1");
        article1.setContent("Test content 1");
        article1.setImageUrl("Test image url 1");
        article1.setCreatedAt(new Date());

        Article article2 = new Article();
        article2.setId(2);
        article2.setTitle("Test article 2");
        article2.setContent("Test content 2");
        article2.setImageUrl("Test image url 2");
        article2.setCreatedAt(new Date());

        testArticles = List.of(article1, article2);
    }

    @Test
    void findArticle_WhenExists_ShouldReturnArticle() {
        int articleId = 1;
        when(articlesRepository.findById(articleId)).thenReturn(Optional.of(article1));

        Article result = articleService.findArticle(articleId);

        assertNotNull(result);
        assertEquals(article1.getId(), result.getId());
        assertEquals(article1.getTitle(), result.getTitle());
        assertEquals(article1.getContent(), result.getContent());

        verify(articlesRepository, times(1)).findById(articleId);
    }

    @Test
    void findArticle_WhenDoesNotExist_ShouldReturnNull() {
        int articleId = 50;
        when(articlesRepository.findById(articleId)).thenReturn(Optional.empty());

        Article result = articleService.findArticle(articleId);

        assertNull(result);

        verify(articlesRepository, times(1)).findById(articleId);
    }

    @Test
    void findFreshArticlesWithPagination_ShouldReturnPageOfArticles() {
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> expectedPage = new PageImpl<>(testArticles, pageable, testArticles.size());

        when(articlesRepository.findByCreatedAtAfterOrderByCreatedAtDesc(any(Date.class), eq(pageable))).thenReturn(expectedPage);

        Page<Article> result = articleService.findFreshArticlesWithPagination(page, size);

        assertNotNull(result);
        assertEquals(expectedPage.getTotalElements(), result.getTotalElements());
        assertEquals(expectedPage.getContent().size(), result.getContent().size());
        assertEquals(expectedPage.getContent().get(0).getTitle(), result.getContent().get(0).getTitle());

        verify(articlesRepository, times(1)).findByCreatedAtAfterOrderByCreatedAtDesc(any(Date.class), eq(pageable));
    }

    @Test
    void findFreshArticlesWithPagination_WhenNoArticles_ShouldReturnEmptyPage() {
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> expectedPage = new PageImpl<>(List.of(), pageable, 0);

        when(articlesRepository.findByCreatedAtAfterOrderByCreatedAtDesc(any(Date.class), eq(pageable))).thenReturn(expectedPage);

        Page<Article> result = articleService.findFreshArticlesWithPagination(page, size);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.getTotalElements());

        verify(articlesRepository, times(1)).findByCreatedAtAfterOrderByCreatedAtDesc(any(Date.class), eq(pageable));
    }

    @Test
    void findFreshArticlesWithPagination_ShouldUseCorrectDateFilter() {
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);

        ArgumentCaptor<Date> dateCaptor = ArgumentCaptor.forClass(Date.class);

        when(articlesRepository.findByCreatedAtAfterOrderByCreatedAtDesc(dateCaptor.capture(), eq(pageable)))
                .thenReturn(new PageImpl<>(testArticles, pageable, testArticles.size()));

        articleService.findFreshArticlesWithPagination(page, size);

        Date capturedDate = dateCaptor.getValue();
        assertNotNull(capturedDate);

        long currentTime = System.currentTimeMillis();
        long capturedTime = capturedDate.getTime();
        long difference = currentTime - capturedTime;

        assertTrue(difference >= (24 * 60 * 60 * 1000 - 1000) && difference <= (24 * 60 * 60 * 1000 + 1000));
    }

    @Test
    void findArchiveArticlesWithPagination_ShouldReturnPageOfArticles() {
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> expectedPage = new PageImpl<>(testArticles, pageable, testArticles.size());

        when(articlesRepository.findByCreatedAtBeforeOrderByCreatedAtDesc(any(Date.class), eq(pageable))).thenReturn(expectedPage);

        Page<Article> result = articleService.findArchiveArticlesWithPagination(page, size);

        assertNotNull(result);
        assertEquals(expectedPage.getTotalElements(), result.getTotalElements());
        assertEquals(expectedPage.getContent().size(), result.getContent().size());
        assertEquals(expectedPage.getContent().get(0).getTitle(), result.getContent().get(0).getTitle());

        verify(articlesRepository, times(1)).findByCreatedAtBeforeOrderByCreatedAtDesc(any(Date.class), eq(pageable));
    }

    @Test
    void findArchiveArticlesWithPagination_WhenNoArticles_ShouldReturnEmptyPage() {
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> expectedPage = new PageImpl<>(List.of(), pageable, 0);

        when(articlesRepository.findByCreatedAtBeforeOrderByCreatedAtDesc(any(Date.class), eq(pageable))).thenReturn(expectedPage);

        Page<Article> result = articleService.findArchiveArticlesWithPagination(page, size);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.getTotalElements());

        verify(articlesRepository, times(1)).findByCreatedAtBeforeOrderByCreatedAtDesc(any(Date.class), eq(pageable));
    }

    @Test
    void finArchiveArticlesWithPagination_ShouldUseCorrectDateFilter() {
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);

        ArgumentCaptor<Date> dateCaptor = ArgumentCaptor.forClass(Date.class);

        when(articlesRepository.findByCreatedAtBeforeOrderByCreatedAtDesc(dateCaptor.capture(), eq(pageable)))
                .thenReturn(new PageImpl<>(testArticles, pageable, testArticles.size()));

        articleService.findArchiveArticlesWithPagination(page, size);

        Date capturedDate = dateCaptor.getValue();
        assertNotNull(capturedDate);

        long currentTime = System.currentTimeMillis();
        long capturedTime = capturedDate.getTime();
        long difference = currentTime - capturedTime;

        assertTrue(difference >= (24 * 60 * 60 * 1000 - 1000) && difference <= (24 * 60 * 60 * 1000 + 1000));
    }
}
