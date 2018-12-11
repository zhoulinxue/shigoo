package com.xgsb.cashregister.customViews;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;

import com.xgsb.cashregister.R;
import com.xgsb.cashregister.R2;
import com.xgsb.cashregister.web.WebOperate;
import com.xgsb.cashregister.web.XgDAppBridge;
import com.xgsb.cashregister.web.onOperateLisenter;
import com.zx.datafactory.JSONManager;
import com.zx.mvplibrary.BaseCustomView;

import butterknife.BindView;
import io.starteos.dappsdk.DAppBridge;
import io.starteos.dappsdk.Request;
import io.starteos.dappsdk.Response;

/**
 * Name: WebChartView
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-28 10:18
 */
public class WebChartView extends BaseCustomView implements WebOperate {
    @BindView(R2.id.cashregister_xgsb_webview)
    WebView mWebView;
    private DAppBridge dAppBridge;
    private int width = 0;
    private int hight = 0;

    public WebChartView(Context context, ViewGroup rootGroup) {
        super(context, rootGroup);
    }


    public WebChartView(Context context, ViewGroup rootGroup, onOperateLisenter lisenter, Handler handler) {
        this(context, rootGroup);
        dAppBridge = new XgDAppBridge(getContext(), mWebView, lisenter, handler);
    }

    public WebChartView(Context context) {
        super(context);
    }

    @Override
    protected void initView(Context context, View rootView) {
        mWebView.setWebContentsDebuggingEnabled(true);
        mWebView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mWebView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                width = mWebView.getMeasuredWidth();
                hight = mWebView.getMeasuredHeight();
            }
        });


    }

    @Override
    public int initLayout() {
        return R.layout.cashregister_web_chart_layout;
    }

    @Override
    public void reload() {
        mWebView.clearHistory();
        mWebView.clearCache(true);
        mWebView.clearFormData();
        mWebView.reload();
    }

    @Override
    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    @Override
    public void refresh(String action) {
        mWebView.evaluateJavascript("javascript:reData(" + JSONManager.getInstance().toJson(action) + ")", null);
    }

    public void loadDefaultUrl() {
        loadUrl("http://www.kx910.com/webView/index.html#/");
    }

    public int getWidth() {
        return width;
    }

    public int getHight() {
        return hight;
    }

    public void callback(Request request, Response response) {
        dAppBridge.callback(request, response);
    }

    public void setLisenter(onOperateLisenter mLisenter) {
        ((XgDAppBridge) dAppBridge).setLisenter(mLisenter);
    }


}
