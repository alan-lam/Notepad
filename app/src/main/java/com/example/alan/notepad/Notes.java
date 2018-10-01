package com.example.alan.notepad;

public class Notes {
    private String title;
    private String content;

    public Notes(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }
}
