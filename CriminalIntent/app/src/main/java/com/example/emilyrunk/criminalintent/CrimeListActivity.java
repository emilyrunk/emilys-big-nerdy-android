package com.example.emilyrunk.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by emilyrunk on 5/25/17.
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
