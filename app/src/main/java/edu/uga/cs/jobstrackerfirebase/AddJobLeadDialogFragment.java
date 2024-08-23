package edu.uga.cs.jobstrackerfirebase;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

// A DialogFragment class to handle job lead additions from the job lead review activity
// It uses a DialogFragment to allow the input of a new job lead.
public class AddJobLeadDialogFragment extends DialogFragment {

    private EditText companyNameView;
    private EditText phoneView;
    private EditText urlView;
    private EditText commentsView;

    // This interface will be used to obtain the new job lead from an AlertDialog.
    // A class implementing this interface will handle the new job lead, i.e. store it
    // in Firebase and add it to the RecyclerAdapter.
    public interface AddJobLeadDialogListener {
        void addJobLead(JobLead jobLead);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create the AlertDialog view
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.add_job_lead_dialog,
                                             getActivity().findViewById(R.id.root));

        // get the view objects in the AlertDialog
        companyNameView = layout.findViewById( R.id.editText1 );
        phoneView = layout.findViewById( R.id.editText2 );
        urlView = layout.findViewById( R.id.editText3 );
        commentsView = layout.findViewById( R.id.editText4 );

        // create a new AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        // Set its view (inflated above).
        builder.setView(layout);

        // Set the title of the AlertDialog
        builder.setTitle( "New Job Lead" );
        // Provide the negative button listener
        builder.setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // close the dialog
                dialog.dismiss();
            }
        });
        // Provide the positive button listener
        builder.setPositiveButton( android.R.string.ok, new AddJobLeadListener() );

        // Create the AlertDialog and show it
        return builder.create();
    }

    private class AddJobLeadListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // get the new job lead data from the user
            String companyName = companyNameView.getText().toString();
            String phone = phoneView.getText().toString();
            String url = urlView.getText().toString();
            String comments = commentsView.getText().toString();

            // create a new JobLead object
            JobLead jobLead = new JobLead( companyName, phone, url, comments );

            // get the Activity's listener to add the new job lead
            AddJobLeadDialogListener listener = (AddJobLeadDialogListener) getActivity();

            // add the new job lead
            listener.addJobLead( jobLead );

            // close the dialog
            dismiss();
        }
    }
}
