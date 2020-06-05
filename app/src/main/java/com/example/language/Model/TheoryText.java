package com.example.language.Model;

public class TheoryText {
    private String Text, CategoryId;

    public TheoryText() {
    }

    public TheoryText(String text, String categoryId) {
        Text = text;
        CategoryId = categoryId;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }
}
