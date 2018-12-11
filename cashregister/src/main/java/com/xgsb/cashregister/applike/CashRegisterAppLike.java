package com.xgsb.cashregister.applike;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.applicationlike.RegisterCompManual;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.component.componentlib.router.ui.UIRouter;


/**
 * Name: OrderSheetAppLike
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-07 14:24
 */
@RegisterCompManual
public class CashRegisterAppLike implements IApplicationLike {
    Router mRouter = Router.getInstance();
    UIRouter mUiRouter = UIRouter.getInstance();

    @Override
    public void onCreate() {
        mUiRouter.registerUI("cashregister");
//        mRouter.addService(TablesService.class.getSimpleName(), new TablesFragmentServiceImpl());
    }

    @Override
    public void onStop() {
        mUiRouter.unregisterUI("cashregister");
//        mRouter.removeService(TablesService.class.getSimpleName());
    }
}
