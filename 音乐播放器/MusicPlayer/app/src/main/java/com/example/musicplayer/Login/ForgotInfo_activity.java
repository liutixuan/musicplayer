package com.example.musicplayer.Login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.musicplayer.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ForgotInfo_activity extends AppCompatActivity {
    private EditText myName;
    private EditText myPwd;
    private EditText myRePwd;
    private ToggleButton mySex;
    private EditText myBirth;
    private Spinner myDegree;
    private CheckBox myAccept;
    private Button mySubmit;
    private TextView txtPrint;
    private int myYear,myMonth,myDay;
    private String myDegreeTemp;
    private String msg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotinfo_activity);
        findViews();

        myBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new
                        DatePickerDialog(ForgotInfo_activity.this,DateListener,myYear,myMonth,myDay);
                datePickerDialog.show();
            }
        });
        Spinner.OnItemSelectedListener listener=new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                myDegreeTemp=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                myDegreeTemp="未知";
            }
        };
        myDegree.setOnItemSelectedListener(listener);
    }


    private void findViews(){
        myName=(EditText)findViewById(R.id.edtName);
        myPwd=(EditText)findViewById(R.id.edtPwd);
        myRePwd=(EditText)findViewById(R.id.edtRePwd);
        mySex=(ToggleButton)findViewById(R.id.toggleButtonSex);
        myBirth=(EditText)findViewById(R.id.edtBirth);
        myDegree=(Spinner)findViewById(R.id.spinnerDegree);
        myAccept=(CheckBox)findViewById(R.id.checkBoxAccept);
        mySubmit=(Button)findViewById(R.id.buttonSubmit);
//初始化日历
        Calendar calendar=Calendar.getInstance(Locale.CHINA);
        Date date=new Date();
        calendar.setTime(date);
        myYear=calendar.get(Calendar.YEAR)-20;
        myMonth=calendar.get(Calendar.MONTH);;
        myDay=calendar.get(Calendar.DAY_OF_MONTH);
    }
    private DatePickerDialog.OnDateSetListener DateListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myYear=year;
            myMonth=month;
            myDay=dayOfMonth;
            myBirth.setText(myYear+"年"+(myMonth+1)+"月"+myDay+"日");
        }
    };
    public void onCheckBoxClick(View view){
        if(myAccept.isChecked()){
            if(isValue(myPwd))
                mySubmit.setEnabled(true);
            else
                myAccept.setChecked(false);
        }
        else
            mySubmit.setEnabled(false);
    }
    private boolean isValue(EditText editText){
        String pwd=myPwd.getText().toString();
        String repwd=myRePwd.getText().toString();
        if(!repwd.equals(pwd)){
            myRePwd.setError("两次输入不一致！");
            return false;
        }
        else
            return true;
    }
    public void onSubmitClick(View view){
        Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ForgotInfo_activity.this, Login_Activity.class);
        startActivity(intent);
    }
}
