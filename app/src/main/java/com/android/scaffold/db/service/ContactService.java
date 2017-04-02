package com.android.scaffold.db.service;

import android.content.Context;

import com.android.scaffold.db.dao.base.BaseDao;
import com.android.scaffold.db.entity.Contact;


/**
 * Created by wuganlin on 2017/3/31.
 */

public class ContactService extends BaseDao<Contact, Long> {

    public ContactService(Context context) {
        super(context);
    }


}
