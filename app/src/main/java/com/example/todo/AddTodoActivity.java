package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTodoActivity extends AppCompatActivity {

    private EditText inputText;
    private EditText inputMonth;
    private EditText inputDay;
    private EditText inputYear;

    private String todoMessage;
    private String month;

    private String itemText;
    private String itemDate;

    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        inputText = findViewById(R.id.editTextTodo);
        inputMonth = findViewById(R.id.editTextMonth);
        inputDay = findViewById(R.id.editTextDay);
        inputYear = findViewById(R.id.editTextYear);

        btnAdd = findViewById(R.id.buttonAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setItemText();
                setItemDate();

                Intent todoIntent = new Intent();
                todoIntent.putExtra("message", itemText);
                todoIntent.putExtra("date", itemDate);
                setResult(Activity.RESULT_OK, todoIntent);
                finish();
            }
        });

    }

    //setters
    public void setItemText() {
        this.itemText = inputText.getText().toString();
    }

    public void setItemDate() {
        this.itemDate = inputMonth.getText().toString()+"/"+inputDay.getText().toString()+"/"+
                inputYear.getText().toString();
    }

    //getters
    public String getItemText() {
        return this.itemText;
    }

    public String getItemDate() {
        return this.itemDate;
    }
}