package com.example.emilyrunk.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by emilyrunk on 5/25/17.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {
    //SingleFragmentActivity is used by CriminalListActivity and CrimeActivity
    //

    protected abstract Fragment createFragment();

    //Called when activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        //FragmentManager handles:
        //1. list of fragments
        //2. back stack of fragment transactions
        FragmentManager fm = getSupportFragmentManager();
        //Retrieve a fragment by it's container ID
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            //Transactions are used to add, remove, detach fragments in a list of fragments
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}