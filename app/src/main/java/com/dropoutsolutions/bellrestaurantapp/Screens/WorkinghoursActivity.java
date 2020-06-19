package com.dropoutsolutions.bellrestaurantapp.Screens;
import androidx.appcompat.app.AppCompatActivity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dropoutsolutions.bellrestaurantapp.R;

import java.util.Calendar;

public class WorkinghoursActivity extends AppCompatActivity {

    int hour , min ;
    private TextView time ;
    private Button next ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workinghours);
        ImageView timepicker = findViewById(R.id.timeimage);
        time = findViewById(R.id.time);
        next = findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorkinghoursActivity.this , MapActivity.class));
            }
        });
        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(WorkinghoursActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                hour = hourOfDay ;
                                min = minute ;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0 , 0 , 0 ,hour , min);
                                time.setText(DateFormat.format("hh:mm aa" , calendar));
                            }
                        } , 12 , 0 , false);

                timePickerDialog.updateTime(hour ,min);
                timePickerDialog.show();
            }

        });


    }
}