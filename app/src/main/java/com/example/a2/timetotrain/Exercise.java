package com.example.a2.timetotrain;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

import static com.example.a2.timetotrain.MainActivity.APP_PREFERENCES_TYPECOURSE;


public class Exercise {
    String name, description, imagePath, gifPath;
    int typeTrain, course[][]; // typeTrain: 1 - number array; 2 - time array;
    private Context mContext;

    public Exercise(Context context) {
        this.mContext = context;
    }

    public Exercise(String name, int typeTrain, int[][] course, String description, Context context, String imagePath, String gifPath) {
        this.name = name;
        this.description = description;
        this.typeTrain = typeTrain;
        this.course = course;
        this.mContext = context;
        this.imagePath = imagePath;
        this.gifPath = gifPath;

    }

    public LinkedList<Exercise> parseExercises(Context context, String path) {
        LinkedList<Exercise> list = new LinkedList<>();
        AssetManager am = mContext.getAssets();

        try {
            InputStream is = am.open(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            /*
            * Reading the file and putting data in object for Format:
            *
            * 1.NAME
            * 2.TYPE TRAIN       --     1-count; 2-seconds;
            * 3.MAN COURSE 28 DAYS REPETITIONS OR SECONDS
            * 4.WOMAN COURSE 28 DAYS REPETITIONS OR SECONDS
            * 5.DESCRIPTION OF THIS
            * 6.IMAGEPATH (without expansion)
            *
            * */
            while ((line = reader.readLine()) != null) {
                String name = line;
                int typeTrain = Integer.parseInt(reader.readLine());
                String manCourse[] = reader.readLine().split(" ");
                String womanCourse[] = reader.readLine().split(" ");
                int[][] course = new  int[2][28];
                for (int i = 0; i < manCourse.length; i++) {
                    course[0][i] = Integer.parseInt(manCourse[i]);
                    course[1][i] = Integer.parseInt(womanCourse[i]);
                }
                String description = reader.readLine();
                String imagePath = reader.readLine();
                list.add(new Exercise(name, typeTrain, course, description, context, imagePath+".png", imagePath+".gif"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Exercise[] makeEx(Context context, String parsingFile) {
        Exercise exercise = new Exercise(context);
        LinkedList<Exercise> ex = exercise.parseExercises(context, parsingFile);
        Exercise[] exercises = new Exercise[ex.size()];
        for (int i = 0; i < ex.size(); i++)
            exercises[i] = ex.get(i);
        return exercises;
    }
    public static String getParsingFileName(String name){
        String parsingFile;
        switch (name){
            case "Пресс":
                parsingFile = "press.txt";
            case "Тренировка всего тела":
                parsingFile = "allBody.txt";
            default:
                parsingFile = "press.txt";
        }
        return parsingFile;
    }
}
