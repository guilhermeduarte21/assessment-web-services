package com.guilherme.duarte.api;

public class CriarPostRequest {

    private Long userId;
    private String title;
    private String body;

    public CriarPostRequest(Long userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
