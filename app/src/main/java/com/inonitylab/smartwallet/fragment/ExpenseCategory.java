package com.inonitylab.smartwallet.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.inonitylab.smartwallet.R;
import com.inonitylab.smartwallet.database.CategoriesCRUD;
import com.inonitylab.smartwallet.model.Categories;
import com.inonitylab.smartwallet.model.IPickCategory;

import java.util.ArrayList;


public class ExpenseCategory extends Fragment {
    IPickCategory iPickCategory;
    CategoriesCRUD categoriesCRUD;
    ArrayList<Categories> expenseCategoriesList;
    ArrayList<String> expenseCategoriesNameList;
    ListView expenseListView;

    public ArrayAdapter<String> expenseCategoryAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);
        expenseCategoriesList = new ArrayList<>();
        expenseCategoriesNameList = new ArrayList<>();
        categoriesCRUD = new CategoriesCRUD(view.getContext());

        expenseCategoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, expenseCategoriesNameList);
        expenseListView = (ListView) view.findViewById(R.id.list_item_expenseCategory);
        expenseListView.setAdapter(expenseCategoryAdapter);
        getAllData();

        expenseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               /* Intent intent = new Intent(getContext(), PickCategoryActivity.class);
                intent.putExtra("category", incomeCategoriesNameList.get(i));
                //intent.putExtra("id", "id");
                startActivity(intent);*/
                iPickCategory.pickCategory(expenseCategoriesNameList.get(i),"Expense");
            }
        });

        return view;
    }

    private void getAllData() {
        expenseCategoriesList = categoriesCRUD.getExpenseCategories();
        for (Categories c : expenseCategoriesList){
            expenseCategoriesNameList.add(c.getCategoryName());
        }
        expenseCategoryAdapter.notifyDataSetChanged();
    }
    public ExpenseCategory() {
        // Required empty public constructor
    }
    public static ExpenseCategory newInstance(String param1, String param2) {
        ExpenseCategory fragment = new ExpenseCategory();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iPickCategory = (IPickCategory) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }
}
