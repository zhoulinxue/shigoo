package com.xgsb.cashregister.mvp.contacts;

import com.zx.datafactory.bean.ConsumeListData;
import com.zx.datafactory.bean.Consumebean;
import com.zx.datafactory.bean.ReChargebean;
import com.zx.api.api.mvp.BaseView;

import java.util.List;

/**
 * Name: MemberManageContact
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-23 14:05
 */
public class ConsumeContact {
    public interface presenter {
        void getConsumeList(String... params);
    }

    public interface view extends BaseView {
        void onGetConsumeListCallback(ConsumeListData list);
    }
}
