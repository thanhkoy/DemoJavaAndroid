package com.example.myapplication.Listener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.ActivityUi.ManageAccount;
import com.example.myapplication.ActivityUi.ManagePacket;
import com.example.myapplication.Entity.ObjectAcount;
import com.example.myapplication.Entity.ObjectPacket;
import com.example.myapplication.Model.AccountModel;
import com.example.myapplication.Model.PacketModel;
import com.example.myapplication.R;

public class OnLongClickPacketRow implements View.OnLongClickListener {
    Context context;
    String id;

    @Override
    public boolean onLongClick(View view) {
        context = view.getContext();
        id = view.getTag().toString();

        final CharSequence[] items = { "Edit" };

        new AlertDialog.Builder(context).setTitle("Packet").setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            editRecord(Integer.parseInt(id));
                        }
                        dialog.dismiss();
                    }
                }).show();
        return false;
    }

    public void editRecord(final int packet_id) {
        final PacketModel PacketModel = new PacketModel(context);
        final ObjectPacket objectPacket = PacketModel.readSingleRecord(packet_id);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.packet_input_form, null, false);

        final EditText form_unit = (EditText) formElementsView.findViewById(R.id.unit);
        final EditText form_from = (EditText) formElementsView.findViewById(R.id.from);
        final EditText form_to = (EditText) formElementsView.findViewById(R.id.to);
        final EditText form_cost = (EditText) formElementsView.findViewById(R.id.cost);
        final EditText form_description = (EditText) formElementsView.findViewById(R.id.description);
        final Spinner dropdown = (Spinner) formElementsView.findViewById(R.id.transporter);
        dropdown.setVisibility(View.GONE);

        form_unit.setText(objectPacket.unit);
        form_from.setText(objectPacket.from_address);
        form_to.setText(objectPacket.to_address);
        form_cost.setText(Integer.toString(objectPacket.cost));
        form_description.setText(objectPacket.description);

        new AlertDialog.Builder(context).setView(formElementsView).setTitle("Edit Record").setPositiveButton("Save Changes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                ObjectPacket objectPacket = new ObjectPacket();
                                objectPacket.id = packet_id;
                                objectPacket.code = form_unit.getText().toString() + packet_id;
                                objectPacket.unit = form_unit.getText().toString();
                                objectPacket.from_address = form_from.getText().toString();
                                objectPacket.to_address = form_to.getText().toString();
                                objectPacket.cost = Integer.parseInt(form_cost.getText().toString());
                                objectPacket.description = form_description.getText().toString();

                                boolean updateSuccessful = PacketModel.update(objectPacket);

                                if(updateSuccessful){
                                    Toast.makeText(context, "Packet record was updated.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to update packet record.", Toast.LENGTH_SHORT).show();
                                }

                                ((ManagePacket) context).ListPacket();

                                dialog.cancel();
                            }
                        }).show();
    }
}