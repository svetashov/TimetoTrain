package com.example.a2.timetotrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
public class ExerciseFragment extends ListFragment {

    private TypeOfExerciseAdapter typesAdapter;
    private Exercise[] arrayOfExercises;
    public static final String EXTRAS_NAME_OF_FILE = "extras_name_file";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_list, container, false);
        arrayOfExercises = Exercise.makeEx(getContext(), "types.txt");
        typesAdapter = new TypeOfExerciseAdapter(getContext(), arrayOfExercises);
        setListAdapter(typesAdapter);


        return view;
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getContext(), ListExercisesActivity.class);
        intent.putExtra(EXTRAS_NAME_OF_FILE, arrayOfExercises[position].name);
        startActivity(intent);
    }
}
