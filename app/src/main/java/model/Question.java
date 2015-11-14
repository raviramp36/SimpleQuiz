package model;

/**
 * Created by Marcin on 13/11/2015.
 */
public class Question {

    private int id;
    private String question;
    private String correctAnswer;
    private String incorrAns1;
    private String incorrAns2;
    private String incorrAns3;

    public Question() {
    }

    public Question(int id, String question, String correctAnswer, String incorrAns1, String incorrAns2, String incorrAns3) {
        this.id = id;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrAns1 = incorrAns1;
        this.incorrAns2 = incorrAns2;
        this.incorrAns3 = incorrAns3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getIncorrAns1() {
        return incorrAns1;
    }

    public void setIncorrAns1(String incorrAns1) {
        this.incorrAns1 = incorrAns1;
    }

    public String getIncorrAns2() {
        return incorrAns2;
    }

    public void setIncorrAns2(String incorrAns2) {
        this.incorrAns2 = incorrAns2;
    }

    public String getIncorrAns3() {
        return incorrAns3;
    }

    public void setIncorrAns3(String incorrAns3) {
        this.incorrAns3 = incorrAns3;
    }
}
