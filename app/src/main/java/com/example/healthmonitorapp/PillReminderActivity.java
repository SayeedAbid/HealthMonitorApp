package com.example.healthmonitorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PillReminderActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    TextView dosetime;
    EditText name,note;
    String unit;
    Button savebtn;

    ArrayList<Medicine> arrayList;
    DatabaseHelper myDb;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_reminder);

//////dele this
//        Spinner instructionSpinner = findViewById(R.id.medicineInstructionSpinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.medicineInstruction,android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        instructionSpinner.setAdapter(adapter);
//        instructionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String instruction = adapterView.getItemAtPosition(i).toString();
//
//                //Toast.makeText(adapterView.getContext(),"Instruction "+ instruction, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


        Spinner medicineUnitSpinner = findViewById(R.id.medicineUnitSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.medicineUnit,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        medicineUnitSpinner.setAdapter(adapter2);
        medicineUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String medicineUnit = adapterView.getItemAtPosition(i).toString();
                unit = medicineUnit;

                //Toast.makeText(adapterView.getContext(),"Unit "+ medicineUnit, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ///////////////////////////////////////

        Calendar c =Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);



        ////////////////////////////////////////
        dosetime = (TextView) findViewById(R.id.medicineDoseTime);
        Button buttonTimePicker = findViewById(R.id.buttonpill);

        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        myDb = new DatabaseHelper(this);

        name = findViewById(R.id.medName);
        //unit = findViewById(R.id.medUnit);
        note = findViewById(R.id.medNote);
        savebtn=findViewById(R.id.saveMedicineButton);

        arrayList = new ArrayList<>();

//        if (name.getText().toString() == null)
//            Toast.makeText(this, "Fill all values", Toast.LENGTH_SHORT).show();
//        else if (unit == null)
//            Toast.makeText(this, "Fill all values", Toast.LENGTH_SHORT).show();
//        else if (dosetime.getText().toString() == null)
//            Toast.makeText(this, "Fill all values", Toast.LENGTH_SHORT).show();
//        else if (note.getText().toString() == null)
//            Toast.makeText(this, "Fill all values", Toast.LENGTH_SHORT).show();
//        else
            addData();



    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        updateTimeText(c);
        startAlarm(c);
    }

    private void updateTimeText(Calendar c) {
        String timeText = "Time : ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        dosetime.setText(timeText);
    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReciver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReciver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        dosetime.setText("Alarm canceled");
    }

    public void addData(){
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean cancel = false;

                if (TextUtils.isEmpty(name.getText().toString()))
                    cancel = true;
                else if (TextUtils.isEmpty(unit))
                    cancel = true;
                else if (TextUtils.isEmpty(dosetime.getText().toString()))
                    cancel = true;
                else if (TextUtils.isEmpty(note.getText().toString()))
                    cancel = true;


                if(cancel)
                    Toast.makeText(PillReminderActivity.this, "Fill all values", Toast.LENGTH_SHORT).show();
                else{
                    boolean isInserted = myDb.insertData(name.getText().toString(),
                            unit,dosetime.getText().toString(),note.getText().toString());
                    if (isInserted){
                        Toast.makeText(PillReminderActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(PillReminderActivity.this,ProfileActivity.class);
                        finish();
                        startActivity(intent);

//                   FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.nav_medicine,new MedicineFragment());
//                    ft.commit();

//                    Fragment fragment = new MedicineFragment();
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    FragmentTransaction ft = fragmentManager.beginTransaction();
//
//                    ft.replace(R.id.pill,fragment);
//                    ft.commit();
                    }
                    else
                        Toast.makeText(PillReminderActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                }




            }
        });
    }
}
