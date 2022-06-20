package com.example.todo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button btnAdd;

    private EditText inputText;
    private EditText inputMonth;
    private EditText inputDay;
    private EditText inputYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference("message");

        dbRef.setValue("Hello world!");

        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.buttonAdd);

        inputText = findViewById(R.id.todoEditText);

        inputMonth = findViewById(R.id.editTextMonth);
        inputDay = findViewById(R.id.editTextDay);
        inputYear = findViewById(R.id.editTextYear);

        btnAdd.setVisibility(View.GONE);
        inputMonth.setVisibility(View.GONE);
        inputDay.setVisibility(View.GONE);
        inputYear.setVisibility(View.GONE);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });

        inputText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAdd.setVisibility(View.VISIBLE);
                inputMonth.setVisibility(View.VISIBLE);
                inputDay.setVisibility(View.VISIBLE);
                inputYear.setVisibility(View.VISIBLE);
            }
        });

        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //goes to register activity if no logged user is detected in the device
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    private void setUpListViewListener() { //method that removes an item if a long click happens
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Item removed", Toast.LENGTH_LONG).show();

                items.remove(i); //remove the item in the list with the index i
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void addItem(View view) {
        String itemText = inputText.getText().toString(); //extracts the string from the editText
        String itemDate = inputMonth.getText().toString()+"-"+inputDay.getText().toString()+"-"+
                inputYear.getText().toString();

        if(!(itemText.equals("")) && !(itemDate.equals("--"))) { //verifies if the string is not empty
                itemsAdapter.add(itemText + "   " + itemDate);
                inputText.setText(""); //resets the string inside the input so it can be used again
                inputMonth.setText("");
                inputDay.setText("");
                inputYear.setText("");
        } else {
            Toast.makeText(getApplicationContext(), "Cannot add empty text", Toast.LENGTH_SHORT).show();
        }
    }
}