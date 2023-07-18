package net.shadyoakdesign.netplus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView textFlashcard;
    private LinearLayout answersContainer;
    private List<RadioButton> optionRadioButtons;
    private Question currentQuestion;

    private int currentQuestionIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialize UI elements
        textFlashcard = findViewById(R.id.text_flashcard);
        answersContainer = findViewById(R.id.answers_container);
        optionRadioButtons = new ArrayList<>();
        optionRadioButtons.add(findViewById(R.id.option_a));
        optionRadioButtons.add(findViewById(R.id.option_b));
        optionRadioButtons.add(findViewById(R.id.option_c));
        optionRadioButtons.add(findViewById(R.id.option_d));

        // Load and display the first question
        loadNextQuestion();

        // Set the onClickListener for the submit button
        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitAnswer();
            }
        });
    }


    private void loadNextQuestion() {

        // Check if there are more questions
        if (currentQuestionIndex < exampleQuestions.size()) {
            // Get the next question
            currentQuestion = exampleQuestions.get(currentQuestionIndex);

            // Display the question
            displayQuestion();

            // Increment the question index
            currentQuestionIndex++;
        } else {
            // Display quiz completion message
            Toast.makeText(this, "Quiz completed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayQuestion() {
        // Set the question prompt
        textFlashcard.setText(currentQuestion.getPrompt());

        // Set the answer options
        List<String> answers = currentQuestion.getAnswers();
        for (int i = 0; i < optionRadioButtons.size(); i++) {
            RadioButton radioButton = optionRadioButtons.get(i);
            if (i < answers.size()) {
                radioButton.setText(answers.get(i));
                radioButton.setChecked(false);
                radioButton.setVisibility(View.VISIBLE);
            } else {
                radioButton.setVisibility(View.GONE);
            }
        }
    }

    public void onSubmitAnswer() {
        // Get the selected answer
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        String selectedAnswer = selectedRadioButton.getText().toString();

        // Check if the selected answer is correct
        boolean isCorrect = currentQuestion.isAnswerCorrect(selectedAnswer);

        // Show a popup with the correctness of the answer
        showResultPopup(isCorrect);

        // Load the next question
        loadNextQuestion();
    }

    private void showResultPopup(boolean isCorrect) {
        View popupView = getLayoutInflater().inflate(R.layout.popup_result, null);
        PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView textResult = popupView.findViewById(R.id.text_result);
        textResult.setText(isCorrect ? "Correct!" : "Incorrect!");
        popupWindow.showAsDropDown(textFlashcard, 0, 0);

        // Dismiss the popup after a short delay
        popupView.postDelayed(popupWindow::dismiss, 1500);
    }

    public static List<Question> exampleQuestions = new ArrayList<>();

    static {
        // Question 1
        String prompt1 = "What does DHCP stand for?";
        List<String> answers1 = new ArrayList<>();
        answers1.add("Dynamic Host Configuration Protocol");
        answers1.add("Domain Host Configuration Protocol");
        answers1.add("Distributed Host Control Protocol");
        answers1.add("Domain Host Control Protocol");
        String correctAnswer1 = "Dynamic Host Configuration Protocol";
        int difficulty1 = 2;


        // Question 2
        String prompt2 = "Which protocol is used to transfer files from a local host to a remote host over a network?";
        List<String> answers2 = new ArrayList<>();
        answers2.add("HTTP");
        answers2.add("SMTP");
        answers2.add("FTP");
        answers2.add("TCP");
        String correctAnswer2 = "FTP";
        int difficulty2 = 1;


        // Question 3
        String prompt3 = "Which network device operates at the Data Link layer of the OSI model?";
        List<String> answers3 = new ArrayList<>();
        answers3.add("Router");
        answers3.add("Switch");
        answers3.add("Hub");
        answers3.add("Bridge");
        String correctAnswer3 = "Switch";
        int difficulty3 = 1;


        // Question 4
        String prompt4 = "What is the default subnet mask for a Class C network?";
        List<String> answers4 = new ArrayList<>();
        answers4.add("255.0.0.0");
        answers4.add("255.255.0.0");
        answers4.add("255.255.255.0");
        answers4.add("255.255.255.255");
        String correctAnswer4 = "255.255.255.0";
        int difficulty4 = 1;


        // Question 5
        String prompt5 = "What is the maximum number of hosts that can be addressed on a subnet with a 24-bit mask?";
        List<String> answers5 = new ArrayList<>();
        answers5.add("254");
        answers5.add("256");
        answers5.add("65534");
        answers5.add("510");
        String correctAnswer5 = "254";
        int difficulty5 = 2;

    }
}
