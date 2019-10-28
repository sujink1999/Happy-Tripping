package com.sujin.happytripping;

import java.io.Serializable;

public class Blog implements Serializable {
    String authorName, articleName,body,location;

    public Blog(String authorName, String articleName,String body, String location){

        this.authorName=authorName;
        this.articleName=articleName;
        this.body=body;
        this.location=location;

    }

    public String getArticleName() {
        return articleName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getBody() {
        return body;
    }

    public String getLocation() {
        return location;
    }
}
