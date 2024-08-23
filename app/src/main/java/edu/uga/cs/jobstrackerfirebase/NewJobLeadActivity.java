package edu.uga.cs.jobstrackerfirebase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * This class is an activity to create a new job lead.
 */
public class NewJobLeadActivity extends AppCompatActivity {

    public static final String DEBUG_TAG = "NewJobLeadActivity";

    private EditText companyNameView;
    private EditText phoneView;
    private EditText urlView;
    private EditText commentsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_job_lead);

        companyNameView = findViewById( R.id.editText1 );
        phoneView = findViewById( R.id.editText2 );
        urlView = findViewById( R.id.editText3 );
        commentsView = findViewById( R.id.editText4 );
        Button saveButton = findViewById(R.id.button);

        saveButton.setOnClickListener( new ButtonClickListener()) ;
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String companyName = companyNameView.getText().toString();
            String phone = phoneView.getText().toString();
            String url = urlView.getText().toString();
            String comments = commentsView.getText().toString();
            final JobLead jobLead = new JobLead( companyName, phone, url, comments );

            // Add a new element (JobLead) to the list of job leads in Firebase.
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("jobleads");

            // First, a call to push() appends a new node to the existing list (one is created
            // if this is done for the first time).  Then, we set the value in the newly created
            // list node to store the new job lead.
            // This listener will be invoked asynchronously, as no need for an AsyncTask, as in
            // the previous apps to maintain job leads.
            myRef.push().setValue( jobLead )
                    .addOnSuccessListener( new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Show a quick confirmation
                                Toast.makeText(getApplicationContext(), "Job lead created for " + jobLead.getCompanyName(),
                                        Toast.LENGTH_SHORT).show();

                                // Clear the EditTexts for next use.
                                companyNameView.setText("");
                                phoneView.setText("");
                                urlView.setText("");
                                commentsView.setText("");
                            }
                    })
                    .addOnFailureListener( new OnFailureListener() {
                            @Override
                            public void onFailure( @NonNull Exception e ) {
                                Toast.makeText( getApplicationContext(), "Failed to create a Job lead for " + jobLead.getCompanyName(),
                                        Toast.LENGTH_SHORT).show();
                            }
                    });
        }
    }

    @Override
    protected void onResume() {
        Log.d( DEBUG_TAG, "NewJobLeadActivity.onResume()" );
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d( DEBUG_TAG, "NewJobLeadActivity.onPause()" );
        super.onPause();
    }

    // The following activity callback methods are not needed and are for educational purposes only
    @Override
    protected void onStart() {
        Log.d( DEBUG_TAG, "NewJobLeadActivity.onStart()" );
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d( DEBUG_TAG, "NewJobLeadActivity.onStop()" );
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d( DEBUG_TAG, "NewJobLeadActivity.onDestroy()" );
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d( DEBUG_TAG, "NewJobLeadActivity.onRestart()" );
        super.onRestart();
    }
}
