package com.example.emilyrunk.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

/**
 * Created by emilyrunk on 6/27/17.
 */

public class CrimePagerActivity extends AppCompatActivity {
    private static final String EXTRA_CRIME_ID = "com.example.emilyrunk.criminalintent.crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    private Button mFirstCrimeButton;
    private Button mLastCrimeButton;

    //New Intent with extra
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        mFirstCrimeButton = (Button) findViewById(R.id.first_crime_button);
        mLastCrimeButton = (Button) findViewById(R.id.last_crime_button);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);

        //get list of crimes from CrimeLab
        mCrimes = CrimeLab.get(this).getCrimes();
        //New instance of FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        //set Adapter on FragmentStatePagerAdapter which needs an instance of FragmentManager (above)
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
        mFirstCrimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });
        mLastCrimeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mCrimes.size());
            }
        });


    }
}
