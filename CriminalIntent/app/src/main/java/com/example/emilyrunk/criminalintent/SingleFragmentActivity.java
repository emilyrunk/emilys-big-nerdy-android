package com.example.emilyrunk.criminalintent;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by emilyrunk on 5/25/17.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {
    //SingleFragmentActivity is used by CrimeListActivity and CrimeActivity
    //

    protected abstract Fragment createFragment();

    //To accomodate tablet master-detail interfaces, remove hardcoding of layout to inflate
    //@LayoutRes tells AS that any implementation of this method would return valid layout resourceID
    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    //Called when activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

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