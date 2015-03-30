package com.syuct.zhanglong.message4u;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.syuct.zhanglong.Utils.GlobalData;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by xcnana on 2015/1/3.
 */
public class register_activity extends Activity {
    private EditText name;
    private EditText realName;
    private EditText password;
    private static Date birthday;
    private Button btnpost;
    private Button btncancel;


    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.btnpost:
                    Intent intentpost = new Intent(register_activity.this, register_activity.class);
                    startActivity(intentpost);
                    GlobalData.showToast(register_activity.this, "登陆成功!!");
                    break;
                case R.id.btnCancel:
                    Intent cancelIntent = new Intent(register_activity.this, login_activity.class);
                    startActivity(cancelIntent);
                    overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_top);
                    break;
                default:break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        btnpost=(Button)this.findViewById(R.id.btnpost);
        btnpost.setOnClickListener(onClickListener);
        btncancel=(Button)this.findViewById(R.id.btnCancel);
        btncancel.setOnClickListener(onClickListener);
        name=(EditText)this.findViewById(R.id.Tname);
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    Toast.makeText(register_activity.this, "much", Toast.LENGTH_SHORT);
                }

            }
        });
        if(name.getText().equals("zhanglong")){
            Toast.makeText(register_activity.this, "", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void showDatePickerDialog(View view){
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.show(getFragmentManager(), "datePicker");
    }

public static class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setShowsDialog(true);
            }
        });
    }
}

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}