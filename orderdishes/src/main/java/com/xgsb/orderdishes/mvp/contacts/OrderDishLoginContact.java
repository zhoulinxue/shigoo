package com.xgsb.orderdishes.mvp.contacts;


import com.zx.api.api.mvp.BaseView;
import com.zx.datafactory.bean.User;

/**
 * Name: OrderDishLoginContact
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-12 14:29
 */
public class OrderDishLoginContact {
    public interface presenter {
        void cashLogin(User user);
    }

    public interface view extends BaseView {
        void loginCallbak(User user);
    }

}
