package com.example.priyanka.todolistapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.priyanka.todolistapp.R.id.text;

/**
 * Created by PRIYANKA on 03-07-2017.
 */

public class ExpenseOpenHelper extends SQLiteOpenHelper {
    static String TABLE_NAME="Expenses";
    static String TITLE="title";
    static String  ID="id";
    static String PRICE="price";
    static String CATEGORY="category";
    static String EPOCH="epoch";
    public static ExpenseOpenHelper expenseOpenHelper;

    public static ExpenseOpenHelper getExpenseOpenHelper(Context context){
        if(expenseOpenHelper==null){
            expenseOpenHelper=new ExpenseOpenHelper(context);
        }
        return expenseOpenHelper;
    }

    private ExpenseOpenHelper(Context context) {
        super(context, "expenses.db", null, 1);
}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Query="Create Table "+ExpenseOpenHelper.TABLE_NAME+" ("+ ExpenseOpenHelper.ID+" integer primary key autoincrement, "+
                ExpenseOpenHelper.TITLE + " text, " +ExpenseOpenHelper.PRICE+" real, " + ExpenseOpenHelper.CATEGORY+ " text, "+
                ExpenseOpenHelper.EPOCH+" bigint)";
        db.execSQL(Query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
