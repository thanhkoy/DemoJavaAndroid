package com.example.myapplication.Listener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ActivityUi.ManagePacket;
import com.example.myapplication.ActivityUi.ProcessPacket;
import com.example.myapplication.Entity.ObjectPacket;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.PacketModel;
import com.example.myapplication.R;

public class OnClickProcessPacketRow implements View.OnClickListener {
    Context context;
    String id;

    @Override
    public void onClick(View view) {
        context = view.getContext();
        id = view.getTag().toString();

        processRecord(Integer.parseInt(id));
    }

    public void processRecord(final int packet_id) {
        final PacketModel PacketModel = new PacketModel(context);
        final ObjectPacket objectPacket = PacketModel.readSingleRecord(packet_id);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.packet_process_form, null, false);

        final TextView form_unit = (TextView) formElementsView.findViewById(R.id.unit);
        final TextView form_from = (TextView) formElementsView.findViewById(R.id.from);
        final TextView form_to = (TextView) formElementsView.findViewById(R.id.to);
        final TextView form_cost = (TextView) formElementsView.findViewById(R.id.cost);
        final TextView form_description = (TextView) formElementsView.findViewById(R.id.description);
        final RadioGroup select_status = (RadioGroup) formElementsView.findViewById(R.id.process_status);

        form_unit.setText(objectPacket.unit);
        form_from.setText(objectPacket.from_address);
        form_to.setText(objectPacket.to_address);
        form_cost.setText(Integer.toString(objectPacket.cost) + "VND");
        form_description.setText(objectPacket.description);

        new AlertDialog.Builder(context).setView(formElementsView).setTitle("Process Packet").setPositiveButton("Save Changes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                ObjectPacket objectPacket = new ObjectPacket();
                                objectPacket.id = packet_id;
                                int selectedRadioButtonID = select_status.getCheckedRadioButtonId();
                                int selectedRadioButtonText = 1;

                                // If nothing is selected from Radio Group, then it return -1
                                if (selectedRadioButtonID != -1) {
                                    RadioButton selectedRadioButton = (RadioButton) formElementsView.findViewById(selectedRadioButtonID);
                                    selectedRadioButtonText = Integer.parseInt(selectedRadioButton.getTag().toString());
                                }
                                objectPacket.status = selectedRadioButtonText;

                                boolean updateSuccessful = PacketModel.processPacket(objectPacket);

                                if(updateSuccessful){
                                    Toast.makeText(context, "Packet record was processed.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, ProcessPacket.class);
                                    context.startActivity(intent);
                                }else{
                                    Toast.makeText(context, "Unable to process packet record.", Toast.LENGTH_SHORT).show();
                                }

                                dialog.cancel();
                            }
                        }).show();
    }
}