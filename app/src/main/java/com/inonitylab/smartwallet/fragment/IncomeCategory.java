package com.inonitylab.smartwallet.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.inonitylab.smartwallet.activity.PickCategoryActivity;
import com.inonitylab.smartwallet.database.CategoriesCRUD;
import com.inonitylab.smartwallet.model.Categories;
import com.inonitylab.smartwallet.model.IPickCategory;

import java.util.ArrayList;

public class IncomeCategory extends Fragment {

    IPickCategory iPickCategory;
    CategoriesCRUD categoriesCRUD;
    ArrayList<Categories> incomeCategoriesList;
    ArrayList<String> incomeCategoriesNameList;
    ListView incomeListView;

    public ArrayAdapter<String> incomeCategoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_income, container, false);
        incomeCategoriesList = new ArrayList<>();
        incomeCategoriesNameList = new ArrayList<>();
        categoriesCRUD = new CategoriesCRUD(view.getContext());

        incomeCategoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, incomeCategoriesNameList);
        incomeListView = (ListView) view.findViewById(R.id.list_item_incomeCategory);
        incomeListView.setAdapter(incomeCategoryAdapter);
        getAllData();

        incomeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               /* Intent intent = new Intent(getContext(), PickCategoryActivity.class);
                intent.putExtra("category", incomeCategoriesNameList.get(i));
                //intent.putExtra("id", "id");
                startActivity(intent);*/
                iPickCategory.pickCategory(incomeCategoriesNameList.get(i),"Income");
            }
        });

        return view;
    }

    private void getAllData() {
        incomeCategoriesList = categoriesCRUD.getIncomeCategories();
        for (Categories c : incomeCategoriesList){
            incomeCategoriesNameList.add(c.getCategoryName());
        }
        incomeCategoryAdapter.notifyDataSetChanged();
    }

    public IncomeCategory() {
        // Required empty public constructor
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
    }

}
