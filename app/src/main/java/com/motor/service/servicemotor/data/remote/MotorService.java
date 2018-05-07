package com.motor.service.servicemotor.data.remote;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.motor.service.servicemotor.data.model.Motor;

/**
 * Created by ikun on 17/01/18.
 */

public class MotorService {
    DatabaseReference databaseRef;

    public MotorService(){
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getMotors(){
        return databaseRef.child("motors");
    }

    public DatabaseReference getDetailsMotor (String id){
        return databaseRef.child("motors").child(id);
    }

    public Task<Void> saveMotor(Motor motor){
        return databaseRef.child("motors").child(motor.getIdmotor()).setValue(motor);
    }
}
