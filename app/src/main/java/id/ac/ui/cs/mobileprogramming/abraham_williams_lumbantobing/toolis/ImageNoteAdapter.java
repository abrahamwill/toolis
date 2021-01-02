package id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class ImageNoteAdapter extends ListAdapter<ImageNote, ImageNoteAdapter.ImageNoteHolder> {
    private OnItemClickListener listener;

    public ImageNoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<ImageNote> DIFF_CALLBACK = new DiffUtil.ItemCallback<ImageNote>() {
        @Override
        public boolean areItemsTheSame(@NonNull ImageNote oldItem, @NonNull ImageNote newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ImageNote oldItem, @NonNull ImageNote newItem) {
            return false;
        }

    };

    @NonNull
    @Override
    public ImageNoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.imagenote_item, parent, false);
        return new ImageNoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageNoteHolder holder, int position) {

//        ImageNote currentImageNote = getItem(position); //method list adapter
//
//        holder.imageViewImage.setImageBitmap(BitmapFactory.decodeFile(currentImageNote.getImage()));

        ImageNote currentImageNote = getItem(position); //method list adapter

        if (currentImageNote.getImage() != null) {
            Log.e("image", currentImageNote.getImage());

            String currentImagePath = currentImageNote.getImage();

            String checkHttp = currentImagePath.substring(0, 4);
            Log.e("checkhttp", checkHttp);

            if (checkHttp.equals("http")) {
                Log.e("image", "masuk");

                LoadImage loadImage = new LoadImage(holder.imageViewImage);
                loadImage.execute(currentImageNote.getImage());
            } else {
                Log.e("image", "masuk2");
                holder.imageViewImage.setImageBitmap(BitmapFactory.decodeFile(currentImageNote.getImage()));
            }
        }

    }

    public ImageNote getImageNoteAt(int position){
        return getItem(position);  //method list adapter
    }

    class ImageNoteHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewImage;

        public ImageNoteHolder(@NonNull View itemView) {
            super(itemView);
            imageViewImage = itemView.findViewById(R.id.imagenote_image);

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
        void onItemClick(ImageNote imageNote);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
