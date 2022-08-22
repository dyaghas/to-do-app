package com.example.todo;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference ref;

    private FirebaseAuth mAuth;

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button btnAdd;
    private Button btnConfig;

    private EditText inputText;
    private EditText inputMonth;
    private EditText inputDay;
    private EditText inputYear;

    private String itemText;
    private String itemDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConfig = findViewById(R.id.btnConfig);

        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.buttonAdd);

        inputText = findViewById(R.id.todoEditText);

        inputMonth = findViewById(R.id.editTextMonth);
        inputDay = findViewById(R.id.editTextDay);
        inputYear = findViewById(R.id.editTextYear);

        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();

        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ConfigActivity.class));
            }
        });

        //Firebase authentication
        mAuth = FirebaseAuth.getInstance();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //goes to login activity if no logged user is detected in the device
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else {
            //Firebase realtime database
            //Takes the current user reference in the realtime database
            ref = FirebaseDatabase.getInstance().getReference(Objects.requireNonNull(mAuth.getUid()));

            //Loads all references previously created by the user
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    items.clear();
                    for (DataSnapshot i : snapshot.getChildren()) {
                        items.add(i.getValue().toString());
                    }
                    itemsAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    private void setUpListViewListener() { //method that removes an item if a long click happens
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();

                removeData(i);

                return true;
            }
        });
    }

    //Methods

    private boolean checkDate() {
        Locale locale = Locale.getDefault();

        //Formats date with Locale.ITALY (there's no locale for Brazil).
        if(locale.toString().equals("pt_BR")) {
            String date = getItemDate();
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
            df.setLenient(false);
            try {
                df.parse(date);
            } catch (ParseException pe) {
                String toastMessage = MainActivity.this.getResources().
                        getString(R.string.invalid_date_format);
                Toast.makeText(MainActivity.this, toastMessage,
                        Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        } else {
            //Formats date with Locale.ENGLISH
            String date = getItemDate();
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ENGLISH);
            df.setLenient(false);
            try {
                df.parse(date);
            } catch (ParseException pe) {
                String toastMessage = MainActivity.this.getResources().
                        getString(R.string.invalid_date_format);
                Toast.makeText(MainActivity.this, toastMessage,
                        Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    }

    //remove the list item with index i
    private void removeData(int i) {
        ref.child(String.valueOf(i)).removeValue().
                addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    String toastMessage = MainActivity.this.getResources().
                            getString(R.string.item_removed);
                    Toast.makeText(MainActivity.this, toastMessage,
                            Toast.LENGTH_SHORT).show();
                } else {
                    String toastMessage = MainActivity.this.getResources().
                            getString(R.string.error);
                    Toast.makeText(MainActivity.this, toastMessage,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        items.remove(i);
        itemsAdapter.notifyDataSetChanged();
    }

    private void addItem(View view) {
        setItemText();
        setItemDate();

        //verifies if the inputs are valid
        if(!(getItemText().equals(""))) {
            if(checkDate()) {
                itemsAdapter.add(getItemText() + "   " + getItemDate());
                //resets the string inside the input so it can be used again
                inputText.setText("");
                inputMonth.setText("");
                inputDay.setText("");
                inputYear.setText("");
            }
        } else {
            String toastMessage = MainActivity.this.getResources().
                    getString(R.string.empty_text);
            Toast.makeText(MainActivity.this, toastMessage,
                    Toast.LENGTH_SHORT).show();
        }
        //Firebase realtime database
        ref.setValue(items);
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