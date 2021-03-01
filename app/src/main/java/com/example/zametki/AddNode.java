package com.example.zametki;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.LinkedList;

public class AddNode extends AppCompatActivity {

    Calendar dateAndTime=Calendar.getInstance();
    Spinner spinner;
    Button currentDateTime;
    boolean isChange=false;
    int IDChange=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnode);
        currentDateTime=(Button)(findViewById(R.id.button2));


    spinner = (Spinner) findViewById(R.id.spinner);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new LinkedList<String>() {
        {
            add("Low");
            add("Midle");
            add("Extra");
        }
    });

    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);





        Bundle args = getIntent().getExtras();
        if(args != null)
        {
            IDChange= (int) args.get("ID");
            isChange=true;

            ((TextView)findViewById(R.id.editTextTextPersonName2)).setText(args.get("tittle").toString());
                ((TextView)findViewById(R.id.button2)).setText((args.get("date").toString()));
                ((TextView)findViewById(R.id.editTextTextPersonName3)).setText(args.get("notetext").toString());


                    spinner.setSelection((int)args.get("priority")-1);

        }
    }



    public void SaveToDBNote(String tittle, String date, String notetext, String priority)
    {
        try {


            SQLiteDatabase datab = getBaseContext().openOrCreateDatabase("noteDB", MODE_PRIVATE, null);
            datab.execSQL("CREATE TABLE IF NOT EXISTS notes(ID INTEGER PRIMARY KEY AUTOINCREMENT,tittle TEXT,date TEXT,notetext TEXT, priority INTEGER)");
            ContentValues values = new ContentValues();
            values.put("tittle",tittle);
            values.put("date",date);
            values.put("notetext",notetext);
            values.put("priority",(priority.equals("Low") ? 1 : (priority.equals("Midle") ? 2 : 3)));

            if(isChange)
            {
                datab.update("notes",values,"ID = "+IDChange,null);
            }
            else {
                datab.insert("notes", null, values);
//                datab.execSQL("INSERT INTO notes Values('" + tittle + "','" + date + "','" + notetext + "')");
            }
            datab.close();
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void SaveClick(View v)
    {
        if(((TextView)findViewById(R.id.editTextTextPersonName3)).getText().length()>0 && ((TextView)findViewById(R.id.editTextTextPersonName2)).getText().length()>0) {
//            getIntent().putExtra("tittle", ((TextView)findViewById(R.id.editTextTextPersonName2)).getText());
//            getIntent().putExtra("date", ((TextView)findViewById(R.id.button2)).getText());
//            getIntent().putExtra("nodetext", ((TextView)findViewById(R.id.editTextTextPersonName3)).getText());
            try {


                SaveToDBNote(((TextView) findViewById(R.id.editTextTextPersonName2)).getText().toString(),
                        ((TextView) findViewById(R.id.button2)).getText().toString(),
                        ((TextView) findViewById(R.id.editTextTextPersonName3)).getText().toString(), spinner.getSelectedItem().toString());

            }
            catch (Exception ex)
            {
                Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
            }
            this.finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Add any text to node!", Toast.LENGTH_SHORT).show();
        }
    }

    public void ClearClick(View v)
    {
        ((TextView)findViewById(R.id.editTextTextPersonName3)).setText("");
    }

    public void CancelClick(View v)
    {
        this.finish();
    }




    DatePickerDialog.OnDateSetListener dt = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, month);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    public void setDate(View v){
        new DatePickerDialog(AddNode.this,
                dt,
                dateAndTime.get(Calendar.YEAR),

                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH)
        ).show();
    }


    private void setInitialDateTime(){
        currentDateTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE  | DateUtils.FORMAT_SHOW_YEAR));
    }

}