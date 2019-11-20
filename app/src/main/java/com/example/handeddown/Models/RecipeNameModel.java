package com.example.handeddown.Models;

public class RecipeNameModel {

    String url;
    String instructions;
    String key;
    String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

}
