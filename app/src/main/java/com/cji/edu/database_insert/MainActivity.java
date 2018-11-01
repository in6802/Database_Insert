package com.cji.edu.database_insert;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyDBOpenHelper dbHelper;
    SQLiteDatabase mdb;
    Button buttonInsertRecord, buttonReadData, buttonUpdateRecord, buttonDeleteRecord;
    TextView textViewResult;
    EditText editTextContry, editTextCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonInsertRecord = findViewById(R.id.buttonInsertRecord);
        buttonInsertRecord.setOnClickListener(this);

        buttonReadData = findViewById(R.id.buttonReadData);
        buttonReadData.setOnClickListener(this);

        buttonUpdateRecord = findViewById(R.id.buttonUpdateRecord);
        buttonUpdateRecord.setOnClickListener(this);

        buttonDeleteRecord = findViewById(R.id.buttonDeleteRecord);
        buttonDeleteRecord.setOnClickListener(this);

        TextView textViewResult = findViewById(R.id.textViewResult);
        EditText editTextContry = findViewById(R.id.editTextCountry);
        EditText editTextCity = findViewById(R.id.editTextCity);

        // java에서 DB열기
        dbHelper = new MyDBOpenHelper(this, "awe.db", null, 1);
        mdb = dbHelper.getWritableDatabase();
    }

    @Override
    public void onClick(View v) {
        int id = 0;
        String country, capital;
        String query = "";

        capital = editTextCity.getText().toString();
        country = editTextContry.getText().toString();

        switch(v.getId()) {
            // insert
            case R.id.buttonInsertRecord:
                query =  "INSERT INTO awe_country VALUES(null, '" + country + "', '" + capital + "');";
                mdb.execSQL(query);

                query = "SELECT * FROM awe_country";
                break;

            // read
            case R.id.buttonReadData:
                query = "SELECT * FROM awe_country";
                break;

            // Update
            case R.id.buttonUpdateRecord:
                //update
                query = "UPDATE awe_country SET capital='"+ capital +"' WHERE country = '"+ country +"'";
                mdb.execSQL(query);

                query = "SELECT * FROM awe_country ORDER BY _id DESC";
                break;

            // delete
            case R.id.buttonDeleteRecord:
                query = "DELETE FROM awe_country WHERE country='"+ country +"'";
                mdb.execSQL(query);

                query = "SELECT * FROM awe_country ORDER BY _id DESC";
                break;

            default:
                textViewResult.setText("다시 선택하세요.");
        }

        editTextContry.setText("");
        editTextCity.setText("");

        // (쿼리, 표준)
        Cursor cursor = mdb.rawQuery(query, null);
        String str = "";
        while (cursor.moveToNext()) {
            //moveToNext()다음으로 넘겨라. 첫번째 레코드 처음점으로 커서 이동
            id = cursor.getInt(0); //getInt(컬럼 순서)
            country = cursor.getString(1);
            capital = cursor.getString(2);
            str += (id + ":" + country + "-" + capital + "\n");
        }
        textViewResult.setText(str);
    }
}