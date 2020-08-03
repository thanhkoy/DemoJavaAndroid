package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ActivityUi.ManageAccount;
import com.example.myapplication.ActivityUi.ManagePacket;
import com.example.myapplication.ActivityUi.ProcessPacket;
import com.example.myapplication.Entity.ObjectAcount;
import com.example.myapplication.Entity.ObjectStudent;
import com.example.myapplication.Listener.OnClickListenerCreateStudent;
import com.example.myapplication.Listener.OnClickLoginButton;
import com.example.myapplication.Listener.OnLongClickListenerStudentRecord;
import com.example.myapplication.Model.AccountModel;
import com.example.myapplication.Model.TableControllerStudent;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    /*Khi gọi đến màn hình main sẽ chạy đếm bản ghi và list các bản ghi*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Khai bao nut tao moi va lang nghe su kien click*/
        /*Button buttonCreateStudent = (Button) findViewById(R.id.buttonCreateStudent);*/

        final EditText form_username = findViewById(R.id.username);
        final EditText form_password = findViewById(R.id.password);
        Button btn_login = (Button) findViewById(R.id.btn_login);

        /*buttonCreateStudent.setOnClickListener(new OnClickListenerCreateStudent());*/
        btn_login.setOnClickListener(new OnClickLoginButton() {
            public void onClick(View v) {
                String username = form_username.getText().toString();
                String password = form_password.getText().toString();
                checkAuth(v, username, password);
            }
        });

        /*countRecords();
        readRecords();*/
    }

    /*public void countRecords() {
        int recordCount = new TableControllerStudent(this).count();
        TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
        textViewRecordCount.setText(recordCount + " records found.");
    }*/

    public void checkAuth(View view, String username, String password) {
        final Context context = view.getRootView().getContext();
        final ObjectAcount objectAccount = new AccountModel(context).checkAth(username, password);
        if (objectAccount.role > 0) {
            SharedPreferences preferences = getSharedPreferences("com.example.myapplication", Context.MODE_PRIVATE);
            preferences.edit().putString("session", String.valueOf(objectAccount.id)).commit();
        }
        if(objectAccount.role == 0){
            Toast.makeText(context, "Sign In Fail.", Toast.LENGTH_SHORT).show();
        } else if(objectAccount.role == 1) {
            Toast.makeText(context, "Sign In Successfully.", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Redirecting...",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this, ManageAccount.class);
            startActivity(intent);
        } else if (objectAccount.role == 2){
            Toast.makeText(context, "Sign In Successfully.", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Redirecting...",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this, ManagePacket.class);
            startActivity(intent);
        } else if (objectAccount.role == 3){
            Toast.makeText(context, "Sign In Successfully.", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Redirecting...",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this, ProcessPacket.class);
            startActivity(intent);
        }
    }

    /*public void readRecords() {

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();

        List<ObjectStudent> students = new TableControllerStudent(this).read();

        if (students.size() > 0) {

            for (ObjectStudent obj : students) {

                int id = obj.id;
                String studentFirstname = obj.firstname;
                String studentEmail = obj.email;

                String textViewContents = studentFirstname + " - " + studentEmail;

                TextView textViewStudentItem= new TextView(this);
                textViewStudentItem.setPadding(0, 10, 0, 10);
                textViewStudentItem.setText(textViewContents);
                textViewStudentItem.setTag(Integer.toString(id));

                textViewStudentItem.setOnLongClickListener(new OnLongClickListenerStudentRecord());

                linearLayoutRecords.addView(textViewStudentItem);
            }
        } else {

            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");

            linearLayoutRecords.addView(locationItem);
        }
    }*/
}