package id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public class SastraAdapter extends ListAdapter<Sastra, SastraAdapter.SastraHolder> {
    private OnItemClickListener listener;

    public SastraAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Sastra> DIFF_CALLBACK = new DiffUtil.ItemCallback<Sastra>() {
        @Override
        public boolean areItemsTheSame(@NonNull Sastra oldItem, @NonNull Sastra newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Sastra oldItem, @NonNull Sastra newItem) {
            return false;
        }

    };

    @NonNull
    @Override
    public SastraHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sastra_item, parent, false);
        return new SastraHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SastraHolder holder, int position) {

        Sastra currentSastra = getItem(position); //method list adapter

        holder.textViewTitle.setText(currentSastra.getTitle());
        holder.textViewSummary.setText(currentSastra.getSummary());
        holder.imageViewCover.setImageBitmap(BitmapFactory.decodeFile(currentSastra.getCover()));
    }

    public Sastra getSastraAt(int position){
        return getItem(position);  //method list adapter
    }

    class SastraHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewSummary;
        private ImageView imageViewCover;

        public SastraHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.sastra_title);
            textViewSummary = itemView.findViewById(R.id.sastra_summary);
            imageViewCover = itemView.findViewById(R.id.cover_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Sastra sastra);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
