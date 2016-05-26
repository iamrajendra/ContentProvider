package com.rajendra.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText studentNameET;
    private EditText gradeET;
    private Button addStudentBtn, reteriveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        studentNameET = (EditText)findViewById(R.id.studentET);
        gradeET = (EditText)findViewById(R.id.gradeET);
        addStudentBtn = (Button)findViewById(R.id.addStudent);
        reteriveButton=(Button)findViewById(R.id.reteriveStudent);
        addStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              addSudent(studentNameET.getText().toString(),gradeET.getText().toString());
            }
        });
        reteriveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Retrieve student records
                String URL = "content://com.example.provider.College/students";

                Uri students = Uri.parse(URL);
                Cursor c = managedQuery(students, null, null, null, "name");

                if (c.moveToFirst()) {
                    do{
                        Toast.makeText(MainActivity.this,
                                c.getString(c.getColumnIndex(StudentProvider._ID)) +
                                        ", " +  c.getString(c.getColumnIndex( StudentProvider.NAME)) +
                                        ", " + c.getString(c.getColumnIndex( StudentProvider.GRADE)),
                                Toast.LENGTH_SHORT).show();
                    } while (c.moveToNext());
                }
            }

        });
    }

    private void addSudent(String studentName, String studentGrade) {
        ContentValues values = new ContentValues();

        values.put(StudentProvider.NAME,
                studentName);

        values.put(StudentProvider.GRADE,
                studentGrade);

        Uri uri = getContentResolver().insert(
                StudentProvider.CONTENT_URI, values);

        Toast.makeText(getBaseContext(),
                uri.toString(), Toast.LENGTH_LONG).show();
    }
}
