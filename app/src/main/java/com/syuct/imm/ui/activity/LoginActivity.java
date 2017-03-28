package com.syuct.imm.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.syuct.imm.asynctask.LoginAsyncTask;
import com.syuct.imm.core.io.CacheToolkit;
import com.syuct.imm.ui.R;
import com.syuct.imm.utils.GlobalData;
import com.syuct.imm.bean.Login;
import com.syuct.imm.utils.okhttp.https.HttpsUtils;


public class LoginActivity extends SuperActivity {
    static long back_pressed;
    boolean bRemPass = false;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgRemPass:
                case R.id.lblRemPass:
                    bRemPass = !bRemPass;
                    if (bRemPass) {
                        imgRemPass.setImageResource(R.drawable.checked);
                        GlobalData.SetPassFlag(LoginActivity.this, bRemPass);
                    } else
                        imgRemPass.setImageResource(R.drawable.unchecked);
                    break;
                case R.id.lblDisPass:
                    break;
                case R.id.btnRegister:
                    Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivityForResult(intentRegister, 0);
                    //overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
//                    startActivity(intentRegister);
                    break;
                case R.id.btnLogin:
                    String UserAccountOrPhoneNumber = txtUserName.getText().toString();
                    /*String connection = txtConnect.getText().toString();*/
                    /*if (connection.length() == 0) {
                        GlobalData.showToast(LoginActivity.this,
                                getString(R.string.disconnect));
                        return;
                    } else {
                        GlobalData.setIPaddress(connection);
                    }*/
                    if (UserAccountOrPhoneNumber.length() == 0) {
                        GlobalData.showToast(LoginActivity.this,
                                getString(R.string.inputusername));
                        return;
                    }
                    String Password = txtPass.getText().toString();
                    if (Password.length() == 0) {
                        GlobalData.showToast(LoginActivity.this,
                                getString(R.string.inputpassword));
                        return;
                    }

                    if (GlobalData.isOnline(LoginActivity.this) == true) {
                        Login login = new Login();
                        login.setUserAccount(UserAccountOrPhoneNumber);
                        login.setPassword(Password);
                        LoginAsyncTask task=new LoginAsyncTask(getApplicationContext());
                        task.execute(login);
                    } else {
                        GlobalData.showToast(LoginActivity.this, "断网了!");
                    }
                    break;
            }
        }
    };
    ImageView imgRemPass = null;
    TextView lblRemPass = null;
    EditText txtUserName = null;
    EditText txtPass = null;
    EditText txtConnect = null;
    TextView lblForgetPass = null;
    Button btnRegister = null;
    Button btnLogin = null;
    ProgressDialog dialog=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        GlobalData.setLoginContext(LoginActivity.this);
        initControl();
    }

    private void initControl() {
        bRemPass = GlobalData.GetPassFlag(LoginActivity.this);
        imgRemPass = (ImageView) findViewById(R.id.imgRemPass);
        if (bRemPass)
            imgRemPass.setImageResource(R.drawable.checked);
        else
            imgRemPass.setImageResource(R.drawable.unchecked);
        imgRemPass.setOnClickListener(onClickListener);
        lblRemPass = (TextView) findViewById(R.id.lblRemPass);
        lblRemPass.setOnClickListener(onClickListener);
        lblForgetPass = (TextView) findViewById(R.id.lblDisPass);
        lblForgetPass.setOnClickListener(onClickListener);

        txtUserName = (EditText) findViewById(R.id.txtUserID);
        txtPass = (EditText) findViewById(R.id.txtPassword);
        txtConnect = (EditText) findViewById(R.id.connect);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(onClickListener);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(onClickListener);
        if (GlobalData.GetPassFlag(LoginActivity.this)) {
            txtUserName.setText(GlobalData.GetUserName(LoginActivity.this));
            txtPass.setText(GlobalData.GetPass(LoginActivity.this));
        } else {
            txtUserName.setText("");
            txtPass.setText("");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            GlobalData.showToast(LoginActivity.this, getString(R.string.exitapp));
            back_pressed = System.currentTimeMillis();
        }
    }

}

