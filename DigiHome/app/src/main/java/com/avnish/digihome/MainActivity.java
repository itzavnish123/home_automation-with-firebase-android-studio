package com.avnish.digihome;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    TextView deviceStatus;
    TextView onOff_light;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switch_light;
    TextView onOff_tv;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switch_tv;
    TextView onOff_ac;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switch_ac;
    TextView onOff_fan;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switch_fan;
    TextView fanController;


    int fanInt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            deviceStatus = findViewById(R.id.device_status);
            onOff_light = findViewById(R.id.on_off_light);
            switch_light = findViewById(R.id.switch_light);
            onOff_tv = findViewById(R.id.on_off_tv);
            switch_tv = findViewById(R.id.switch_tv);
            onOff_ac = findViewById(R.id.on_off_ac);
            switch_ac = findViewById(R.id.switch_ac);
            onOff_fan = findViewById(R.id.on_off_fan);
            switch_fan = findViewById(R.id.switch_fan);
            fanController = findViewById(R.id.fan_controller);

            manageUi();
            buttonClicked();

            return insets;
        });
    }

    @SuppressLint("SetTextI18n")
    public void manageUi(){

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String isStatus = Objects.requireNonNull(dataSnapshot.child("IsStatus").getValue()).toString();
                String isLight = Objects.requireNonNull(dataSnapshot.child("Light").getValue()).toString();
                String isTv = Objects.requireNonNull(dataSnapshot.child("Tv").getValue()).toString();
                String isAc = Objects.requireNonNull(dataSnapshot.child("Ac").getValue()).toString();
                String isFan = Objects.requireNonNull(dataSnapshot.child("Fan").getValue()).toString();
                if(Integer.parseInt(isStatus) == 1){
                    deviceStatus.setText("Online");
                }else {
                    deviceStatus.setText("Offline");
                }
                if(Integer.parseInt(isLight) == 1){
                    switch_light.setChecked(true);
                    onOff_light.setText("On");
                }else {
                    switch_light.setChecked(false);
                    onOff_light.setText("Off");
                }
                if(Integer.parseInt(isTv) == 1){
                    switch_tv.setChecked(true);
                    onOff_tv.setText("On");
                }else {
                    switch_tv.setChecked(false);
                    onOff_tv.setText("Off");
                }
                if(Integer.parseInt(isAc) == 1){
                    switch_ac.setChecked(true);
                    onOff_ac.setText("On");
                }else {
                    switch_ac.setChecked(false);
                    onOff_ac.setText("Off");
                }
                if(Integer.parseInt(isFan) == 0){
                    switch_fan.setChecked(false);
                    onOff_fan.setText("Off");
                }else {
                    switch_fan.setChecked(true);
                    onOff_fan.setText("On");
                }
                fanInt = Integer.parseInt(isFan)/42;
                fanController.setText(Integer.toString(fanInt));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        
    }
    public void buttonClicked(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        switch_light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            final DatabaseReference setLight = database.getReference("Light");
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    setLight.setValue(1);
                    onOff_light.setText("On");
                }else {
                    setLight.setValue(0);
                    onOff_light.setText("Off");
                }
            }
        });
        switch_tv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            final DatabaseReference setTv = database.getReference("Tv");
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    setTv.setValue(1);
                    onOff_tv.setText("On");
                }else {
                    setTv.setValue(0);
                    onOff_tv.setText("Off");
                }
            }
        });
        switch_ac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            final DatabaseReference setAc = database.getReference("Ac");
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    setAc.setValue(1);
                    onOff_ac.setText("On");
                }else {
                    setAc.setValue(0);
                    onOff_ac.setText("Off");
                }
            }
        });
        switch_fan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            final DatabaseReference setFan = database.getReference("Fan");
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    setFan.setValue(252);
                    onOff_fan.setText("On");
                }else {
                    setFan.setValue(0);
                    onOff_fan.setText("Off");
                }
            }
        });
        fanController.setOnClickListener(new View.OnClickListener() {
            final DatabaseReference setFan = database.getReference("Fan");


            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (fanInt != 0){
                    if (fanInt == 6){
                        fanInt = 1;
                    }else {
                        fanInt = fanInt + 1;
                    }
                    setFan.setValue(fanInt*42);
                }
            }
        });



    }
}