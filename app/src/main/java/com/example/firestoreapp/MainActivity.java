package com.example.firestoreapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.firestoreapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");
    private DocumentReference friendsRef = db.collection("Users").document("cPUxsbXKar4FH0T27FSV");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToNewDocument();
            }
        });

        binding.read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllDocumentsInCollection();
            }
        });

        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSpecificDocument();
            }
        });

        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAll();
            }
        });
        
    }

    private void saveDataToNewDocument() {
        String name = binding.personName.getText().toString();
        String email = binding.personEmail.getText().toString();

        Friend friend = new Friend(name, email);

        collectionReference .add(friend).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // Getting document Id
                String docId = documentReference.getId();
            }
        });
    }

    private void getAllDocumentsInCollection() {
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                StringBuilder data = new StringBuilder();

                // This code is executed when data retrieval is successful
                // the queryDocumentSnapshot contains the documents in the collection

                for ( QueryDocumentSnapshot snapshot: queryDocumentSnapshots ) {
                    // Transforming snapshot into objects
                    Friend friend = snapshot.toObject(Friend.class);
                    data.append("Name: ").append(friend.getName()).append(" Email: ").append(friend.getEmail()).append("\n");
                }
                binding.status.setText(data);
            }
        });
    }

    private void updateSpecificDocument() {
        String name = binding.personName.getText().toString();
        String email = binding.personEmail.getText().toString();

        friendsRef.update("name", name);
        friendsRef.update("email", email);
    }

    private void deleteAll() {
        friendsRef.delete();
    }

}