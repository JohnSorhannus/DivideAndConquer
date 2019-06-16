package com.johnsorhannus.divideandconquer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.johnsorhannus.divideandconquer.viewmodels.MainTaskViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MainTaskViewModel viewModel;

    //public AppRepository repository;
    //int numTasks;
    //private MyViewModel viewModel;
    //private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //repository = new AppRepository(this);

        //TESTING
        //viewModel = ViewModelProviders.of(this).get(MyViewModel.class);

        //Set ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Attach the SectionsPagerAdapter to the ViewPager
        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        //Attach thE ViewPager to the TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

        //invalidateOptionsMenu();
    }

    //create action bar buttons
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //AppRepository repository = new AppRepository(this);

        getMenuInflater().inflate(R.menu.menu_main, menu);

        /*try {
            repository.retrieveSubTasksForSTFragment(); //this should be changed, retrieveAllMainTasks
            //should I enable the subtask option here if a main task is found
        } catch (NullPointerException e) {
            Log.d(TAG, "onPrepareOptionsMenu: Null pointer " + e.getMessage());
            //add sub task item disabled if no main tasks exists
            menu.findItem(R.id.action_create_subtask).setEnabled(false);
            //invalidateOptionsMenu();
        }*/

        //Sets the "create sub task" menu option enabled or disabled based on whether an active main task exists
        //menu.findItem(R.id.action_create_subtask).setEnabled(isActiveMainTaskAvailable());
        viewModel = ViewModelProviders.of(this).get(MainTaskViewModel.class);
        viewModel.getMainTasks().observe(this, new Observer<List<MainTask>>() {
            @Override
            public void onChanged(@Nullable List<MainTask> mainTasks) {
                if (mainTasks.size() == 0) {
                    //mainTasks.size();
                    menu.findItem(R.id.action_create_subtask).setEnabled(false);
                } else {
                    menu.findItem(R.id.action_create_subtask).setEnabled(true);
                }
            }
        });

        //menu.findItem(R.id.action_create_subtask).setEnabled(false);

        return super.onCreateOptionsMenu(menu);
    }

    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        return super.onPrepareOptionsMenu(menu);
    }*/

    //handle button activities in action bar


    //TODO: Write a function that says whether or not a user can add a sub task. It will check if there are any subtasks that are not past their due date
    /*public boolean isActiveMainTaskAvailable() {
        AppRepository repository = new AppRepository(this);

        //int f = repository.retrieveNumActiveMainTasks().getValue();
        //Log.d(TAG, "isActiveMainTaskAvailable: numActive=" + Integer.valueOf(repository.retrieveNumActiveMainTasks().getValue()));

        /*try {
            repository.retrieveNumActiveMainTasks().getValue();
            Log.d(TAG, "isActiveMainTaskAvailable: WORKS!");
            return true;
        } catch (NullPointerException e) {
            Log.d(TAG, "isActiveMainTaskAvailable: Null pointer " + e.getMessage());
            return false;
        }*/
        //int numActiveMainTasks;

        /*repository.retrieveNumActiveMainTasks().observe(this, new Observer<Integer>() {
            //int numActiveMainTasks;

            @Override
            public void onChanged(@Nullable Integer integer) {
                numTasks = integer;
            }
        });

        return (numTasks != 0);

        num = viewModel.retrieveNumActiveMainTasks().getValue();

        return (num != 0);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_create_subtask:
                //intent to create subtask activity
                Intent createSubTaskIntent = new Intent(this, AddSubTaskActivity.class);
                startActivity(createSubTaskIntent);
                return true;

            case R.id.action_create_maintask:
                //intent to create maintask activity
                Intent createMainTaskIntent = new Intent(this, AddMainTaskActivity.class);
                startActivity(createMainTaskIntent);
                return true;

            case R.id.about:
                //intent to create about activity
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new SubTaskFragment();
                case 1:
                    return new MainTaskFragment();
            }

            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.subtask_tab);
                case 1:
                    return getResources().getText(R.string.maintask_tab);
            }

            return null;
        }

    }
}
