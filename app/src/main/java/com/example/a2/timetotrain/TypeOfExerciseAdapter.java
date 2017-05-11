package com.example.a2.timetotrain;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;


public class TypeOfExerciseAdapter extends ArrayAdapter<Exercise> {
    public TypeOfExerciseAdapter(Context context, Exercise[] arr) {
        super(context, R.layout.item_card, arr);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Exercise exercise = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_card, null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView_card_image_exercise);
        InputStream inputStream = null;
        try {
            inputStream = getContext().getAssets().open(exercise.imagePath);
            Drawable d = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(d);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        ((TextView) convertView.findViewById(R.id.textView3)).setText(exercise.name);
        return convertView;

    }
}
