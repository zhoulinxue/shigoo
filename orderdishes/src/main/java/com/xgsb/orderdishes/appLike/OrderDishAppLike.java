package com.xgsb.orderdishes.appLike;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.applicationlike.RegisterCompManual;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.xgsb.orderdishes.serviceImpl.TablesFragmentServiceImpl;
import com.zx.api.componentservice.TablesService;

/**
 * Name: OrderDishAppLike
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-07 14:24
 */
@RegisterCompManual
public class OrderDishAppLike implements IApplicationLike {
    Router mRouter = Router.getInstance();
    UIRouter mUiRouter = UIRouter.getInstance();
    @Override
    public void onCreate() {
        mUiRouter.registerUI("ordersheet");
        mRouter.addService(TablesService.class.getSimpleName(),new TablesFragmentServiceImpl());
    }

    @Override
    public void onStop() {
        mUiRouter.unregisterUI("ordersheet");
        mRouter.removeService(TablesService.class.getSimpleName());
    }
}
