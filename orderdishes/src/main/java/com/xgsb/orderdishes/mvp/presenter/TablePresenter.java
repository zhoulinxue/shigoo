package com.xgsb.orderdishes.mvp.presenter;

import com.xgsb.orderdishes.mvp.contacts.TableContact;
import com.zx.mvplibrary.presenter.BasePresenterImpl;

/**
 * Name: TablePresenter
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-11 13:18
 */
public class TablePresenter extends BasePresenterImpl<TableContact.view> implements TableContact.presenter {
    public TablePresenter(TableContact.view view) {
        super(view);
    }

    @Override
    public void getTableList(String... params) {

    }
}
