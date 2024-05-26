package com.example.quizappraman;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private TextView progressText, questionText, scoreText;
    private RadioButton option1, option2, option3, option4;
    private RadioGroup optionsGroup;
    private MaterialButton nextButton;

    private int currentQuestionIndex = 0;
    private int totalQuestions = 5;
    private int score = 0;

    private String[] questions = {
            "What is the default value of an int variable in Java?",
            "Which method is used to start a thread in Java?",
            "Which of the following is not a Java keyword?",
            "What is the size of a boolean variable in Java?",
            "Which of the following is a marker interface?"
    };

    private String[][] options = {
            {"0", "1", "null", "-1"},
            {"run()", "start()", "init()", "begin()"},
            {"static", "Boolean", "void", "private"},
            {"1 bit", "1 byte", "8 bytes", "4 bytes"},
            {"Serializable", "Cloneable", "Remote", "All of the above"}
    };

    private int[] correctAnswers = {0, 1, 1, 0, 3};

    private long backButtonPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressText = findViewById(R.id.progress_text);
        questionText = findViewById(R.id.question_text);
        option1 = findViewById(R.id.option_1);
        option2 = findViewById(R.id.option_2);
        option3 = findViewById(R.id.option_3);
        option4 = findViewById(R.id.option_4);
        optionsGroup = findViewById(R.id.options_group);
        nextButton = findViewById(R.id.next_button);
        scoreText = findViewById(R.id.score_text);

        displayQuestion();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedOptionId = optionsGroup.getCheckedRadioButtonId();
                if (selectedOptionId == -1) {
                    Toast.makeText(MainActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedRadioButton = findViewById(selectedOptionId);
                int selectedOptionIndex = optionsGroup.indexOfChild(selectedRadioButton);
                if (selectedOptionIndex == correctAnswers[currentQuestionIndex]) {
                    score++;
                }

                Animation fadeOut = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out);
                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        currentQuestionIndex++;

                        if (currentQuestionIndex < totalQuestions) {
                            displayQuestion();
                            Animation fadeIn = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in);
                            findViewById(R.id.quiz_layout).startAnimation(fadeIn);
                        } else {
                            Toast.makeText(MainActivity.this, "Quiz finished! Your score: " + score, Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });

                findViewById(R.id.quiz_layout).startAnimation(fadeOut);
            }
        });
    }

    private void displayQuestion() {
        optionsGroup.clearCheck();
        progressText.setText("Question " + (currentQuestionIndex + 1) + "/" + totalQuestions);
        questionText.setText(questions[currentQuestionIndex]);
        option1.setText(options[currentQuestionIndex][0]);
        option2.setText(options[currentQuestionIndex][1]);
        option3.setText(options[currentQuestionIndex][2]);
        option4.setText(options[currentQuestionIndex][3]);
        scoreText.setText("Score: " + score);
    }

    @Override
    public void onBackPressed() {
        if (backButtonPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        backButtonPressedTime = System.currentTimeMillis();
    }
}
