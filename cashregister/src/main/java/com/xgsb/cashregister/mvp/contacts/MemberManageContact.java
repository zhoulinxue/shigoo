package com.xgsb.cashregister.mvp.contacts;

import com.zx.datafactory.bean.Countbean;
import com.zx.datafactory.bean.Member;
import com.zx.datafactory.bean.MemberLevel;
import com.zx.datafactory.bean.MemberReChargeGive;
import com.zx.datafactory.bean.ReChargebean;
import com.zx.datafactory.bean.User;
import com.zx.api.api.mvp.BaseView;

import java.util.List;

/**
 * Name: MemberManageContact
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-23 14:05
 */
public class MemberManageContact {
    public interface presenter {
        void getMemberList(String... params);

        void searchMembers(String... params);

        void getCountData(String... params);

        void addmember(String... params);

        void getMemberLevelList(String... params);

        void getMemberData(String... params);
    }

    public interface view extends BaseView {
        void onGetMenbersCallback(List<Member> list);

        void onSearchResult(List<Member> list);

        void onResult(Countbean countbean);

        void onAddSuc(String msg);

        void onMemberList(List<MemberLevel> Levels);

        void onMemberResult(Member member);

    }
}
