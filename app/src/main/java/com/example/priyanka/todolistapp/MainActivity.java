package com.example.priyanka.todolistapp;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    String whatData;
     ArrayList<Expense> ExpenseList;
    ExpenseListAdapter expenseAdapter;
//    HashMap<Integer,Long> epocList;
    ArrayList<Integer> list;  // CHECKBOX
    FloatingActionButton Fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list=new ArrayList<>();
//        epocList=new HashMap<>();
        Intent data=getIntent();
        whatData=data.getStringExtra("start");             // whatData="allDatabasse"
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(myToolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Custom Title");

        recyclerView=(RecyclerView) findViewById(R.id.listview);
        ExpenseList=new ArrayList<>();
        expenseAdapter=new ExpenseListAdapter(this, ExpenseList, new ExpenseListAdapter.NotesClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i=new Intent(MainActivity.this,ExpenseDetailActivity.class);
                Expense exp=ExpenseList.get(position);
                i.putExtra("key",exp);
                startActivityForResult(i,1);
            }

            @Override
            public ArrayList<Integer> forCheckboxArrayList() {
                return list;
            }
        });
        recyclerView.setAdapter(expenseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT){
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int from=viewHolder.getAdapterPosition();
                int to=target.getAdapterPosition();
                Collections.swap(ExpenseList,from,to);
                Collections.swap(list,from,to);
                expenseAdapter.notifyItemMoved(from,to);
                return true;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int index=viewHolder.getAdapterPosition();
                int id= ExpenseList.get(index).id;
            ExpenseList.remove(index);
            list.remove(index);
            expenseAdapter.notifyItemRemoved(index);
            removeFrpmDatabase(id);
        }
    });
        itemTouchHelper.attachToRecyclerView(recyclerView);

//        recyclerView.setOnItemClickListener(new RecyclerView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i=new Intent(MainActivity.this,ExpenseDetailActivity.class);
//                Expense exp=ExpenseList.get(position);
//                i.putExtra("key",exp);
//                startActivityForResult(i,1);
//            }
//        });
        updateExpenseList();
        Fab=(FloatingActionButton) findViewById(R.id.fab);
        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,ExpenseDetailActivity.class);
                i.putExtra("Category",whatData);
                i.putExtra("index",ExpenseList.size());
                startActivityForResult(i,0);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_left);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add){
            final AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Add");
            builder.setMessage("Do you wanna add ??");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // ADD ITEM IN DATABASE AND SHOW IT ON ACTIVITY
                    Intent i=new Intent(MainActivity.this,ExpenseDetailActivity.class);
                    i.putExtra("Category",whatData);
                    i.putExtra("index",ExpenseList.size());
                    startActivityForResult(i,0);                      // REQUEST CODE = 0
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_left);
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            Dialog dialog=builder.create();
            dialog.show();
        }

        if(item.getItemId()==R.id.remove){
            removeChecked();
        }
        if(item.getItemId()==R.id.all){
            Log.i("selectall",list.size()+"");
            for(int i=0;i<ExpenseList.size();i++){
                    list.set(i,1);
            }
            expenseAdapter.notifyDataSetChanged();
        }
        if(item.getItemId()==R.id.deall){
            for(int i=0;i<ExpenseList.size();i++){
                    list.set(i,0);
            }
            expenseAdapter.notifyDataSetChanged();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if((requestCode==0||requestCode==1) && resultCode==2){
            list.add(0);
            Log.i("LISTLEN",list.size()+"");
            updateExpenseList();
        }
    }
    public void removeChecked(){
        ExpenseOpenHelper expenseOpenHelper=ExpenseOpenHelper.getExpenseOpenHelper(this);
        boolean isChanged=false;
        for(int i=0;i<ExpenseList.size();i++){
            if(list.get(i)==1){
                isChanged=true;
                Log.i("length",i+"");
                Log.i("what","in here");
                SQLiteDatabase db=expenseOpenHelper.getWritableDatabase();
                int id=ExpenseList.get(i).id;
                db.delete(ExpenseOpenHelper.TABLE_NAME," id = "+id,null);
                list.remove(i);
                ExpenseList.remove(i);
                i--;
            }
        }
        if(isChanged)
        {
            Log.i("lengths",list.size()+"");
            expenseAdapter.notifyDataSetChanged();
        }
    }

    public void removeFrpmDatabase(int id)
    {
        ExpenseOpenHelper expenseOpenHelper=ExpenseOpenHelper.getExpenseOpenHelper(this);
        SQLiteDatabase db=expenseOpenHelper.getWritableDatabase();
        db.delete(ExpenseOpenHelper.TABLE_NAME," id = "+id,null);
    }
    public void updateExpenseList(){
//        epocList.clear();
        ExpenseOpenHelper expenseOpenHelper=ExpenseOpenHelper.getExpenseOpenHelper(this);
        SQLiteDatabase db=expenseOpenHelper.getReadableDatabase();
        Cursor cursor=db.query(ExpenseOpenHelper.TABLE_NAME,null,null,null,null,null,null);
        ExpenseList.clear();
        int j=0;
        while(cursor.moveToNext())
        {
            if(j>=list.size()){
                list.add(0);
            }else {
                list.set(j,0);
            }
            String title = cursor.getString(cursor.getColumnIndex("title"));
            double price = cursor.getDouble(cursor.getColumnIndex("price"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String category = cursor.getString(cursor.getColumnIndex("category"));
            long epoch=cursor.getLong(cursor.getColumnIndex("epoch"));
            Expense e = new Expense(id, title,price,category,epoch);
            //epocList.put(j,epoch);
            if( ! whatData.equals(AllCategories.SCHEDULE_EXTRAS) && e.category.equals(whatData))
            {
                ExpenseList.add(e);
                j++;
            }
            else if(whatData.equals(AllCategories.SCHEDULE_EXTRAS))
            {
                ExpenseList.add(e);
                j++;
            }
        }
        Log.i("here",ExpenseList.size()+"");
        expenseAdapter.notifyDataSetChanged();
        //upateAlarm();
    }

//    private void upateAlarm() {
//                for(int o=0;o<epocList.size();o++){
//            if(epocList.containsKey(o)){
//                AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                Intent i=new Intent(this,AlarmReciever.class);
//                i.putExtra("TITLE",ExpenseList.get(o).title);
//                PendingIntent pendingIntent=PendingIntent.getBroadcast(this,o,i,0);
//                alarmManager.set(AlarmManager.RTC,epocList.get(i).longValue(),pendingIntent);
//            }
//        }
//    }

}
