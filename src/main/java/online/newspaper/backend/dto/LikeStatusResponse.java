package online.newspaper.backend.dto;

public class LikeStatusResponse {
    private boolean isLiked;
    private int likesCount;

    public LikeStatusResponse(boolean isLiked, int likesCount) {
        this.isLiked = isLiked;
        this.likesCount = likesCount;
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
