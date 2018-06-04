package dustit.moderatorapp.mvp.ui.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dustit.moderatorapp.R;
import dustit.moderatorapp.mvp.model.entities.Category;
import dustit.moderatorapp.mvp.presenter.activities.ChangeCategoriesActivityPresenter;
import dustit.moderatorapp.mvp.ui.adapters.ChangeCategoriesRecyclerViewAdapter;
import dustit.moderatorapp.mvp.ui.interfaces.IChangeCategoriesActivityView;

public class ChangeCategoriesActivity extends AppCompatActivity implements IChangeCategoriesActivityView, ChangeCategoriesRecyclerViewAdapter.IChangeCategoriesListInteractionListener {

    @BindView(R.id.btnChangeCategoriesAddCategory)
    Button btnAddCategory;
    @BindView(R.id.rvChangeCategoriesList)
    RecyclerView rvCategoriesList;
    @BindView(R.id.tvChangeCategoryEmptyList)
    TextView tvEmptyList;

    private ChangeCategoriesRecyclerViewAdapter adapter;

    private final ChangeCategoriesActivityPresenter presenter = new ChangeCategoriesActivityPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_categories);
        ButterKnife.bind(this);
        presenter.bind(this);
        adapter = new ChangeCategoriesRecyclerViewAdapter(this);
        rvCategoriesList.setLayoutManager(new LinearLayoutManager(this));
        rvCategoriesList.setAdapter(adapter);
        presenter.reloadCategoriesList();
        new AlertDialog.Builder(this)
                .setTitle("Лицензия использования сервиса")
                .setMessage("Привет! Сейчас ты попал на самый важный раздел в мемспейсе. Думай прежде, чем делать. Это правило раздела. Если сервис упадет из-за твоих изменений(а они записываются) - тебе реально будет плохо;) Продолжим?")
                .setPositiveButton("Окей", null)
                .setNegativeButton("Лучше уйду", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setCancelable(false).create().show();
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(ChangeCategoriesActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                new AlertDialog.Builder(ChangeCategoriesActivity.this)
                        .setTitle("Введите название для категории:")
                        .setView(input)
                        .setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.createCategory(input.getText().toString());
                            }
                        }).setNegativeButton("Отмена", null)
                        .create().show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

    @Override
    public void onCreatedCategory(String name) {
        presenter.reloadCategoriesList();
    }

    @Override
    public void onDeletedCategory(String id) {
        adapter.categoryDeleted(id);
        if (adapter.getItemCount() == 0) {
            showEmpty();
        }
    }

    @Override
    public void onFailedToCreateCategory(String name) {
        showErrorCreatingCategory(name);
    }

    @Override
    public void onFailedToDeleteCategory(String id) {
        showErrorDeletingCategory();
    }

    @Override
    public void onFailedToLoadCategories() {
        showError();
    }

    @Override
    public void onCategoriesLoaded(List<Category> categories) {
        if (categories.size() != 0) {
            adapter.updateList(categories);
            showFull();
        } else {
            showEmpty();
        }
    }

    @Override
    public void deleteCategory(final String id) {
        new AlertDialog.Builder(this)
                .setTitle("Удалить категорию")
                .setMessage("Вы уверены, что хотите удалить категорию?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteCategory(id);

                    }
                })
                .setNegativeButton("Нет", null)
                .create().show();
    }

    private void showFull() {
        rvCategoriesList.setVisibility(View.VISIBLE);
        tvEmptyList.setVisibility(View.GONE);
    }

    private void showEmpty() {
        rvCategoriesList.setVisibility(View.GONE);
        tvEmptyList.setVisibility(View.VISIBLE);
    }

    private void showErrorCreatingCategory(String name) {
        Toast.makeText(this, "Не удалось создать категорию \"" + name + "\"", Toast.LENGTH_SHORT).show();
    }

    private void showErrorDeletingCategory() {
        Toast.makeText(this, "Не удалось удалить категорию", Toast.LENGTH_SHORT).show();
    }

    private void showError() {
        Toast.makeText(this, "Не удалось загрузить категории", Toast.LENGTH_SHORT).show();
    }

}
