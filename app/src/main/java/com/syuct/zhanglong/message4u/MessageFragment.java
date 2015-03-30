package com.syuct.zhanglong.message4u;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MessageFragment extends Fragment {

    private WebView webView;
    private View view;
    private ProgressBar progress;
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


        webView =(WebView)view.findViewById(R.id.web);
        webView.setEnabled(true);
        webView.setNetworkAvailable(true);
        webView.setAlwaysDrawnWithCacheEnabled(true);

        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebChromeClient(new mychromeClient());
        webView.setWebViewClient(new myWebview());
        webView.loadUrl("http://192.168.1.132:8080/WebRoot/");
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

