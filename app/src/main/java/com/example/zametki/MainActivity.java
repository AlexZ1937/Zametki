package com.example.zametki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Date;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    LinkedList<NotA> nodes=new LinkedList<NotA>();
    ListView view;
    Intent intent;
    ArrayAdapter<NotA> adapt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent=new Intent(getApplicationContext(),AddNode.class);
        adapt=new ArrayAdapter<NotA>(this, android.R.layout.simple_list_item_1 , nodes);

      view= ((ListView)findViewById(R.id.listNodes));
        view.setAdapter(adapt);

        
        
        view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                try {
                    intent.putExtra("tittle", nodes.get(position).getTittle());
                    intent.putExtra("ID", nodes.get(position).getID_KEY());
                    intent.putExtra("date", nodes.get(position).getDate());
                    intent.putExtra("notetext", nodes.get(position).getNodetext());

                    intent.putExtra("priority", nodes.get(position).getPriority());

                    startActivity(intent);

                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });





    }

    @Override
    protected void onResume() {
        super.onResume();
        try {


            Bundle args = intent.getExtras();
            if (args != null) {
                intent.removeExtra("tittle");
                intent.removeExtra("ID");
                intent.removeExtra("date");
                intent.removeExtra("notetext");
                intent.removeExtra("priority");
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }

        ReadFromDB();


//        NotA noda=new NotA(getIntent().getStringExtra("tittle"),getIntent().getStringExtra("nodetext"),getIntent().getStringExtra("nodetext"));
//        nodes.add(noda);
//        adapt.notifyDataSetChanged();
    }

    public void ReadFromDB()
    {
//
        try {


            nodes.clear();
            SQLiteDatabase db = getBaseContext().openOrCreateDatabase("noteDB", MODE_PRIVATE, null);

//            db.execSQL("DROP TABLE IF EXISTS notes");
            db.execSQL("CREATE TABLE IF NOT EXISTS notes(ID INTEGER PRIMARY KEY AUTOINCREMENT,tittle TEXT,date TEXT,notetext TEXT, priority INTEGER)");
            Cursor cursor = db.rawQuery("SELECT * FROM notes", null);

            while (cursor.moveToNext()) {
                nodes.add(new NotA(cursor.getInt(0),cursor.getString(1),
                        cursor.getString(2), cursor.getString(3),cursor.getInt(4)));

            }
            adapt.notifyDataSetChanged();

            cursor.close();
            db.close();
            
            SetPriority();
            
            
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


public  void SetPriority()
{
    for(int k=0;k<nodes.size();k++) {
        view.getChildAt(k).setBackground(
                (nodes.get(k).getPriority()==1)?getDrawable(R.drawable.lowbackground):(nodes.get(k).getPriority()==2)?getDrawable(R.drawable.middlebackground):getDrawable(R.drawable.extrabackground));
    }
}

    public void AddNodesToList(View v)
    {
        try {
        startActivity(intent);
    }
        catch (Exception ex)
    {
        Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
    }
//        getIntent().getStringExtra("date");

    }
}