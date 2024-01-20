package com.example.profile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBhelper extends SQLiteOpenHelper {


    private static final String dbname="User_data.db";
    public DBhelper(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase Db) {
        Db.execSQL("create table userData(email TEXT primary key, password TEXT, firstName TEXT , lastName TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase Db, int i, int i1) {
        Db.execSQL("drop table if exists userData");
        onCreate(Db);
    }

    // Method for data insertion to database
    public Boolean insertData(String email, String password,String firstName, String lastName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("firstName",firstName);
        cv.put("lastName",lastName);
        cv.put("email",email);
        cv.put("password",password);
        long result = db.insert("userData",null,cv);

        //result return in which row data is inserted in the database, if insertion fails it return result = -1
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    // Method to check if user email already exist in database
    public Boolean checkEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from userData where email = ?",new String[]{email});

        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    // Login credentials checking
    public Boolean checkEmailPassword(String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from userData where email= ? and password = ?", new String[]{email,password});

        if(cursor.getCount()> 0){
            return true;
        }else{
            return false;
        }
    }

    // Method to get user data to show in profile
    public UserData getUserData(String UserEmail){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from userData where userData.email = ?";
        Cursor cursor = db.rawQuery(query,new String[]{UserEmail});
        UserData userData = null;
        ArrayList<UserData> dataholder = new ArrayList<>();
        try {
            if (cursor.moveToFirst()) {
                int emailIndex = cursor.getColumnIndex("email");
                int firstNameIndex = cursor.getColumnIndex("firstName");
                int lastNameIndex = cursor.getColumnIndex("lastName");

                // Check if columns exist in the cursor
                if (emailIndex != -1 && firstNameIndex != -1 && lastNameIndex != -1) {
                    String userEmail = cursor.getString(emailIndex);
                    String firstName = cursor.getString(firstNameIndex);
                    String lastName = cursor.getString(lastNameIndex);

                    userData = new UserData(firstName,lastName, userEmail);
                }
            }
        }finally {
            cursor.close();
        }
        return userData;
    }

//    public void logoutUser(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put("password","");
//        String whereClause = "email = ?";
//        String [] whereArgs = new String[]{UserEmail};
//        db.update("userData",cv,whereClause,whereArgs);
//        db.close();
//    }
}
