package com.example.myapplication.Listener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.ActivityUi.ManageAccount;
import com.example.myapplication.Entity.ObjectAcount;
import com.example.myapplication.Entity.ObjectStudent;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.AccountModel;
import com.example.myapplication.Model.TableControllerStudent;
import com.example.myapplication.R;

public class OnLongClickAccountRow implements View.OnLongClickListener {
    Context context;
    String id;

    @Override
    public boolean onLongClick(View view) {
        context = view.getContext();
        id = view.getTag().toString();

        final CharSequence[] items = { "Edit" };

        new AlertDialog.Builder(context).setTitle("Account").setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            editRecord(Integer.parseInt(id));
                        }
                        dialog.dismiss();
                    }
                }).show();
        return false;
    }

    public void editRecord(final int account_id) {
        final AccountModel AccountModel = new AccountModel(context);
        final ObjectAcount objectAccount = AccountModel.readSingleRecord(account_id);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.account_input_form, null, false);

        final EditText form_username = (EditText) formElementsView.findViewById(R.id.username);
        final EditText form_password = (EditText) formElementsView.findViewById(R.id.password);
        final RadioGroup select_role = (RadioGroup) formElementsView.findViewById(R.id.select_role);
        String role_name = null;
        if (objectAccount.role == 1){
            select_role.check(R.id.ch_admin);
        } else if (objectAccount.role == 2){
            select_role.check(R.id.ch_manager);
        } else if (objectAccount.role == 3){
            select_role.check(R.id.ch_transporter);
        }

        form_username.setText(objectAccount.username);
        form_password.setText(objectAccount.password);

        new AlertDialog.Builder(context).setView(formElementsView).setTitle("Edit Record").setPositiveButton("Save Changes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                ObjectAcount objectAcount = new ObjectAcount();
                                objectAcount.id = account_id;
                                objectAcount.username = form_username.getText().toString();
                                objectAcount.password = form_password.getText().toString();

                                boolean updateSuccessful = AccountModel.update(objectAccount);

                                if(updateSuccessful){
                                    Toast.makeText(context, "Account record was updated.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to update account record.", Toast.LENGTH_SHORT).show();
                                }

                                ((ManageAccount) context).ListAccount();

                                dialog.cancel();
                            }
                        }).show();
    }
}