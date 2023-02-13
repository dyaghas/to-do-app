package com.example.todo;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference ref;

    private FirebaseAuth mAuth;

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button btnNewTodo;
    private Button btnConfig;

    private String itemText;
    private String itemDate;

    private ActivityResultLauncher<Intent> activityResultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConfig = findViewById(R.id.btnConfig);

        listView = findViewById(R.id.listView);
        btnNewTodo = findViewById(R.id.buttonNew);

        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK) {
                    itemText = result.getData().getStringExtra("message");
                    itemDate = result.getData().getStringExtra("date");

                    addItem(itemText, itemDate);
                }
            }
        });

        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ConfigActivity.class));
            }
        });

        //Firebase authentication
        mAuth = FirebaseAuth.getInstance();


        btnNewTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityResultLauncher.launch(new Intent(
                        MainActivity.this, AddTodoActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkUser();
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

    private void checkUser() {
        //goes to login activity if no logged user is detected in the device
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else {
            //Firebase realtime database
            //Takes the current user reference in the realtime database
            ref = FirebaseDatabase.getInstance().getReference(Objects.requireNonNull(mAuth.getUid()));
            ref.keepSynced(true);

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

    private boolean checkDate() {
        Locale locale = Locale.getDefault();

        //Formats date with Locale.ITALY (there's no locale for Brazil).
        if(locale.toString().equals("pt_BR")) {
            String date = itemDate;
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
            String date = itemDate;
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

        String itemValue = (String) listView.getItemAtPosition(i);

        ref.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()) {
                    if(data.getValue().equals(itemValue)) {
                        data.getRef().removeValue();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addItem(String itemText, String itemDate) {
        //verifies if the inputs are valid
        if(!(itemText.equals(""))) {
            if(checkDate()) {
                itemsAdapter.add(itemText + "   " + itemDate);
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
}