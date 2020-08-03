package com.example.myapplication.ActivityUi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Entity.ObjectAcount;
import com.example.myapplication.Entity.ObjectStudent;
import com.example.myapplication.Listener.OnClickAddAccount;
import com.example.myapplication.Listener.OnClickListenerCreateStudent;
import com.example.myapplication.Listener.OnLongClickAccountRow;
import com.example.myapplication.Listener.OnLongClickListenerStudentRecord;
import com.example.myapplication.Model.AccountModel;
import com.example.myapplication.Model.TableControllerStudent;
import com.example.myapplication.R;

import java.lang.reflect.Array;
import java.util.List;

public class ManageAccount extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_account);

        /*Khai bao nut tao moi va lang nghe su kien click*/
        Button btn_add_account = (Button) findViewById(R.id.btn_add_account);
        btn_add_account.setOnClickListener(new OnClickAddAccount());

        ListAccount();
    }

    public void ListAccount() {

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();

        List<ObjectAcount> account = new AccountModel(this).read();

        if (account.size() > 0) {
            TableLayout TableAccount = new TableLayout(this);

            TableRow TableAccountItem = new TableRow(this);
            TextView col1 = new TextView(this);
            TextView col2 = new TextView(this);
            col1.setText("Username");
            col2.setText("Role");
            col1.setPadding(30, 0, 70, 0);
            col2.setPadding(30, 0, 0, 0);
            col1.setTextSize(30);
            col2.setTextSize(30);
            TableAccountItem.addView(col1);
            TableAccountItem.addView(col2);

            TableAccountItem.setPadding(0, 0, 0, 10);

            TableAccount.addView(TableAccountItem);

            for (ObjectAcount obj : account) {
                int id = obj.id;
                String username = obj.username;
                int role = obj.role;
                String role_name = "";
                if (role == 1){
                    role_name = "Admin";
                } else if (role == 2){
                    role_name = "Manager";
                } else if (role == 3){
                    role_name = "Transporter";
                }

                TableRow RowItem = new TableRow(this);
                TextView col_username = new TextView(this);
                TextView col_role = new TextView(this);
                col_username.setText(String.valueOf(username));
                col_role.setText(String.valueOf(role_name));
                col_username.setPadding(30, 0, 70, 0);
                col_role.setPadding(30, 0, 0, 0);
                col_username.setTextSize(25);
                col_role.setTextSize(25);
                RowItem.addView(col_username);
                RowItem.addView(col_role);

                RowItem.setPadding(0, 0, 0, 10);
                TableAccount.addView(RowItem);
                RowItem.setTag(Integer.toString(id));
                RowItem.setOnLongClickListener(new OnLongClickAccountRow());
            }
            linearLayoutRecords.addView(TableAccount);
        } else {
            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");

            linearLayoutRecords.addView(locationItem);
        }
    }
}
