package online.newspaper.backend.dto;

import online.newspaper.backend.models.Like;

public class LikeResponse {
    private int articleId;
    private boolean isLiked;
    private int likesCount;

    public LikeResponse() { }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }
}
