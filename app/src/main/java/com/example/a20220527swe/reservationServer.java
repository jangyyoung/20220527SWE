package com.example.a20220527swe;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class reservationServer {

    public String table;//table id
    public String user;



    public reservationServer(){}

    public reservationServer(String table,String user){
        this.table=table;
        this.user=user;

    }
    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String,Object> result =new HashMap<>();
        result.put("table",table);
        result.put("user",user);

        return result;
    }
}
