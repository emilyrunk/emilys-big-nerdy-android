package com.example.emilyrunk.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by emilyrunk on 8/23/17.
 */

public class PhotoZoomFragment extends DialogFragment {
    public static final String EXTRA_DATE = "com.example.emilyrunk.criminalintent.image";
    private static final String ARG_IMAGE = "image";

    private ImageView mPhotoZoom;

    public static PhotoZoomFragment newInstance(String photoFile) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_IMAGE, photoFile);

        PhotoZoomFragment fragment = new PhotoZoomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String image = (String) getArguments().getSerializable(ARG_IMAGE);
        Log.e("PHOTO FILE STRING", image);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_photo, null);

        Bitmap bitmap = PictureUtils.getScaledBitmap(image, getActivity());

        mPhotoZoom = (ImageView) v.findViewById(R.id.dialog_photo_zoom);
        mPhotoZoom.setImageBitmap(bitmap);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .create();
    }
}
