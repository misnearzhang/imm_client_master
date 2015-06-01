package com.syuct.zhanglong.message4u;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.syuct.zhanglong.AsyncTask.LoginAsyncTask;
import com.syuct.zhanglong.Utils.GlobalData;
import com.syuct.zhanglong.bean.Login;


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
                    overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
//                    startActivity(intentRegister);
                    break;
                case R.id.btnLogin:
                    String UserAccountOrPhoneNumber = txtUserName.getText().toString();
                    String connection = txtConnect.getText().toString();
                    LoginAsyncTask loginAsyncTask = new LoginAsyncTask();
                    if (connection.length() == 0) {
                        GlobalData.showToast(LoginActivity.this,
                                getString(R.string.disconnect));
                        return;
                    } else {
                        GlobalData.setIPaddress(connection);
                    }
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
                        //loginAsyncTask.execute(login);

                        //httpRequest.login(UserAccountOrPhoneNumber, Password);

                        if (true) {

                            Intent intentLogin = new Intent(LoginActivity.this, IndexActivity.class);
                            intentLogin.putExtra("name", new String("根据世界军力排名网“全球火力”(Global Firepower)" +
                                    "公布的世界最新军事力量排名，俄罗斯军队在世界最强军队排名榜上仅次于美国，位居第二位。" +
                                    "美军仍位居世界上最强军队榜首，而中国位居第三位，之后依次是印度、英国、法国、德国、" +
                                    "土耳其、韩国。日本位居第10位。"));

                            startActivity(intentLogin);
                            overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_fade_out);
                            finish();

                        } else {
                            GlobalData.showToast(LoginActivity.this, "账号或密码错误!");
                        }
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
        if (GlobalData.GetPassFlag(LoginActivity.this)) {
            txtUserName.setText(GlobalData.GetUserName(LoginActivity.this));
            txtPass.setText(GlobalData.GetPass(LoginActivity.this));
        } else {
            txtUserName.setText("");
            txtPass.setText("");
        }

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(onClickListener);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(onClickListener);
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

