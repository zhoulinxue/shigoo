package com.zx.mvplibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.api.api.app.ICustomeView;

import butterknife.ButterKnife;

/**
 * Name: BaseCustomView
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-26 13:28
 */
public abstract class BaseCustomView implements ICustomeView {
    private View rootView;
    protected Context mContext;
    private ViewGroup rootGroup;

    public BaseCustomView(Context context, ViewGroup rootGroup) {
        this.rootGroup = rootGroup;
        this.mContext = context;
        rootView = LayoutInflater.from(mContext).inflate(initLayout(), rootGroup);
        ButterKnife.bind(this, rootView);
        initView(context, rootView);
    }

    public BaseCustomView(Context context) {
        this(context, null);
    }

    protected abstract void initView(Context context, View rootView);


    @Override
    public View getView() {
        return rootView;
    }

    @Override
    public void gone() {
        getView().setVisibility(View.GONE);
    }

    @Override
    public void visiable() {
        getView().setVisibility(View.VISIBLE);
    }

    @Override
    public Context getContext() {
        return mContext;
    }
}
