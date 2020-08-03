package com.example.myapplication.Listener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Entity.*;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.AccountModel;
import com.example.myapplication.Model.TableControllerStudent;
import com.example.myapplication.R;

public class OnClickLoginButton implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        final Context context = view.getRootView().getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.activity_main, null, false);

        final EditText form_username = formElementsView.findViewById(R.id.username);
        final EditText form_password = formElementsView.findViewById(R.id.password);
        final String username = (form_username).getText().toString();
        final String password = form_password.getText().toString();

        ObjectAcount objectAccount = new ObjectAcount();
        objectAccount.username = username;
        objectAccount.password = password;

        /*boolean auth = new AccountModel(context).checkAth(username, password);
        if(auth == true){
            Toast.makeText(context, "Sign In Fail.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Sign In Successfully.", Toast.LENGTH_SHORT).show();
        }*/

        /*new AlertDialog.Builder(context).setView(formElementsView).setTitle("Create Student").setPositiveButton("Add",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String studentFirstname = editTextStudentFirstname.getText().toString();
                        String studentEmail = editTextStudentEmail.getText().toString();

                        ObjectStudent objectStudent = new ObjectStudent();
                        objectStudent.firstname= studentFirstname;
                        objectStudent.email= studentEmail;

                        boolean createSuccessful = new TableControllerStudent(context).create(objectStudent);
                        if(createSuccessful){
                            Toast.makeText(context, "Student information was saved.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Unable to save student information.", Toast.LENGTH_SHORT).show();
                        }
                        ((MainActivity) context).countRecords();
                        ((MainActivity) context).readRecords();

                        dialog.cancel();
                    }
                }).show();*/
    }
}
