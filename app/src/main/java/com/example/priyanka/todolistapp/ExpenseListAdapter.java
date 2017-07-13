package com.example.priyanka.todolistapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.DataFormatException;

/**
 * Created by PRIYANKA on 02-07-2017.
 */

public class ExpenseListAdapter extends ArrayAdapter {
    Expense e;
    ArrayList<Expense> expenseArrayList;
    ArrayList<Integer> arrayList=MainActivity.list;  /// ESPICIALLY KEPT FOR CHECKBOX
    Context context;
    public ExpenseListAdapter(Context context, ArrayList<Expense> expenseList){
        super(context, 0);
        this.context=context;
        this.expenseArrayList=expenseList;
    }
    @Override
    public int getCount() {
        return expenseArrayList.size();
    }
    static class ExpenseViewHolder{
        TextView title;
        TextView date;
        CheckBox c;
        ExpenseViewHolder(TextView nameTextView , TextView date,CheckBox c){
            this.title = nameTextView;
            this.date=date;
            this.c=c;
        }
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.list_view,null);
            TextView title=(TextView) convertView.findViewById(R.id.textView);
            CheckBox checkBox=(CheckBox) convertView.findViewById(R.id.checkBox);
            TextView date=(TextView) convertView.findViewById(R.id.date) ;
            ExpenseViewHolder holder=new ExpenseViewHolder(title,date,checkBox);
            convertView.setTag(holder);
        }
        Expense e=expenseArrayList.get(position);
        ExpenseViewHolder expenseViewHolder = (ExpenseViewHolder)convertView.getTag();
        Log.i("message",e.title);
        expenseViewHolder.title.setText(e.title);
        if(arrayList.size()>position &&  arrayList.get(position)==1){
            Log.i("onAdd",arrayList.size()+"");
            expenseViewHolder.c.setChecked(true);
        }
        if(arrayList.size()>position &&  arrayList.get(position)==0){
            expenseViewHolder.c.setChecked(false);
        }
        if( expenseViewHolder.c.isChecked()){
            arrayList.set(position,1);
        }else{
            arrayList.set(position,0);
        }
        expenseViewHolder.c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i("messd",arrayList.size() +"");
                int i = 0;
                if(isChecked){i=1;}
                arrayList.set(position,i);
                return;
            }
        });
        Date date=new Date(e.epoch);
        Time tim=new Time(e.epoch);
        Log.i("epoch",e.epoch+"*"+date);
        DateFormat dateFormat=DateFormat.getDateInstance();
        expenseViewHolder.date.setText(dateFormat.format(date) + "  "+tim);
        return convertView;
    }
}
