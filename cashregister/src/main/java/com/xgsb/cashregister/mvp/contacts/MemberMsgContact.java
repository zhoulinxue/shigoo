package com.xgsb.cashregister.mvp.contacts;

import com.zx.datafactory.bean.Countbean;
import com.zx.datafactory.bean.Member;
import com.zx.datafactory.bean.MemberLevel;
import com.zx.api.api.mvp.BaseView;

import java.util.List;

/**
 * Name: MemberMsgContact
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-06 14:43
 */
public class MemberMsgContact {
    public interface presenter {
        void addMember(String... params);

        void updateMember(String...params);

        void getMemberLevelList(String... params);
    }

    public interface view extends BaseView {
        void onAddSuc(String msg);

        void onUpdateSuc(String msg);

        void onLevelList(List<MemberLevel> Levels);
    }
}
