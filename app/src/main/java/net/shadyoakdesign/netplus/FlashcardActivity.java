package net.shadyoakdesign.netplus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class FlashcardActivity extends AppCompatActivity {

    private Question question;
    private boolean isAnswerVisible = false;
    private Button nextCardButton;
    private List<Question> questions;
    private TextView textFlashCard;
    private int currentQuestionIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);
        textFlashCard = findViewById(R.id.text_flashcard);
        nextCardButton = findViewById(R.id.button_getnext);

        textFlashCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFlashcardClick(v);
            }
        });
        nextCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextButtonClick();
            }
        });

    }


    public void onFlashcardClick(View view) {
        TextView textFlashcard = findViewById(R.id.text_flashcard);
        if (!isAnswerVisible) {
            textFlashcard.setText(question.getCorrectAnswer());
            nextCardButton.setVisibility(View.VISIBLE);
            nextCardButton.setText("Next");
            isAnswerVisible = true;
        } else {
            textFlashcard.setText(question.getPrompt());
            isAnswerVisible = false;


        }
    }
    private void onNextButtonClick() {

        if (currentQuestionIndex < QuizActivity.exampleQuestions.size()) {
            // Get the next question
            question = QuizActivity.exampleQuestions.get(currentQuestionIndex);
            nextCardButton.setVisibility(View.INVISIBLE);
            textFlashCard.setVisibility(View.VISIBLE);
            // Display the next question/answer on the flashcard
            displayQuestion(question);

            // Increment the question index
            currentQuestionIndex++;
        } else {
            // Return to the parent activity or perform any other desired action
            finish(); // Finish the current activity and return to the parent activity
        }


    }

    private void displayQuestion(Question q) {
        textFlashCard.setText(q.getPrompt());
    }

}
