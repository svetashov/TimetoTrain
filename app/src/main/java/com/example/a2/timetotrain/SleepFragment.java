package com.example.a2.timetotrain;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jjoe64.graphview.GraphView;


public class SleepFragment extends Fragment {

    private GraphView graphViewSleep;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View currentView = inflater.inflate(R.layout.fragment_sleep, container, false);
        graphViewSleep = (GraphView) currentView.findViewById(R.id.graph);

        return currentView;
    }

}
