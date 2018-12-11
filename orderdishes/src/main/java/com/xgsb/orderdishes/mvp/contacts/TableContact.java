package com.xgsb.orderdishes.mvp.contacts;

import com.zx.datafactory.bean.Table;
import com.zx.api.api.mvp.BaseView;

import java.util.List;

/**
 * Name: TableContact
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-11 13:18
 */
public class TableContact {
    public interface presenter {
        void getTableList(String... params);
    }

    public interface view extends BaseView {
        void onTableResult(List<Table> tables);
    }
}
