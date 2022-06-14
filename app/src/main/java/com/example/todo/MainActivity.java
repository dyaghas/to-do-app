package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.buttonAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });

        items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();
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
        EditText input = findViewById(R.id.todoEditText);
        String itemText = input.getText().toString(); //extracts the string from the editText

        if(!(itemText.equals(""))) { //verifies if the string is not empty
            itemsAdapter.add(itemText);
            input.setText(""); //resets the string inside the input so it can be used again
        } else {
            Toast.makeText(getApplicationContext(), "cannot add empty text", Toast.LENGTH_SHORT).show();
        }
    }
}