package com.inonitylab.smartwallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.inonitylab.smartwallet.R;
import com.inonitylab.smartwallet.adapter.ReminderAdapter;
import com.inonitylab.smartwallet.adapter.TransactionAdapter;
import com.inonitylab.smartwallet.database.CategoriesCRUD;
import com.inonitylab.smartwallet.database.SharedPrefDb;
import com.inonitylab.smartwallet.database.TransactionCRUD;
import com.inonitylab.smartwallet.fragment.FutureFragment;
import com.inonitylab.smartwallet.fragment.IncomeDashboardFragment;
import com.inonitylab.smartwallet.fragment.ExpenseDashboardFragment;
import com.inonitylab.smartwallet.model.TransactionModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ViewPager.OnPageChangeListener {


    ReminderAdapter reminderAdapter;
    SharedPrefDb sharedPrefDb;
    private Session session;
    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    ArrayList<TransactionModel> allRemindersList;

    /*  private int[] tabIcons = {
              R.drawable.ic_menu_camera,
              R.drawable.ic_menu_camera,
      };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);


        /*
        * creating categories for all users in first time app run
        * */
        sharedPrefDb = new SharedPrefDb(this);
        try {
            int firstTimeStatus = sharedPrefDb.getFirstTimeStatus();
            if (firstTimeStatus == 0){
                CategoriesCRUD crud = new CategoriesCRUD(getApplicationContext());
                crud.insertCategories();
                sharedPrefDb.setFirstTimeStatus();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        session = new Session(this);
        if (!session.loggedin()) {
            logout();
        }

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerMain);
        setupViewPager(viewPager);
        assert viewPager != null;
        viewPager.setOnPageChangeListener(this);


        tabLayout = (TabLayout) findViewById(R.id.tabsMian);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);
       // setupTabIcons();


   /*     FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setDataAndPrepareAdapter();
        //recycle view
        Log.d("bla bla","............... "+allRemindersList.size());
        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(reminderAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private void setDataAndPrepareAdapter() {
        TransactionCRUD transactionCRUD = new TransactionCRUD(this);
        allRemindersList = new ArrayList<>();
        allRemindersList = transactionCRUD.getAllReminders();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_reminder);
        reminderAdapter = new ReminderAdapter(allRemindersList);

        reminderAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.logout){
            logout();
        }
        else if (id == R.id.addTransaction){
            Intent intent = new Intent(getApplicationContext(), AddTransactionActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.activity_dashboard) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
        } else if (id == R.id.activity_transaction) {
                Intent intent = new Intent(getApplicationContext(), Transaction.class);
                startActivity(intent);
        } else if (id == R.id.activity_budgets) {
                Intent intent = new Intent(getApplicationContext(), BudgetActivity.class);
                startActivity(intent);
        } else if (id == R.id.activity_reminders) {
                Intent intent = new Intent(getApplicationContext(), ReminderActivity.class);
                startActivity(intent);
        } else if (id == R.id.activity_Categories) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
        } else if (id == R.id.activity_calendar) {
                Intent intent = new Intent(getApplicationContext(), Help.class);
                startActivity(intent);
        } else if (id == R.id.activity_forecasts) {
                Intent intent = new Intent(getApplicationContext(), ForecastActivity.class);
                startActivity(intent);
        } else if (id == R.id.activity_suggestions) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
        }
        else if (id == R.id.activity_monthly_report) {
            Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
            startActivity(intent);
        }else if (id == R.id.activity_transaction) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
       * make swipe tab alive
       * */
/*    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }*/

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ExpenseDashboardFragment(), "Expense");
        adapter.addFrag(new IncomeDashboardFragment(), "Income");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

/*    @Override
    public void onPageSelected(int position) {
//        reset floating
        searchPosition = position;
    }*/

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void logout() {
        session.setLoggedIn(false);
        finish();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
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
