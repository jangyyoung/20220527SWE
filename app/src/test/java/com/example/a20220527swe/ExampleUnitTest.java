package com.example.a20220527swe;

import org.junit.Test;

import static org.junit.Assert.*;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mPostReference;
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void clearFirebaseDatabase(boolean add){
        mPostReference=mDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        if(add){
            FirebasePost post = new FirebasePost(null,null);
            postValues = post.toMap();
        }
        childUpdates.clear();
        mPostReference.updateChildren(childUpdates);
    }
}