package net.shadyoakdesign.netplus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Question {
    private int id;
    private String prompt;
    private List<String> answers;
    private String correctAnswer;
    private int difficulty;

    public Question(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.prompt = resultSet.getString("prompt");
        this.answers = getAnswersFromResultSet(resultSet);
        this.correctAnswer = resultSet.getString("correct_answer");
        this.difficulty = resultSet.getInt("difficulty");
    }


    public Question(int id, String prompt, List<String> answers, String correctAnswer, int difficulty) {
        this.id = id;
        this.prompt = prompt;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
    }
    public List<String> getAnswersFromResultSet(ResultSet resultSet) throws SQLException {
        List<String> answers = new ArrayList<>();
        answers.add(resultSet.getString("answer1"));
        answers.add(resultSet.getString("answer2"));
        answers.add(resultSet.getString("answer3"));
        answers.add(resultSet.getString("answer4"));
        return answers;
    }

    public int getId() {
        return id;
    }

    public String getPrompt() {
        return prompt;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public boolean isAnswerCorrect(String selectedAnswer) {
        return correctAnswer.equals(selectedAnswer);
    }
}
