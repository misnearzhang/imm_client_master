package com.syuct.imm.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.syuct.imm.ui.R;

/**
 * Created by zhanglong on 4/11/15.
 */
public class UserDetailActivity extends Activity{

    private Button close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        close=(Button)findViewById(R.id.closeButton);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                //overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_bottom);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_bottom);
    }
}
