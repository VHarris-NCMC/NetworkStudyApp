package net.shadyoakdesign.netplus;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


import net.shadyoakdesign.tools.DBHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuizActivity extends AppCompatActivity {

    private TextView flashcardTextView;
    private boolean isQuestionShowing = true;
    private DBHelper questionFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        flashcardTextView = findViewById(R.id.text_flashcard);
        flashcardTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipFlashcard();
            }
        });
        questionFetcher = new DBHelper();
        TextView tv = findViewById(R.id.text_flashcard);
        ResultSet rs = null;
        questionFetcher.connect();

        questionFetcher.disconnect();
        tv.setText(rs.toString());

    }

    private void flipFlashcard() {
        if (isQuestionShowing) {
            flashcardTextView.setText("No");
            isQuestionShowing = false;
        } else {
            flashcardTextView.setText("Am I ready for the exam?");
            isQuestionShowing = true;
        }
    }
}
