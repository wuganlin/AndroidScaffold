package com.android.scaffold.ui.main.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.scaffold.R;
import com.android.scaffold.db.entity.Contact;
import com.android.scaffold.ui.main.contract.ContactMainContract;
import com.android.scaffold.ui.main.presenter.ContactMainPresenter;
import com.wgl.android.library.base.BaseActivity;

import java.util.List;

public class MainActivity extends BaseActivity implements ContactMainContract.View {
    private ContactMainContract.Presenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        //mPresenter.loadContacts();
        showNetErrorTip("wuganlin");
    }

    @Override
    public void showContacts(List<Contact> contacts) {

    }
}
