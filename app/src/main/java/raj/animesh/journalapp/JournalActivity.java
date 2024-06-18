package raj.animesh.journalapp;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class JournalActivity extends AppCompatActivity {


    // firebase auth
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUserJournal;
    private FirebaseAuth.AuthStateListener authStateListener ;


    //fire store
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Journal");

    // storage
    private StorageReference storageReference;

    // list of journals
    private List<Journal> journalList;

    // recycler view
    private RecyclerView recyclerView;
    private  MyAdapter myAdapter;

    private FloatingActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(JournalActivity.this, AddJournalActivity.class);
                startActivity(i);
                finish();
            }
        });


        // firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        // checking existing user or not
        firebaseUserJournal = firebaseAuth.getCurrentUser();
        if (firebaseUserJournal == null){
            Intent i = new Intent(JournalActivity.this, MainActivity.class);
            startActivity(i);
        }

        // auth listener
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth != null){

                }else{

                }
            }
        };

        // widgets
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // post array list
        journalList = new ArrayList<>();
    }

    // adding menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();
        if ( itemID == R.id.action_add){
            if ( firebaseUserJournal != null && firebaseAuth != null){
                Intent i = new Intent(JournalActivity.this, AddJournalActivity.class);
                startActivity(i);
                finish();
            }
        }
        else if (itemID == R.id.action_signout){
            if( firebaseUserJournal != null && firebaseAuth != null){
                firebaseAuth.signOut();
                Intent i = new Intent(JournalActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // QueryDocumentSnapshot: is an object that represents
                // a single document retrieved from a Firestore query
                // QueryDocumentSnapshot --> Document
                // QuerySnapshot --> List of Documents
                // DocumentSnapshot --> Object

                for (QueryDocumentSnapshot journals : queryDocumentSnapshots){
                    Journal journal  = journals.toObject(Journal.class);
                    journalList.add(journal);
                }

                //recycler view
                myAdapter = new MyAdapter(JournalActivity.this,journalList);

                myAdapter.notifyDataSetChanged();

                recyclerView.setAdapter(myAdapter);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(JournalActivity.this, "Oops! Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}