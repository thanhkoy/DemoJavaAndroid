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

public class OnClickAddAccount implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        final Context context = view.getRootView().getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.account_input_form, null, false);

        final EditText form_username = (EditText) formElementsView.findViewById(R.id.username);
        final EditText form_password = (EditText) formElementsView.findViewById(R.id.password);
        final EditText form_re_password = (EditText) formElementsView.findViewById(R.id.re_password);
        final RadioGroup select_role = (RadioGroup) formElementsView.findViewById(R.id.select_role);

        new AlertDialog.Builder(context).setView(formElementsView).setTitle("Create Account").setPositiveButton("Add",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String username = form_username.getText().toString();
                        String password = form_password.getText().toString();
                        String re_password = form_re_password.getText().toString();
                        int selectedRadioButtonID = select_role.getCheckedRadioButtonId();
                        int selectedRadioButtonText = 0;

                        int num = new AccountModel(context).checkUnique(username);
                        if (num == 0){
                            if (password.equals(re_password)){
                                // If nothing is selected from Radio Group, then it return -1
                                if (selectedRadioButtonID != -1) {
                                    RadioButton selectedRadioButton = (RadioButton) formElementsView.findViewById(selectedRadioButtonID);
                                    selectedRadioButtonText = Integer.parseInt(selectedRadioButton.getTag().toString());

                                    ObjectAcount objectAccount = new ObjectAcount();
                                    objectAccount.username = username;
                                    objectAccount.password = password;
                                    objectAccount.role = selectedRadioButtonText;

                                    boolean createSuccessful = new AccountModel(context).create(objectAccount);
                                    if(createSuccessful){
                                        Toast.makeText(context, "Account information was saved.", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(context, "Unable to save account information.", Toast.LENGTH_SHORT).show();
                                    }
                                    ((ManageAccount) context).ListAccount();

                                    dialog.cancel();
                                } else {
                                    Toast.makeText(context, "Unable to save account information. Role is require", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, "Unable to save account information. Repeat password not match", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Unable to save account information. Username exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).show();
    }
}
