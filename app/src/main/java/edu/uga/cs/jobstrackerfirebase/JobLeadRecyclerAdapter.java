package edu.uga.cs.jobstrackerfirebase;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * This is an adapter class for the RecyclerView to show all job leads.
 */
public class JobLeadRecyclerAdapter extends RecyclerView.Adapter<JobLeadRecyclerAdapter.JobLeadHolder> {

    public static final String DEBUG_TAG = "JobLeadRecyclerAdapter";

    private List<JobLead> jobLeadList;
    private Context context;

    public JobLeadRecyclerAdapter( List<JobLead> jobLeadList, Context context ) {
        this.jobLeadList = jobLeadList;
        this.context = context;
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    class JobLeadHolder extends RecyclerView.ViewHolder {

        TextView companyName;
        TextView phone;
        TextView url;
        TextView comments;

        public JobLeadHolder(View itemView ) {
            super(itemView);

            companyName = itemView.findViewById( R.id.companyName );
            phone = itemView.findViewById( R.id.phone );
            url = itemView.findViewById( R.id.url );
            comments = itemView.findViewById( R.id.comments );
        }
    }

    @NonNull
    @Override
    public JobLeadHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.job_lead, parent, false );
        return new JobLeadHolder( view );
    }

    // This method fills in the values of the Views to show a JobLead
    @Override
    public void onBindViewHolder( JobLeadHolder holder, int position ) {
        JobLead jobLead = jobLeadList.get( position );

        Log.d( DEBUG_TAG, "onBindViewHolder: " + jobLead );

        String key = jobLead.getKey();
        String company = jobLead.getCompanyName();
        String phone = jobLead.getPhone();
        String url = jobLead.getUrl();
        String comments = jobLead.getComments();

        holder.companyName.setText( jobLead.getCompanyName());
        holder.phone.setText( jobLead.getPhone() );
        holder.url.setText( jobLead.getUrl() );
        holder.comments.setText( jobLead.getComments() );

        // We can attach an OnClickListener to the itemView of the holder;
        // itemView is a public field in the Holder class.
        // It will be called when the user taps/clicks on the whole item, i.e., one of
        // the job leads shown.
        // This will indicate that the user wishes to edit (modify or delete) this item.
        // We create and show an EditJobLeadDialogFragment.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d( TAG, "onBindViewHolder: getItemId: " + holder.getItemId() );
                //Log.d( TAG, "onBindViewHolder: getAdapterPosition: " + holder.getAdapterPosition() );
                EditJobLeadDialogFragment editJobFragment =
                        EditJobLeadDialogFragment.newInstance( holder.getAdapterPosition(), key, company, phone, url, comments );
                editJobFragment.show( ((AppCompatActivity)context).getSupportFragmentManager(), null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobLeadList.size();
    }
}
