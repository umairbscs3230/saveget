package com.example.umairbscs.saveget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.umairbscs.saveget.Model.Computer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText edtComputerName;
    EditText edtComputerPower;
    EditText edtComputerSpeed;
    EditText edtComputerRam;
    EditText edtComputerScreen;
    EditText edtComputerKeyboard;
    EditText edtComputerCPU;
    Button btnSendAndUpdateData;
    TextView txtGetDataFromServer;
    Button btnGetDataFromServer;

    private FirebaseDatabase firebaseDatabaseInstance;
    private DatabaseReference databaseReference;

    private String computerUniqueID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtComputerName = (EditText) findViewById(R.id.edtComputerName);
        edtComputerPower = (EditText) findViewById(R.id.edtComputerPower);
        edtComputerSpeed = (EditText) findViewById(R.id.edtComputerspeed);
        edtComputerRam = (EditText) findViewById(R.id.edtComputerRam);
        edtComputerScreen = (EditText) findViewById(R.id.edtComputerScreen);
        edtComputerKeyboard = (EditText) findViewById(R.id.edtComputerkeyboard);
        edtComputerCPU = (EditText) findViewById(R.id.edtComputerCpu);
        btnSendAndUpdateData = (Button) findViewById(R.id.btnsendorupdate);
        txtGetDataFromServer = (TextView) findViewById(R.id.txtcomputersdata);
        btnGetDataFromServer = (Button) findViewById(R.id.btnGetDataFromServer);

        firebaseDatabaseInstance = FirebaseDatabase.getInstance();

        firebaseDatabaseInstance.getReference("Great App!");

        databaseReference = firebaseDatabaseInstance.getReference("Computers");

        firebaseDatabaseInstance.getReference("APPLICATION_NAME").setValue("Computers Data");


        firebaseDatabaseInstance.getReference("APPLICATION_NAME").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String applicationName = dataSnapshot.getValue(String.class);

                getSupportActionBar().setTitle(applicationName);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnSendAndUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String computerName = edtComputerName.getText().toString();
                String computerPower = edtComputerPower.getText().toString();
                String computerSpeed = edtComputerSpeed.getText().toString();
                String computerRam = edtComputerRam.getText().toString();
                String computerScreen = edtComputerScreen.getText().toString();
                String computerKeyboard = edtComputerKeyboard.getText().toString();
                String computerCPU = edtComputerCPU.getText().toString();

                int computerPowerIntegerValue = 0;
                int computerSpeedIntegerValue = 0;
                int computerRamIntegerValue = 0;

                try {

                    computerPowerIntegerValue = Integer.parseInt(computerPower);

                } catch (Exception e) {

                    Log.i("TAG", e.getMessage());

                }

                try {
                    computerSpeedIntegerValue = Integer.parseInt(computerSpeed);
                } catch (Exception e) {

                }

                try {
                    computerRamIntegerValue = Integer.parseInt(computerRam);
                } catch (Exception e) {

                }

                if (TextUtils.isEmpty(computerUniqueID)) {

                    produceANewComputerAndPutItInTheDatabase(computerName,
                            computerPowerIntegerValue,
                            computerSpeedIntegerValue,
                            computerRamIntegerValue,
                            computerScreen,
                            computerKeyboard,
                            computerCPU);

                    edtComputerName.setText("");
                    edtComputerPower.setText("");
                    edtComputerSpeed.setText("");
                    edtComputerRam.setText("");
                    edtComputerScreen.setText("");
                    edtComputerKeyboard.setText("");
                    edtComputerCPU.setText("");


                } else {

                    modifyTheProducedComputer(computerName,
                            computerPower,
                            computerSpeed,
                            computerRam,
                            computerScreen,
                            computerKeyboard,
                            computerCPU);
                }



            }
        });

        changeTheTextOfSendOrUpdateButtonAccordingToTheSituation();


        btnGetDataFromServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (computerUniqueID == null) {
                    return;
                }

                computerDataChangedListener();
            }
        });


    }

    private void changeTheTextOfSendOrUpdateButtonAccordingToTheSituation() {

        if (TextUtils.isEmpty(computerUniqueID)) {
            btnSendAndUpdateData.setText("Produce a new Computer and send it to server");
        } else {

            btnSendAndUpdateData.setText("Modify The Produced Computer Data");

        }


    }

    private void produceANewComputerAndPutItInTheDatabase(String computerName,
                                                          int computerPower,
                                                          int computerSpeed,
                                                          int computerRam,
                                                          String computerScreen,
                                                          String computerKeyboard,
                                                          String computerCPU ) {

        if (TextUtils.isEmpty(computerUniqueID)) {

            computerUniqueID = databaseReference.push().getKey();

        }

        Computer computer = new Computer(computerName, computerPower, computerSpeed,
                computerRam, computerScreen, computerKeyboard, computerCPU);
        databaseReference.child(computerUniqueID).setValue(computer);

        changeTheTextOfSendOrUpdateButtonAccordingToTheSituation();


    }


    private void modifyTheProducedComputer(String computerName, String computerPower,
                                           String computerSpeed, String computerRam,
                                           String computerScreen, String computerKeyboard,
                                           String computerCPU) {

        if (!TextUtils.isEmpty(computerName)) {
            databaseReference.child(computerUniqueID).child("computerName")
                    .setValue(computerName);
        }
        if (!TextUtils.isEmpty(computerPower)) {

            try {
                int computerPowerIntValue =  Integer.parseInt(computerPower);

                databaseReference.child(computerUniqueID).child("computerPower")
                        .setValue(computerPowerIntValue);

            } catch (Exception i) {

            }

        }

        if (!TextUtils.isEmpty(computerSpeed)) {

            try {
                int computerSpeedIntValue =  Integer.parseInt(computerSpeed);

                databaseReference.child(computerUniqueID).child("computerSpeed")
                        .setValue(computerSpeedIntValue);

            } catch (Exception i) {

            }
        }
        if (!TextUtils.isEmpty((computerRam))) {
            try {
                int computerRamIntValue =  Integer.parseInt(computerRam);

                databaseReference.child(computerUniqueID).child("computerRam")
                        .setValue(computerRamIntValue);

            } catch (Exception i) {

            }
        }
        if (!TextUtils.isEmpty(computerScreen)) {

            databaseReference.child(computerUniqueID).child("computerScreen")
                    .setValue(computerScreen);
        }
        if (!TextUtils.isEmpty(computerKeyboard)) {
            databaseReference.child(computerUniqueID).child("computerKeyboard")
                    .setValue(computerKeyboard);
        }
        if (!TextUtils.isEmpty(computerCPU)) {

            databaseReference.child(computerUniqueID).child("computerCPU")
                    .setValue(computerCPU);

        }
    }


    private void computerDataChangedListener() {

        databaseReference.child(computerUniqueID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Computer computer = dataSnapshot.getValue(Computer.class);

                if (computer == null) {

                    return;

                }

                txtGetDataFromServer.setText("Computer Name: " + computer.getComputerName()
                        + " - " + "Computer Power: " + computer.getComputerPower() + " - "
                        + "Computer Speed: " + computer.getComputerSpeed() + " - "
                        + "Computer Ram: " + computer.getComputerRam() + " - "
                        + "Computer Screen: "
                        + computer.getComputerScreen() + " - " + "Computer Keyboard: " +
                        computer.getComputerKeyboard() + " - " + "Computer CPU: "
                        + computer.getComputerCpu());



                changeTheTextOfSendOrUpdateButtonAccordingToTheSituation();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


}