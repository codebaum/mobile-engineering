package com.codebaum.livingsocialchallenge.model;

/**
 * Represents the user details that are stored in the JSON.
 *
 * Created by brandon on 11/26/14.
 */
public class User
{
    private String name;
    private Avatar avatar;
    private String username;

    public String getName()
    {
        return name;
    }

    public Avatar getAvatar()
    {
        return avatar;
    }

    public String getUsername()
    {
        return username;
    }
}
