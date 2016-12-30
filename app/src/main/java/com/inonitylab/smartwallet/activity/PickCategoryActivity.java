package com.inonitylab.smartwallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.inonitylab.smartwallet.R;
import com.inonitylab.smartwallet.fragment.ExpenseCategory;
import com.inonitylab.smartwallet.fragment.IncomeCategory;
import com.inonitylab.smartwallet.model.IPickCategory;

import java.util.ArrayList;
import java.util.List;

public class PickCategoryActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,IPickCategory {

    Bundle bundle;
    private TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.ic_menu_camera,
            R.drawable.ic_menu_camera,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_category);

        bundle = getIntent().getExtras();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPickCategory);
        setSupportActionBar(toolbar);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        assert viewPager != null;
        viewPager.setOnPageChangeListener(this);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    //implemented method from IPickCategory interface
    @Override
    public void pickCategory(String category,String categoryType) {
        Toast.makeText(getApplicationContext(),category,Toast.LENGTH_LONG).show();
        if (!category.isEmpty()){
            Intent intent = new Intent(PickCategoryActivity.this,AddTransactionActivity.class);
            intent.putExtras(bundle);
            intent.putExtra("category",category);
            intent.putExtra("categoryType",categoryType);
            intent.putExtra("flag","pickCategory");
            startActivity(intent);
        }
    }

    /*
   * make swipe tab alive
   * */
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ExpenseCategory(), "ExpenseCategory");
        adapter.addFrag(new IncomeCategory(), "IncomeCategory");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
