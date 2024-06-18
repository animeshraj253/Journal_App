package raj.animesh.journalapp;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    List<Journal> list;

    public MyAdapter(Context context, List<Journal> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.journal_row, parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Journal currentJournal = list.get(position);

        holder.title.setText(currentJournal.getTitle());
        holder.name.setText(currentJournal.getUserName());
        holder.thoughts.setText(currentJournal.getThoughts());

        String imageURL = currentJournal.getImageUrl();

        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(
                currentJournal.getTimeAdded().getSeconds()*1000);
        holder.dateAdded.setText(timeAgo);

        // using glide library to display image
        Glide.with(context).load(imageURL).fitCenter().into(holder.image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    // view holder
    public  class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, thoughts, dateAdded, name;
        public ImageView image, shareButton;
        public String userID,username;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.journal_title_list);
            thoughts = itemView.findViewById(R.id.journal_thought_list);
            dateAdded = itemView.findViewById(R.id.journal_timestamp_list);
            image = itemView.findViewById(R.id.journal_image_list);
            name = itemView.findViewById(R.id.journal_row_username);
            shareButton = itemView.findViewById(R.id.journal_row_share_button);

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // code
                }
            });


        }
    }

}
