package com.android.scaffold.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.scaffold.db.entity.Contact;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by wuganlin on 2017/3/23.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static DatabaseHelper instance;

    private static final String NAME = "ormlite_app.db";

    private static final Integer VERSION = 1;


    private DatabaseHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    public static DatabaseHelper getHelper(Context context) {
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null) {
                    instance = new DatabaseHelper(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Contact.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.e("onUpgrade", "oldVersion=" + oldVersion + ",newVersion=" + newVersion);
        switch (oldVersion) {
            case 1:

        }
    }


}
