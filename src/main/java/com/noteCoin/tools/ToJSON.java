package com.noteCoin.tools;

import com.google.gson.Gson;

public class ToJSON {

    static public String convert(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
