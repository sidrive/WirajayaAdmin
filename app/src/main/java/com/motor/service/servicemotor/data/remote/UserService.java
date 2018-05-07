package com.motor.service.servicemotor.data.remote;

import android.app.Application;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.motor.service.servicemotor.data.model.Motor;
import com.motor.service.servicemotor.data.remote.model.EmailConfirmation;
import com.motor.service.servicemotor.data.remote.model.User;

/**
 * Created by ikun on 22/12/17.
 */

public class UserService {

    private Application application;
    private DatabaseReference databaseRef;

    public UserService(Application application) {
        this.application = application;
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
    }



    public void createUser(User user) {
        if(user.getPhoto_url() == null) {
            user.setPhoto_url("NOT");
        }
        databaseRef.child("users").child(user.getUid()).setValue(user);

    }


    public DatabaseReference getUser(String userUid) {
        return databaseRef.child("users").child(userUid);
    }


    public Task<Void> updateUser(User user) {
        databaseRef.child("user-status").child(user.getUid()).child("active").setValue("false");
        return databaseRef.child("users").child(user.getUid()).setValue(user);
    }

    public void deleteUser(String key) {

    }

    public Task<Void> updateMotor(Motor motor) {
        return databaseRef.child("motors").child(motor.getUserid()).child(motor.getIdmotor()).setValue(motor);
    }

    //users

    public void updateUserToken(String uid, String token){
        databaseRef.child("users").child(uid).child("userTokens").child(token).setValue(true);
    }

    public DatabaseReference getService(String id){
        return databaseRef.child("service").child(id);
    }

    public void sendEmailConfirmation(EmailConfirmation emailConfirmation){
        databaseRef.child("confirmationEmailRequest").push().setValue(emailConfirmation);
    }

    public void verifyUser(String uid, boolean status){
        databaseRef.child("users").child(uid).child("verified").setValue(status);
    }

    // userabout

    public DatabaseReference getUserAbout(String uid){
        return databaseRef.child("users-about").child(uid);
    }


    public void updateAbout(String uid, String content){
        databaseRef.child("users-about").child(uid).setValue(content);
        databaseRef.child("users").child(uid).child("about").setValue(content);
    }

    //userabout


    //userskills

    public DatabaseReference getUserSkills(String uid){
        return databaseRef.child("user-skills").child(uid);
    }
    public DatabaseReference getTotalUserSkill(String uid){
        return databaseRef.child("users").child(uid).child("totalSkill");
    }
    public void updateTotalSkill(String uid, int total){
        databaseRef.child("users").child(uid).child("totalSkill").setValue(total);
    }




    //userschedule

    public DatabaseReference getUserSchedule(String uid){
        return databaseRef.child("user-shedules").child(uid);
    }
    public DatabaseReference createUserSchedule(String uid){
        return databaseRef.child("user-shedules").child(uid);
    }
    public void updateSchedule(String uid, String date){
        databaseRef.child("user-schedules").child(uid).child(date).setValue(true);
    }

    public Task<Void> removeUserSchedule(String uid, String date){
        return databaseRef.child("user-schedules").child(uid).child(date).removeValue();
    }
    public Query getDaySchedule(){
        return databaseRef.child("hari-mengajar").orderByChild("id");
    }
    public DatabaseReference setTimeSchedule(){
        return databaseRef.child("user-schedules");
    }
    public Task<Void> removeUserTimeSchedule(String id){
        return databaseRef.child("user-schedules").child(id).removeValue();
    }
    public Task<Void> updateTimeSchedule(String uid, String day, String time){
        return databaseRef.child("user-schedules").child(uid).child(uid+"_"+day).setValue(time);
    }
    public DatabaseReference getUserTimeSchedule(String uid){
        return databaseRef.child("user-schedules").child(uid);
    }
    public Query getUserTimeScheduleById(String uid){
        return databaseRef.child("user-schedules").orderByChild("id").equalTo(uid);
    }

    //userschedule



    //Userlocation
    public DatabaseReference getUserLocation(String uid){
        return databaseRef.child("user-location").child(uid);
    }


    //update price
    public  void updateUserPrice(String uid, int price){
        databaseRef.child("users").child(uid).child("startFrom").setValue(price);
    }

    //update price
    public DatabaseReference getUserPayment(String uid){
        return databaseRef.child("partner-payment").child(uid);
    }


    public void updateStatus(String uid, String status){
        databaseRef.child("user-status").child(uid).child("active").setValue(status);
        boolean stat = Boolean.parseBoolean(status);
        databaseRef.child("users").child(uid).child("active").setValue(stat);
    }




    public DatabaseReference getAcceptTOS(String uid){
        return databaseRef.child("user").child(uid).child("acceptTOS");
    }

    public void updateAcceptTOS(String uid, boolean status){
        databaseRef.child("users").child(uid).child("acceptTOS").setValue(status);
    }
    // User Schedule


    public void updateFirebaseToken(String uid, String token){
        databaseRef.child("users").child(uid).child("userDeviceToken").setValue(token);
    }

}
