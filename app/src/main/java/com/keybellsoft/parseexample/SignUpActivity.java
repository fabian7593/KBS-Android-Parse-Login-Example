package com.keybellsoft.parseexample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.rey.material.widget.Spinner;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import General.StaticsEntities;

/**
 *  Copyright (C) 2015 KeyBellSoft. All rights reserved.
 *  Created by Fabian Rosales  on 25/10/2015.
 *  www.frosquivel.com
 *
 *
 *  Use for example
 *  https://github.com/rey5137/material
 *  www.parse.com
 */
public class SignUpActivity extends AppCompatActivity implements
        View.OnClickListener{

    //the global variables of class, instance components, and others
    private static String tableUserType = StaticsEntities.USERTYPE;
    private EditText editTextViewIdentification;
    private EditText editTextViewFullName;
    private EditText editTextViewEmail;
    private EditText editTextViewPhone;
    private EditText editTextViewUsername;
    private EditText editTextBirthDateSignUp;
    private EditText editTextViewPassword;
    private com.rey.material.widget.Spinner spinnerViewUserType;
    private Button buttonViewSignUp;

    //Principal method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setView();
        chargueUserTypes();
    }

    //set the GUI Components and the respective events
    private void setView() {
        editTextViewIdentification = (EditText) findViewById(R.id.identificationSignUp);
        editTextViewFullName = (EditText) findViewById(R.id.nameSignUp);
        editTextViewEmail = (EditText) findViewById(R.id.emailSignUp);
        editTextViewPhone = (EditText) findViewById(R.id.phone1SignUp);
        editTextViewUsername = (EditText) findViewById(R.id.userSignUp);
        editTextViewPassword = (EditText) findViewById(R.id.passwordSignUp);
        spinnerViewUserType = (com.rey.material.widget.Spinner) findViewById(R.id.userTypeSignUp);
        // spinnerViewUserType.setAdapter(null);
        editTextBirthDateSignUp = (EditText) findViewById(R.id.birthDateSignUp);

        spinnerViewUserType.setOnItemSelectedListener(new com.rey.material.widget.Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(com.rey.material.widget.Spinner adapterView, View view, int i, long id) {
                insertUserType();
            }
        });
        buttonViewSignUp = (Button) findViewById(R.id.buttonSignUp);
        buttonViewSignUp.setOnClickListener(this);


        editTextBirthDateSignUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {

                    Calendar now = Calendar.getInstance();
                    now.getTime();

                    DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                                                            @Override
                                                                            public void onDateSet(DatePickerDialog datePickerDialog, int i, int i1, int i2) {

                                                                                GregorianCalendar calendar = new GregorianCalendar();
                                                                                calendar.set(i, i1, i2);
                                                                                Date selectedDate = calendar.getTime();
                                                                                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                                                                                String finalDate = df.format(selectedDate);
                                                                                editTextBirthDateSignUp.setText(finalDate);

                                                                            }

                                                                        },
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH));

                    dpd.setThemeDark(false);
                    //dpd.setMaxDate(Calendar.getInstance());
                    dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                        }
                    });
                    dpd.show(getFragmentManager(), "Datepickerdialog");
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == buttonViewSignUp) {
            if (requiredFields()) {
                insertUser(insertUserType());
            }
            else{
                Toast.makeText(SignUpActivity.this,R.string.generalRequired, Toast.LENGTH_LONG).show();
            }
        }
    }


    //Verified the required fieldsof the layout
    private Boolean requiredFields() {
        Boolean couldSave = true;
        if (editTextViewUsername.getText().toString().length() == 0) {
            couldSave = false;
            editTextViewUsername.setError(getString(R.string.generalRequiredFields));
        }

        if (editTextViewIdentification.getText().toString().length() == 0) {
            couldSave = false;
            editTextViewIdentification.setError(getString(R.string.generalRequiredFields));
        }

        if (editTextViewFullName.getText().toString().length() == 0) {
            couldSave = false;
            editTextViewFullName.setError(getString(R.string.generalRequiredFields));
        }

        if (editTextViewEmail.getText().toString().length() == 0) {
            couldSave = false;
            editTextViewEmail.setError(getString(R.string.generalRequiredFields));
        }

        if (editTextViewPassword.getText().toString().length() == 0) {
            couldSave = false;
            editTextViewPassword.setError(getString(R.string.generalRequiredFields));
        }

        return couldSave;
    }


    //charge user types in spinner of user types
    private static List<String> itemSpinner = new ArrayList<String>();

    public void chargueUserTypes() {
        ParseQuery<ParseObject> query = new ParseQuery<>(tableUserType);
        query.whereNotEqualTo(StaticsEntities.GENERAL_OBJECTID, "Pm5QAlPCMz");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> userTypeList, ParseException e) {
                com.rey.material.widget.Spinner spinnerUserType = (com.rey.material.widget.Spinner) findViewById(R.id.userTypeSignUp);
                if (e == null) {
                    itemSpinner.add(SignUpActivity.this.getResources().getString(R.string.generalSpinnerDefault));
                    for (int x = 0; x < userTypeList.size(); x++) {
                        ParseObject objectUserType = userTypeList.get(x);
                        Object objUserTypeName = objectUserType.get(StaticsEntities.USERTYPE_USERTYPENAME);
                        if (objUserTypeName != null) {
                            itemSpinner.add((String) objUserTypeName);
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(SignUpActivity.this, R.layout.activity_signup_spinner_detail, itemSpinner);
                    adapter.setDropDownViewResource(R.layout.activity_signup_spinner_detail_dropdown);
                    spinnerUserType.setAdapter(adapter);
                } else {
                    Toast.makeText(SignUpActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
        itemSpinner.clear();
    }


    //get the user type selected to insert in user when the user has saved.
    private static ParseObject objectUserType = null;

    private ParseObject insertUserType() {
        if (objectUserType == null) {
            ParseQuery<ParseObject> query = new ParseQuery<>(tableUserType);
            query.whereEqualTo(StaticsEntities.USERTYPE_USERTYPENAME, spinnerViewUserType.getSelectedItem());
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> userTypeList, ParseException e) {
                    if (e == null) {
                        for (int x = 0; x < userTypeList.size(); x++) {
                            objectUserType = userTypeList.get(x);
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, R.string.generalValueNull, Toast.LENGTH_LONG).show();
                    }
                }
            });
            return objectUserType;
        } else {
            return objectUserType;
        }
    }

    //insert the new user.
    private void insertUser(ParseObject objUserTypeToInsert) {
        try {
            ParseUser userAddObject = new ParseUser();
            userAddObject.setUsername(editTextViewUsername.getText().toString());
            userAddObject.setPassword(editTextViewPassword.getText().toString());
            userAddObject.setEmail(editTextViewEmail.getText().toString());
            userAddObject.put(StaticsEntities.USER_IDENTIFICATION, editTextViewIdentification.getText().toString());
            userAddObject.put(StaticsEntities.USER_FULLNAME, editTextViewFullName.getText().toString());
            userAddObject.put(StaticsEntities.USER_PHONE, editTextViewPhone.getText().toString());

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateInString = editTextBirthDateSignUp.getText().toString();
            if (!dateInString.trim().equals("")) {
                Date date = formatter.parse(dateInString);
                userAddObject.put(StaticsEntities.USER_BIRTHDATE, date);
            }

            userAddObject.put(StaticsEntities.USER_ISACTIVE, false);

            if (objUserTypeToInsert != null) {
                ParseRelation<ParseObject> relation = userAddObject.getRelation(StaticsEntities.USER_USERTYPE);
                relation.add(objUserTypeToInsert);
            }

            userAddObject.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        //save
                        String insertUser = getString(R.string.signUpUserInsert);
                        Toast.makeText(SignUpActivity.this, insertUser.replace("XX", editTextViewUsername.getText().toString()), Toast.LENGTH_LONG).show();


                    } else {
                        //error
                        Toast.makeText(SignUpActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });

            /*new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText(getString(R.string.generalSuccessfull))
                    .setContentText("se inserto el usuario")
                    .show();*/

        } catch (Exception e) {
            Toast.makeText(SignUpActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
