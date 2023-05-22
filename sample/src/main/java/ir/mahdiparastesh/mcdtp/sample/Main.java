package ir.mahdiparastesh.mcdtp.sample;

import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.icu.util.IndianCalendar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import ir.mahdiparastesh.mcdtp.date.DatePickerDialog;
import ir.mahdiparastesh.mcdtp.sample.databinding.MainBinding;
import ir.mahdiparastesh.mcdtp.time.TimePickerDialog;

public class Main extends FragmentActivity {
    MainBinding b;
    HashMap<String, Class<? extends Calendar>> calendars = new HashMap<>();
    ArrayList<String> calIndex = new ArrayList<>();
    String chosenCal;
    DatePickerDialog.Version chosenDVer;
    TimePickerDialog.Version chosenTVer;
    boolean is24HourMode;
    boolean enableSeconds;

    public Main() {
        addCal("Gregorian", GregorianCalendar.class);
        addCal("Persian", PersianCalendar.class);
        addCal("Indian", IndianCalendar.class);
    }

    private void addCal(String key, Class<? extends Calendar> value) {
        calendars.put(key, value);
        calIndex.add(key);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = MainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        // Calendar Type
        ArrayAdapter<String> calAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                calIndex);
        calAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        b.calendar.setAdapter(calAdapter);
        b.calendar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenCal = calIndex.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        b.calendar.setSelection(0);

        // Version
        ArrayAdapter<String> verAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                new String[]{"Version 1", "Version 2"});
        verAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        b.version.setAdapter(verAdapter);
        b.version.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0 -> {
                        chosenDVer = DatePickerDialog.Version.VERSION_1;
                        chosenTVer = TimePickerDialog.Version.VERSION_1;
                    }
                    case 1 -> {
                        chosenDVer = DatePickerDialog.Version.VERSION_2;
                        chosenTVer = TimePickerDialog.Version.VERSION_2;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        b.version.setSelection(1);

        // Checkboxes
        b.is24HourMode.setOnCheckedChangeListener((v, isChecked) -> is24HourMode = isChecked);
        b.enableSeconds.setOnCheckedChangeListener((v, isChecked) -> enableSeconds = isChecked);

        // Date Picker
        b.datePicker.setOnClickListener(v -> {
            Calendar cal;
            try {
                cal = Objects.requireNonNull(calendars.get(chosenCal)).newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }

            DatePickerDialog<?> picker = DatePickerDialog.newInstance((dialog, y, m, d) ->
                    Toast.makeText(this, z(y) + "/" + z(m) + "/" + z(d),
                            Toast.LENGTH_LONG).show(), cal);
            picker.setVersion(chosenDVer);
            //picker.setBoldFont(R.font.bold);
            //picker.setNormalFont(R.font.normal);
            picker.show(getSupportFragmentManager(), "test_date");
        });

        // Time Picker
        b.timePicker.setOnClickListener(v -> {
            TimePickerDialog picker = TimePickerDialog.newInstance((dialog, h, m, s) ->
                    Toast.makeText(this, z(h) + ":" + z(m) + ":" + z(s),
                            Toast.LENGTH_LONG).show());
            picker.setVersion(chosenTVer);
            picker.setBoldFont(R.font.bold);
            picker.setNormalFont(R.font.normal);
            picker.set24HourMode(is24HourMode);
            picker.enableSeconds(enableSeconds);
            picker.show(getSupportFragmentManager(), "test_time");
        });
    }

    String z(int n) {
        String s = Integer.toString(n);
        return s.length() == 1 ? ("0" + s) : s;
    }
}
