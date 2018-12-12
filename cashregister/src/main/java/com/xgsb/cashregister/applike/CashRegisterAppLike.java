package com.xgsb.cashregister.applike;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.applicationlike.RegisterCompManual;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.xgsb.cashregister.fragments.MemberDetailFragment;
import com.xgsb.cashregister.serviceImpl.AddCardFragmentServiceImpl;
import com.xgsb.cashregister.serviceImpl.MemberDetailFragmentServiceImpl;
import com.zx.api.componentservice.AddCardFragmentService;
import com.zx.api.componentservice.TablesService;


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
        mRouter.addService(MemberDetailFragment.class.getSimpleName(), new MemberDetailFragmentServiceImpl());
        mRouter.addService(AddCardFragmentService.class.getSimpleName(), new AddCardFragmentServiceImpl());
    }

    @Override
    public void onStop() {
        mUiRouter.unregisterUI("cashregister");
        mRouter.removeService(MemberDetailFragment.class.getSimpleName());
        mRouter.removeService(AddCardFragmentService.class.getSimpleName());
    }
}
