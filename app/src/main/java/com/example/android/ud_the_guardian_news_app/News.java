package com.example.android.ud_the_guardian_news_app;

public class News {
    private String tittle;
    private String section;
    private String author;
    private String webURL;
    private String date;
    private String time;

    public News(String tittle, String section, String author, String webURL, String date, String time) {
        this.tittle = tittle;
        this.section = section;
        this.author = author;
        this.webURL = webURL;
        this.date = date;
        this.time = time;
    }

    public News(String tittle, String section, String webURL, String date, String time) {
        this.tittle = tittle;
        this.section = section;
        this.webURL = webURL;
        this.date = date;
        this.time = time;
    }

    public News(String tittle, String section, String author, String webURL) {
        this.tittle = tittle;
        this.section = section;
        this.author = author;
        this.webURL = webURL;
    }

    public News(String tittle, String section, String webURL) {
        this.tittle = tittle;
        this.section = section;
        this.webURL = webURL;
    }


    public String getTittle() {
        return tittle;
    }

    public String getSection() {
        return section;
    }

    public String getAuthor() {
        return author;
    }

    public String getWebURL() {
        return webURL;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
