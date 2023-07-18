package net.shadyoakdesign.netplus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizRepository {
    private DBHelper dbHelper;

    public QuizRepository() {
        dbHelper = new DBHelper();
    }

    public List<Question> getQuizQuestions() {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM questions";

        try {
            ResultSet resultSet = dbHelper.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Question question = new Question(resultSet);
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        } finally {
            dbHelper.close();
        }

        return questions;
    }
}
