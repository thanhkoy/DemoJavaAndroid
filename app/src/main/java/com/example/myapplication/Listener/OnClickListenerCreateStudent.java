package com.example.myapplication.Listener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Entity.ObjectStudent;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.TableControllerStudent;
import com.example.myapplication.R;

public class OnClickListenerCreateStudent implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        final Context context = view.getRootView().getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.student_input_form, null, false);

        final EditText editTextStudentFirstname = (EditText) formElementsView.findViewById(R.id.editTextStudentFirstname);
        final EditText editTextStudentEmail = (EditText) formElementsView.findViewById(R.id.editTextStudentEmail);

        new AlertDialog.Builder(context).setView(formElementsView).setTitle("Create Student").setPositiveButton("Add",
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
                                /*((MainActivity) context).countRecords();
                                ((MainActivity) context).readRecords();*/

                                dialog.cancel();
                            }
                        }).show();
    }
}