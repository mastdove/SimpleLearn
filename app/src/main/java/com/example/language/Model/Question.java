package com.example.language.Model;

public class Question {
    private String Question, AnswerA, AnswerB,
            AnswerC, AnswerD, AnswerE, CorrectAnswer, CategoryId, IsImageQuestion;

    public Question() {
    }

    public Question(String question, String answerA, String answerB, String answerC, String answerD,
                    String answerE, String correctAnswer, String categoryId, String isImageQuestion) {
        Question = question;
        AnswerA = answerA;
        AnswerB = answerB;
        AnswerC = answerC;
        AnswerD = answerD;
        AnswerE = answerE;
        CorrectAnswer = correctAnswer;
        this.CategoryId = categoryId;
        this.IsImageQuestion = isImageQuestion;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswerA() {
        return AnswerA;
    }

    public void setAnswerA(String answerA) {
        AnswerA = answerA;
    }

    public String getAnswerB() {
        return AnswerB;
    }

    public void setAnswerB(String answerB) {
        AnswerB = answerB;
    }

    public String getAnswerC() {
        return AnswerC;
    }

    public void setAnswerC(String answerC) {
        AnswerC = answerC;
    }

    public String getAnswerD() {
        return AnswerD;
    }

    public void setAnswerD(String answerD) {
        AnswerD = answerD;
    }

    public String getAnswerE() {
        return AnswerE;
    }

    public void setAnswerE(String answerE) {
        AnswerE = answerE;
    }

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        this.CategoryId = categoryId;
    }

    public String getIsImageQuestion() {
        return IsImageQuestion;
    }

    public void setIsImageQuestion(String isImageQuestion) {
        this.IsImageQuestion = isImageQuestion;
    }
}