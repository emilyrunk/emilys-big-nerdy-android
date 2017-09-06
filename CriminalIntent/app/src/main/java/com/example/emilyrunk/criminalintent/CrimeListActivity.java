package com.example.emilyrunk.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by emilyrunk on 5/25/17.
 */

public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks {

    //Inflates Single fragment for phone
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
    @Override
    protected int getLayoutResId() {
        //Use the alias resource
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onCrimeSelected(Crime crime) {
    //if using phone interface, start new CrimePagerActivity
    //if using tablet interface, put CrimeFragment in detail_fragment_container
    //best to check if layout has detail_fragment_container
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = CrimePagerActivity.newIntent(this, crime.getId());
            startActivity(intent);
        } else {
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }
}
