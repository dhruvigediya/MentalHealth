package com.texastech.talk.navigation;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.texastech.talk.R;
import com.texastech.talk.database.AppDatabase;
import com.texastech.talk.database.Mood;
import com.texastech.talk.database.MoodDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatisticsFragment extends Fragment {

    public StatisticsFragment() {

    }

    public static StatisticsFragment newInstance() {

        return new StatisticsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drawMoodGraph(view);
    }

    private List<Mood> getPastWeekMoods(View view) {
        AppDatabase database = AppDatabase.getDatabase(view.getContext());
        MoodDao moodDao = database.moodDao();

        List<Mood> pastMoods = new ArrayList<>();
        List<Mood> allMoods = moodDao.getAll();
        for (int i = 0; i < 7 && i < allMoods.size(); i++) {
            pastMoods.add(allMoods.get(i));
        }

        return pastMoods;
    }

    private void drawMoodGraph(View view) {

        AppDatabase database = AppDatabase.getDatabase(view.getContext());
        MoodDao moodDao = database.moodDao();
        List<Mood> allMoods = moodDao.getAll();
        List<Mood> pastWeekMoods = new ArrayList<>();
        int count = 0;
        for (int i = allMoods.size() - 1; i >= 0 && count < 7; i--) {
            pastWeekMoods.add(allMoods.get(i));
            count++;
        }
        Log.d("SizeLog", String.format("Got last %d elements", pastWeekMoods.size()));

        List<Entry> entries = new ArrayList<>();
        for (Mood mood : pastWeekMoods) {
            Log.d("Statistics", String.format("Found entry: %d %d", mood.date, mood.value));
            entries.add(new Entry(mood.date, mood.value));
        }
        Collections.sort(entries, new EntryXComparator());

        // TODO: Color in the different moods differently
        LineDataSet dataSet = new LineDataSet(entries, "Mood History");
        dataSet.setColor(R.color.colorBottomNavActive);
        dataSet.setValueTextColor(R.color.colorBottomNavActive);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setDrawFilled(true);
        dataSet.setLineWidth(4.0f);
        dataSet.setHighlightLineWidth(4);
        dataSet.setColor(ContextCompat.getColor(getContext(), R.color.colorBottomNavActive));

        LineChart chart = view.findViewById(R.id.mood_chart);
        chart.getDescription().setEnabled(false);
        chart.getAxisLeft().setTextColor(Color.WHITE);
        chart.getXAxis().setTextColor(Color.WHITE);
        chart.getXAxis().setAxisMinimum(1);
        chart.getXAxis().setAxisMaximum(7);
        chart.getLegend().setEnabled(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.disableAxisLineDashedLine();
        xAxis.setDrawLabels(false);

        String[] weekdays = {"Sun", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(weekdays));

        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setDrawLabels(false);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
    }
}