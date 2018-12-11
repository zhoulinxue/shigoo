package com.xgsb.cashregister.web;

import com.xgsb.cashregister.fragments.MemberListFragement;
import com.zx.api.api.utils.AppLog;

import io.starteos.dappsdk.DAppApi;
import io.starteos.dappsdk.DAppBridge;
import io.starteos.dappsdk.Request;
import io.starteos.dappsdk.annotation.Namespace;

@Namespace(WebNameSpace.SYSTEM)
public class WebBridge extends DAppApi {
    public WebBridge(DAppBridge bridge) {
        super(bridge);
    }

    @Override
    protected void onDestroy() {

    }

    /**
     * 获取当前 页面信息
     *
     * @param request
     */
    public void getTableInfo(final Request request) {
        AppLog.print("WebBridge  " + "getTableInfo");
        if (bridge instanceof XgDAppBridge) {
            ((XgDAppBridge) bridge).getTableInfo(request);
        }
    }

    public void searchOperate(Request request) {
        AppLog.print(MemberListFragement.class.getSimpleName() + "    searchOperate  "
                + request.getParams().optString("button") + "\n"
                + request.getParams().optString("page"));
        if (bridge instanceof XgDAppBridge) {
            ((XgDAppBridge) bridge).searchOperate(request);
        }
    }

    public void operateHandle(Request request) {
        AppLog.print(MemberListFragement.class.getSimpleName() + "    operateHandle  \n" +
                request.getParams().optString("page") + " \n " +
                request.getParams().optString("method") + " \n" +
                request.getParams().optString("row_data"));
        if (bridge instanceof XgDAppBridge) {
            ((XgDAppBridge) bridge).onOperate(request);
        }
    }
}
