package online.newspaper.backend.dto;

import online.newspaper.backend.models.Comment;

import java.util.Date;

public class CommentResponse {
    private int id;
    private String text;
    private String authorName;
    private String authorSurname;
    private Date createdAt;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getContent();
        this.authorName = comment.getAuthor().getName();
        this.authorSurname = comment.getAuthor().getSurname();
        this.createdAt = comment.getCreatedAt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorSurname() {
        return authorSurname;
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurname = authorSurname;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
