package com.example.a20220527swe;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;
public class FirebasePost {

    public String id;
    public String name;


    public FirebasePost(){
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }
    public String getid(){
        return id;
    }
    public void setid(String id){
        this.id=id;
    }
    public String getname(){
        return name;
    }
    public void setname(String id){
        this.name=name;
    }

    public FirebasePost(String id, String name) {
        this.id = id;
        this.name = name;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);

        return result;
    }
}
