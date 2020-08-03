package com.example.myapplication.ActivityUi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Entity.ObjectAcount;
import com.example.myapplication.Entity.ObjectPacket;
import com.example.myapplication.Listener.OnClickAddAccount;
import com.example.myapplication.Listener.OnClickAddPacket;
import com.example.myapplication.Listener.OnClickListenerCreateStudent;
import com.example.myapplication.Listener.OnClickLoginButton;
import com.example.myapplication.Listener.OnLongClickAccountRow;
import com.example.myapplication.Listener.OnLongClickPacketRow;
import com.example.myapplication.Model.AccountModel;
import com.example.myapplication.Model.PacketModel;
import com.example.myapplication.R;

import java.util.List;

public class ManagePacket extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_packet);

        /*Khai bao nut tao moi va lang nghe su kien click*/
        Button btn_add_packet = (Button) findViewById(R.id.btn_add_packet);
        btn_add_packet.setOnClickListener(new OnClickAddPacket());

        ListPacket();
    }

    public void ListPacket() {

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();

        List<ObjectPacket> packet = new PacketModel(this).read();

        if (packet.size() > 0) {
            TableLayout TablePacket = new TableLayout(this);

            TableRow TablePacketItem = new TableRow(this);
            TextView col1 = new TextView(this);
            TextView col2 = new TextView(this);
            TextView col3 = new TextView(this);
            TextView col4 = new TextView(this);
            col1.setText("Code");
            col2.setText("Cost");
            col3.setText("Status");
            col4.setText("Transporter");
            col1.setPadding(30, 0, 50, 0);
            col2.setPadding(0, 0, 0, 0);
            col3.setPadding(50, 0, 50, 0);
            col4.setPadding(0, 0, 30, 0);
            col1.setTextSize(25);
            col2.setTextSize(25);
            col3.setTextSize(25);
            col4.setTextSize(25);
            TablePacketItem.addView(col1);
            TablePacketItem.addView(col2);
            TablePacketItem.addView(col3);
            TablePacketItem.addView(col4);

            TablePacketItem.setPadding(0, 0, 0, 10);

            TablePacket.addView(TablePacketItem);

            for (ObjectPacket obj : packet) {
                int id = obj.id;
                String code = obj.code;
                int cost = obj.cost;
                int status = obj.status;
                String transporter = obj.transporter_name;
                String status_name = "";
                if (status == 1){
                    status_name = "Created";
                } else if (status == 2){
                    status_name = "Finished";
                } else if (status == 3){
                    status_name = "Canceled";
                }

                TableRow PacketRow = new TableRow(this);

                TextView col_code = new TextView(this);
                TextView col_cost = new TextView(this);
                TextView col_status = new TextView(this);
                TextView col_transporter = new TextView(this);

                col_code.setText(String.valueOf(code));
                col_cost.setText(String.valueOf(cost) + "VND");
                col_status.setText(String.valueOf(status_name));
                col_transporter.setText(String.valueOf(transporter));
                col_code.setPadding(30, 0, 50, 0);
                col_cost.setPadding(0, 0, 0, 0);
                col_status.setPadding(50, 0, 50, 0);
                col_transporter.setPadding(0, 0, 30, 0);
                col_code.setTextSize(20);
                col_cost.setTextSize(20);
                col_status.setTextSize(20);
                col_transporter.setTextSize(20);
                PacketRow.removeAllViews();
                PacketRow.addView(col_code);
                PacketRow.addView(col_cost);
                PacketRow.addView(col_status);
                PacketRow.addView(col_transporter);

                PacketRow.setPadding(0, 0, 0, 10);

                /*TablePacket.removeAllViews();*/
                TablePacket.addView(PacketRow);
                PacketRow.setTag(Integer.toString(id));
                PacketRow.setOnLongClickListener(new OnLongClickPacketRow());
            }
            linearLayoutRecords.addView(TablePacket);
        } else {
            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");

            linearLayoutRecords.addView(locationItem);
        }
    }
}
