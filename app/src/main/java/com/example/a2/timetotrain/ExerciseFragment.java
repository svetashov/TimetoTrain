package com.example.a2.timetotrain;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.a2.timetotrain.dummy.DummyContent;
import com.example.a2.timetotrain.dummy.DummyContent.DummyItem;

import java.util.List;

public class ExerciseFragment extends ListFragment {

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_list, container, false);
        final Exercise[] arrayOfExercises = Exercise.makeEx(getContext(), "types.txt");
        TypeOfExerciseAdapter adapter = new TypeOfExerciseAdapter(getContext(), arrayOfExercises);
         listView = (ListView) view.findViewById(R.id.list_viewTypes_exercises);
        listView.setAdapter(adapter);


        return view;
    }

}
