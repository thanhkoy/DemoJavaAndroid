package com.example.myapplication.Listener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.ActivityUi.ManageAccount;
import com.example.myapplication.ActivityUi.ManagePacket;
import com.example.myapplication.Entity.ObjectAcount;
import com.example.myapplication.Entity.ObjectPacket;
import com.example.myapplication.Model.PacketModel;
import com.example.myapplication.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class OnClickAddPacket implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        final Context context = view.getRootView().getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.packet_input_form, null, false);

        final EditText form_unit = (EditText) formElementsView.findViewById(R.id.unit);
        final EditText form_from = (EditText) formElementsView.findViewById(R.id.from);
        final EditText form_to = (EditText) formElementsView.findViewById(R.id.to);
        final EditText form_cost = (EditText) formElementsView.findViewById(R.id.cost);
        final EditText form_description = (EditText) formElementsView.findViewById(R.id.description);

        final Spinner dropdown = (Spinner) formElementsView.findViewById(R.id.transporter);
        final List<ObjectAcount> transporter = new PacketModel(context).ListTransporter();
        ArrayList<String> items = new ArrayList<String>();
        if (transporter.size() > 0) {
            for (ObjectAcount obj : transporter) {
                int id = obj.id;
                String username = obj.username;
                items.add(username);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        new AlertDialog.Builder(context).setView(formElementsView).setTitle("Create Packet").setPositiveButton("Add",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String unit = form_unit.getText().toString();
                        String from = form_from.getText().toString();
                        String transporter = dropdown.getSelectedItem().toString();
                        String to = form_to.getText().toString();
                        int cost = Integer.parseInt(form_cost.getText().toString());
                        String description = form_description.getText().toString();

                        int transporter_id = new PacketModel(context).GetTransporterId(transporter);

                        ObjectPacket objectPacket = new ObjectPacket();
                        objectPacket.code = "";
                        objectPacket.unit = unit;
                        objectPacket.transporter = transporter_id;
                        objectPacket.from_address = from;
                        objectPacket.to_address = to;
                        objectPacket.cost = cost;
                        objectPacket.description = description;
                        objectPacket.status = 1;

                        boolean createSuccessful = new PacketModel(context).create(objectPacket);
                        if(createSuccessful){
                            Toast.makeText(context, "Account information was saved.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Unable to save packet information.", Toast.LENGTH_SHORT).show();
                        }
                        ((ManagePacket) context).ListPacket();

                        dialog.cancel();
                    }
                }).show();
    }
}
