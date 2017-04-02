package com.android.scaffold.http.response.base;

/**
 * Created by wuganlin on 2017/4/1.
 */

public class JSONResult<T> {
    public Integer errorcode;
    public String errormsg;
    public T data;
}
