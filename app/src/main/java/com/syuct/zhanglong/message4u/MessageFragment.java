package com.syuct.zhanglong.message4u;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.syuct.zhanglong.Utils.GlobalData;


public class MessageFragment extends Fragment {

    private WebView webView;
    private View view;
    private ProgressBar progress;
    private Button reload;
    private Button forward;
    private Button back;

    @Override
	public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.message_fragment,null);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        progress=(ProgressBar)view.findViewById(R.id.pb);
        progress.setMax(100);
        reload = (Button) view.findViewById(R.id.reload);



        webView =(WebView)view.findViewById(R.id.web);
        webView.setEnabled(true);
        webView.setNetworkAvailable(true);
        webView.setAlwaysDrawnWithCacheEnabled(true);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebChromeClient(new mychromeClient());
        webView.setWebViewClient(new myWebview());
        webView.loadUrl("http://" + GlobalData.getIPaddress() + ":8080/message4U/");
        webView.setDrawingCacheEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
                GlobalData.showToast(getActivity().getApplicationContext(), "刷新成功!" + webView.getUrl());
            }
        });
    }
    private class mychromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progress.setProgress(newProgress);
            if(newProgress==100){
                progress.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }

    }
    private class myWebview extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    }
}

