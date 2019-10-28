package com.sujin.happytripping;

import java.io.Serializable;

public class UserDetails {

    String username;
    String category;

    public UserDetails(String username,String category)
    {

        this.username=username;
        this.category=category;
    }



    public String getUsername()
    {
       return username;
    }

    public String getCategory() {
        return category;
    }
}
