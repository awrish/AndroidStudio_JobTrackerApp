package edu.uga.cs.jobstrackerfirebase;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


// This is a DialogFragment to handle edits to a JobLead.
// The edits are: updates and deletions of existing JobLeads.
public class EditJobLeadDialogFragment extends DialogFragment {

    // indicate the type of an edit
    public static final int SAVE = 1;   // update an existing job lead
    public static final int DELETE = 2; // delete an existing job lead

    private EditText companyNameView;
    private EditText phoneView;
    private EditText urlView;
    private EditText commentsView;

    int position;     // the position of the edited JobLead on the list of job leads
    String key;
    String company;
    String phone;
    String url;
    String comments;

    // A callback listener interface to finish up the editing of a JobLead.
    // ReviewJobLeadsActivity implements this listener interface, as it will
    // need to update the list of JobLeads and also update the RecyclerAdapter to reflect the
    // changes.
    public interface EditJobLeadDialogListener {
        void updateJobLead(int position, JobLead jobLead, int action);
    }

    public static EditJobLeadDialogFragment newInstance(int position, String key, String company, String phone, String url, String comments) {
        EditJobLeadDialogFragment dialog = new EditJobLeadDialogFragment();

        // Supply job lead values as an argument.
        Bundle args = new Bundle();
        args.putString( "key", key );
        args.putInt( "position", position );
        args.putString("company", company);
        args.putString("phone", phone);
        args.putString("url", url);
        args.putString("comments", comments);
        dialog.setArguments(args);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) {

        key = getArguments().getString( "key" );
        position = getArguments().getInt( "position" );
        company = getArguments().getString( "company" );
        phone = getArguments().getString( "phone" );
        url = getArguments().getString( "url" );
        comments = getArguments().getString( "comments" );

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate( R.layout.add_job_lead_dialog, getActivity().findViewById( R.id.root ) );

        companyNameView = layout.findViewById( R.id.editText1 );
        phoneView = layout.findViewById( R.id.editText2 );
        urlView = layout.findViewById( R.id.editText3 );
        commentsView = layout.findViewById( R.id.editText4 );

        // Pre-fill the edit texts with the current values for this job lead.
        // The user will be able to modify them.
        companyNameView.setText( company );
        phoneView.setText( phone );
        urlView.setText( url );
        commentsView.setText( comments );

        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity(), R.style.AlertDialogStyle );
        builder.setView(layout);

        // Set the title of the AlertDialog
        builder.setTitle( "Edit Job Lead" );

        // The Cancel button handler
        builder.setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // close the dialog
                dialog.dismiss();
            }
        });

        // The Save button handler
        builder.setPositiveButton( "SAVE", new SaveButtonClickListener() );

        // The Delete button handler
        builder.setNeutralButton( "DELETE", new DeleteButtonClickListener() );

        // Create the AlertDialog and show it
        return builder.create();
    }

    private class SaveButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String companyName = companyNameView.getText().toString();
            String phone = phoneView.getText().toString();
            String url = urlView.getText().toString();
            String comments = commentsView.getText().toString();
            JobLead jobLead = new JobLead( companyName, phone, url, comments );
            jobLead.setKey( key );

            // get the Activity's listener to add the new job lead
            EditJobLeadDialogListener listener = (EditJobLeadDialogFragment.EditJobLeadDialogListener) getActivity();
            // add the new job lead
            listener.updateJobLead( position, jobLead, SAVE );

            // close the dialog
            dismiss();
        }
    }

    private class DeleteButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick( DialogInterface dialog, int which ) {

            JobLead jobLead = new JobLead( company, phone, url, comments );
            jobLead.setKey( key );

            // get the Activity's listener to add the new job lead
            EditJobLeadDialogFragment.EditJobLeadDialogListener listener = (EditJobLeadDialogFragment.EditJobLeadDialogListener) getActivity();            // add the new job lead
            listener.updateJobLead( position, jobLead, DELETE );
            // close the dialog
            dismiss();
        }
    }
}
