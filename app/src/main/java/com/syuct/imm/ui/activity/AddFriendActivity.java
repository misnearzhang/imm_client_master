package com.syuct.imm.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.syuct.imm.ui.R;

/**
 * Created by zhanglong on 5/31/15.
 */
public class AddFriendActivity extends Activity {
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriend);
        backButton = (Button) this.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                //overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
            }
        });
    }
}
