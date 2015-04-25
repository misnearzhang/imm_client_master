package com.syuct.zhanglong.message4u;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.syuct.zhanglong.Utils.GlobalData;

/**
 * Created by zhanglong on 5/2/15.
 */
public class SendMessageActivity extends Activity {
    private TextView textView;
    private EditText msgEditor;
    private Button backButton;
    private String tips;
    private Button send;
    private Button clear;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmessage);
        Bundle data = getIntent().getExtras();
        tips = data.getString("friendAccount");


        textView = (TextView) this.findViewById(R.id.tips);
        msgEditor = (EditText) this.findViewById(R.id.msgeditor);
        backButton = (Button) this.findViewById(R.id.backButton);
        send = (Button) this.findViewById(R.id.send);
        clear = (Button) this.findViewById(R.id.clear);

        textView.setTextSize(20f);
        textView.setText("正在向" + tips + "发送信息!");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 发送信息
                 */
                GlobalData.showToast(SendMessageActivity.this, "成功发送信息");
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 清除信息
                 */
                msgEditor.setText("");
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
    }
}
