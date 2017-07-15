package com.example.priyanka.todolistapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.DateFormat;
import java.text.Format;
import java.util.Calendar;
import java.util.Date;


public class ExpenseDetailActivity extends AppCompatActivity {
    EditText title;
    EditText price;
    EditText category;
    String whatData;
    EditText dateEditText;
    EditText timeEditText;
    Calendar calendar;
    TextView textView5;
    long epoch;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);
        calendar=Calendar.getInstance();
        title = (EditText) findViewById(R.id.title);
        price = (EditText) findViewById(R.id.price);
        category = (EditText) findViewById(R.id.editText3);
        textView5=(TextView) findViewById(R.id.textView5);
        dateEditText = (EditText) findViewById(R.id.date);
        timeEditText=(EditText) findViewById(R.id.time);
        b = (Button) findViewById(R.id.submit);
        Intent i = getIntent();
        whatData=i.getStringExtra("Category");
        category.setText(whatData);
        final Expense initial = (Expense) i.getSerializableExtra("key");
        if (initial != null) {
            Log.i("duck", "in_here");
            title.setText(initial.title);
            price.setText(initial.price +"");
            category.setText(initial.category);
            DateFormat dateFormat=DateFormat.getDateInstance();
            dateEditText.setText(dateFormat.format(initial.epoch));
            Time t=new Time(initial.epoch);
            Date d=new Date(initial.epoch);
            timeEditText.setText(t+"");
        }
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(price.getText().toString().trim().equals("")){
                    price.setHint("THIS FIELD CAN'T BE LEFT EMPTY");
                    price.setHintTextColor(Color.RED);
                    return;
                }
                ExpenseOpenHelper expenseopenhelper = ExpenseOpenHelper.getExpenseOpenHelper(ExpenseDetailActivity.this);
                SQLiteDatabase db = expenseopenhelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(ExpenseOpenHelper.CATEGORY, category.getText().toString());
                cv.put(ExpenseOpenHelper.TITLE, title.getText().toString());
                cv.put(ExpenseOpenHelper.PRICE, Double.parseDouble(price.getText().toString()));
                cv.put(ExpenseOpenHelper.EPOCH,epoch);
                if (initial != null) {
                    db.update(ExpenseOpenHelper.TABLE_NAME, cv, " id = " + initial.id, null);
                } else {
                    db.insert(ExpenseOpenHelper.TABLE_NAME, null, cv);
                }
                Intent j = new Intent();
                setResult(2, j);
                finish();
            }
        });
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
                if(!dateEditText.getText().equals("")){
                    timeEditText.setVisibility(View.VISIBLE);
                    textView5.setVisibility(View.VISIBLE);
                }
            }
        });
        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime();
            }
        });
    }
    private void updateTime() {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeEditText.setText(hourOfDay+":"+minute);
                epoch=epoch+(hourOfDay*60+minute)*60000;
            }
        }, 0, 0, true).show();
    }
    private void updateDate() {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                epoch=epoch+calendar.getTime().getTime();
                epoch=epoch-(calendar.get(Calendar.HOUR_OF_DAY)*60+calendar.get(Calendar.MINUTE))*60000-calendar.get(Calendar.SECOND)*1000;
                Date dt=new Date(epoch);
                DateFormat dateFormat=DateFormat.getDateInstance();
                dateEditText.setText(dateFormat.format(dt));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}



