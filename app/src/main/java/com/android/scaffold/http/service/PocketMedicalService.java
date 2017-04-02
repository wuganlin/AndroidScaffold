package com.android.scaffold.http.service;

import com.android.scaffold.db.entity.Contact;
import com.android.scaffold.http.response.base.JSONResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wuganlin on 2017/4/1.
 */

public interface PocketMedicalService {
    @GET("v1/user/getContact")
    Observable<JSONResult<Contact>> getContact(@Query("uid") Long uid);
}
