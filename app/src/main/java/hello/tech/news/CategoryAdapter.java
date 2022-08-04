package hello.tech.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder> {
    private final ArrayList<CategoriesModal> categoriesModals;
    private final CategoryClickInterface categoryClickInterface;

    public CategoryAdapter(ArrayList<CategoriesModal> categoriesModals, CategoryClickInterface categoryClickInterface) {
        this.categoriesModals = categoriesModals;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categories,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.viewHolder holder, int position) {
CategoriesModal categoriesModal=categoriesModals.get(position);
holder.tvName.setText(categoriesModal.getCategory());
        Picasso.get().load(categoriesModal.getCategoryImageUrl()).into(holder.ivImage);
        holder.itemView.setOnClickListener(v -> categoryClickInterface.onCategoryClick(position));
    }

    @Override
    public int getItemCount() {
        return categoriesModals.size();
    }

    public interface CategoryClickInterface
    {
        void onCategoryClick(int position);
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final ImageView ivImage;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvCategories);
            ivImage=itemView.findViewById(R.id.ivCategories);
        }
    }
}
