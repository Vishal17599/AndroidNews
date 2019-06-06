package com.example.android.newsandroid;

public class list {
    private String title;
    private String image;
    private String source;
    private String description;
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public String getSource() {
        return source;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
