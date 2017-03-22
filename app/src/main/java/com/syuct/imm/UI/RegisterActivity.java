package com.syuct.imm.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.syuct.imm.utils.GlobalData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xcnana on 2015/1/3.
 */
public class RegisterActivity extends Activity {
    private EditText name;
    private EditText realName;
    private EditText password;
    private Date birthday;
    private String birthday2=null;
    private TextView datetext;
    private Button btnpost;
    private Button btncancel;
    private DatePickerDialog datePickerDialog;
    private Button buttondate;

    private int mYear;
    private int mMonth;
    private int mDay;


    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.btnpost:
                    Intent intentpost = new Intent(RegisterActivity.this, RegisterActivity.class);
                    startActivity(intentpost);
                    GlobalData.showToast(RegisterActivity.this, "登陆成功!!");
                    break;
                case R.id.btnCancel:
                    Intent cancelIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(cancelIntent);
                    //overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_top);
                    break;
                default:break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        buttondate=(Button)findViewById(R.id.datepicker);
        datetext=(TextView)findViewById(R.id.datetext);

        buttondate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth)
                    {
                        datetext.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
                birthday2=datetext.getText().toString();

            }
        });


        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String date="2015-05-12";
        try {
            Log.v("Before",date);
            if(birthday2!=null){
                birthday = sdf.parse(birthday2);
            }else{
                birthday = sdf.parse(date);
            }


            Log.v("After",birthday.toString());

        } catch (ParseException e) {
            datetext.setText("日期格式错误");
            e.printStackTrace();
        }

        btnpost=(Button)this.findViewById(R.id.btnpost);
        btnpost.setOnClickListener(onClickListener);
        btncancel=(Button)this.findViewById(R.id.btnCancel);
        btncancel.setOnClickListener(onClickListener);
        name=(EditText)this.findViewById(R.id.Tname);
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    Toast.makeText(RegisterActivity.this, "much", Toast.LENGTH_SHORT);
                }
            }
        });
        if(name.getText().equals("zhanglong")){
            Toast.makeText(RegisterActivity.this, "此名字忌讳", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        //overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }

}