package com.example.emilyrunk.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.emilyrunk.criminalintent.database.CrimeBaseHelper;
import com.example.emilyrunk.criminalintent.database.CrimeCursorWrapper;
import com.example.emilyrunk.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by emilyrunk on 5/25/17.
 */

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    //******* commenting out code related to mCrimes to update code to using database  **********//
//    private List<Crime> mCrimes;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
        //When getWritableDatabase() is called, CrimeBaseHelper will open up
        // data/data/com.example.emilyrunk.criminalintent/databases/crimeBase.db
        // creating a new file if it doesn't already exist
//        mCrimes = new ArrayList<>();

    }

    public void addCrime(Crime c) {
//        mCrimes.add(c);
        ContentValues values = getContentValues(c);
        //insert():
        // argument 1: table you want to insert into
        // argument 3: data you want to put in
        // argument 2: nullColumnHack - if you call insert() with empty ContentValue, not allowed
        //       if passed in value of UUID for nullColumnHack, it would ignore empty ContentValue,/
        //       pass in ContentValues with UUID set to null, succeeding and creating a new row
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public void deleteCrime(UUID id) {
//        for (Crime crime : mCrimes) {
//            if (crime.getId().equals(id)) {
//                mCrimes.remove(crime);
//            }mDatabase.delete()
//        }

    }

    public List<Crime> getCrimes() {
        //Get list of Crimes

//        return mCrimes;
//        return new ArrayList<>();
        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return crimes;
    }


    public Crime getCrime(UUID id) {
//        for (Crime crime : mCrimes) {
//            if (crime.getId().equals(id)) {
//                return crime;
//            }
//        }
        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID + " = ?",
                new String[] { id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }

    //Update rows in the database:
    public void updateCrime (Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + " = ?",
                new String[] { uuidString});
    }

//    private Cursor queryCrimes (String whereClause, String[] whereArgs) {
      private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null,  // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // grouBy
                null, // having
                null // orderBy
        );
//        return cursor;
          return new CrimeCursorWrapper(cursor);
      }

    //Create instance of Crime using ContentValues, a way to store data into database using key-value pairs
    //Key: Column Names
    //If there is a typo, insert/update will fail
    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);

        return values;
    }

}
