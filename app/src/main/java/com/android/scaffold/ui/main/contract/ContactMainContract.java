package com.android.scaffold.ui.main.contract;

import com.android.scaffold.db.entity.Contact;

import java.util.List;

import rx.Observable;

/**
 * Created by wuganlin on 2017/3/31.
 */

public interface ContactMainContract {
    interface Model {
        List<Contact> getContacts();
    }

    interface View {
        void showContacts(List<Contact> contacts);
    }

    interface Presenter {
        void loadContacts();
    }
}
