package com.android.scaffold.ui.main.model;

import com.android.scaffold.db.entity.Contact;
import com.android.scaffold.ui.main.contract.ContactMainContract;

import java.util.List;

/**
 * Created by wuganlin on 2017/3/31.
 */

public class ContactMainModel implements ContactMainContract.Model {
    @Override
    public List<Contact> getContacts() {
        return null;
    }
}
