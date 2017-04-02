package com.android.scaffold.ui.main.presenter;

import android.content.Context;

import com.android.scaffold.db.entity.Contact;
import com.android.scaffold.ui.main.contract.ContactMainContract;
import com.android.scaffold.ui.main.model.ContactMainModel;

import java.util.List;

/**
 * Created by wuganlin on 2017/3/31.
 */

public class ContactMainPresenter implements ContactMainContract.Presenter {

    private ContactMainContract.Model mModel;
    private ContactMainContract.View mView;
    private Context mContext;

    public ContactMainPresenter(Context context, ContactMainContract.View view) {
        mContext = context;
        mModel = new ContactMainModel();
        mView = view;
    }

    @Override
    public void loadContacts() {
        List<Contact> contacts = mModel.getContacts();
        mView.showContacts(contacts);
    }
}
