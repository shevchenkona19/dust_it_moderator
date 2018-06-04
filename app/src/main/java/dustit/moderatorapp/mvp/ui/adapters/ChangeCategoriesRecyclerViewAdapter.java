package dustit.moderatorapp.mvp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dustit.moderatorapp.R;
import dustit.moderatorapp.mvp.model.entities.Category;

public class ChangeCategoriesRecyclerViewAdapter extends RecyclerView.Adapter<ChangeCategoriesRecyclerViewAdapter.CategoryChangeViewHolder> {
    private final List<Category> categories = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private IChangeCategoriesListInteractionListener interactionListener;

    public interface IChangeCategoriesListInteractionListener {
        void deleteCategory(String id);
    }

    public ChangeCategoriesRecyclerViewAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        if (context instanceof IChangeCategoriesListInteractionListener) {
            interactionListener = (IChangeCategoriesListInteractionListener) context;
        } else {
            Log.e(context.getClass().getSimpleName(), context.getClass().getSimpleName() + " must implement " + interactionListener.getClass().getSimpleName());
        }
    }

    @Override
    public CategoryChangeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_change_category_row, parent, false);
        return new CategoryChangeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoryChangeViewHolder holder, int position) {
        final Category category = categories.get(position);
        holder.tvCategoryName.setText(category.getName());
        holder.ibRemoveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interactionListener.deleteCategory(category.getId());
            }
        });
        Log.d("MY", "We are binding");
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void categoryDeleted(String id) {
        byte pos = -1;
        for(byte i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)) {
                categories.remove(i);
                pos = i;
                break;
            }
        }
        notifyItemRemoved(pos);
    }

    public void updateList(List<Category> list) {
        categories.clear();
        categories.addAll(list);
        notifyDataSetChanged();
    }

    static class CategoryChangeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvChangeCategoryItemCategoryName)
        TextView tvCategoryName;
        @BindView(R.id.ibChangeCategoriesItemRemoveCategory)
        ImageButton ibRemoveCategory;

        public CategoryChangeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
