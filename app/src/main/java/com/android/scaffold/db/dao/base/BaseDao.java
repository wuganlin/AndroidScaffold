package com.android.scaffold.db.dao.base;

import android.content.Context;

import com.android.scaffold.db.DatabaseHelper;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.*;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.DatabaseConnection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 数据库CRUD操作的Dao，子类继承实现抽象方法，也提供一个简单的泛型实现类
 * T 实体类
 * D id类型
 */
public class BaseDao<T, ID> {
    private OrmLiteSqliteOpenHelper mDatabaseHelper;
    private Context mContext;
    private Dao<T, ID> mDao;

    public BaseDao(Context context) {
        if (context == null) {
            //如果为空，则扔出非法参数异常
            throw new IllegalArgumentException("Context can't be null!");
        }
        //避免产生内存泄露，使用getApplicationContext()
        mContext = context.getApplicationContext();
        //获得单例helper
        mDatabaseHelper = DatabaseHelper.getHelper(mContext);
        /**
         * 获取泛型类型
         */
        Type type = getClass().getGenericSuperclass();
        Class<T> clazz = null;
        if (type instanceof ParameterizedType) {
            clazz = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
        }
        try {
            mDao = mDatabaseHelper.getDao(clazz);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提供Dao
     *
     * @return Dao类
     */
    public Dao<T, ID> getDao() {
        return mDao;
    }

    /**
     * 增，带事务操作
     *
     * @param t 泛型实体类
     * @return 影响的行数
     * @throws SQLException SQLException异常
     */
    public int create(T t) throws SQLException {
        Dao<T, ID> dao = getDao();

        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            int create = dao.create(t);
            dao.commit(databaseConnection);
            return create;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 增或更新，带事务操作
     *
     * @param t 泛型实体类
     * @return Dao.CreateOrUpdateStatus
     * @throws SQLException SQLException异常
     */
    public Dao.CreateOrUpdateStatus createOrUpdate(T t) throws SQLException {
        Dao<T, ID> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            Dao.CreateOrUpdateStatus orUpdate = dao.createOrUpdate(t);
            dao.commit(databaseConnection);
            return orUpdate;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return null;
    }

    /**
     * 增，带事务操作
     *
     * @param t 泛型实体类集合
     * @return 影响的行数
     * @throws SQLException SQLException异常
     */
    public int create(List<T> t) throws SQLException {
        Dao<T, ID> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            for (T item : t) {
                dao.create(item);
            }
            dao.commit(databaseConnection);
            return t.size();
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 删，带事务操作
     *
     * @param t 泛型实体类
     * @return 影响的行数
     * @throws SQLException SQLException异常
     */
    public int delete(T t) throws SQLException {
        Dao<T, ID> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            int delete = dao.delete(t);
            dao.commit(databaseConnection);
            return delete;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 删，带事务操作
     *
     * @param list 泛型实体类集合
     * @return 影响的行数
     * @throws SQLException SQLException异常
     */
    public int delete(List<T> list) throws SQLException {
        Dao<T, ID> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            int delete = dao.delete(list);
            dao.commit(databaseConnection);
            return delete;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 删，带事务操作
     *
     * @param columnNames  列名数组
     * @param columnValues 列名对应值数组
     * @return 影响的行数
     * @throws SQLException              SQLException异常
     * @throws InvalidParameterException InvalidParameterException异常
     */
    public int delete(String[] columnNames, Object[] columnValues) throws SQLException, InvalidParameterException {
        List<T> list = query(columnNames, columnValues);
        if (null != list && !list.isEmpty()) {
            Dao<T, ID> dao = getDao();
            DatabaseConnection databaseConnection = null;
            try {
                databaseConnection = dao.startThreadConnection();
                dao.setAutoCommit(databaseConnection, false);
                int delete = dao.delete(list);
                dao.commit(databaseConnection);
                return delete;
            } catch (SQLException e) {
                dao.rollBack(databaseConnection);
                e.printStackTrace();
            } finally {
                dao.endThreadConnection(databaseConnection);
            }
        }
        return 0;
    }

    /**
     * 删，带事务操作
     *
     * @param id id值
     * @return 影响的行数
     * @throws SQLException SQLException异常
     */
    public int deleteById(ID id) throws SQLException {
        Dao<T, ID> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            int delete = dao.deleteById(id);
            dao.commit(databaseConnection);
            return delete;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 删，带事务操作
     *
     * @param ids id集合
     * @return 影响的行数
     * @throws SQLException SQLException异常
     */
    public int deleteByIds(List<ID> ids) throws SQLException {
        Dao<T, ID> dao = getDao();

        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            int delete = dao.deleteIds(ids);
            dao.commit(databaseConnection);
            return delete;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 删，带事务操作
     *
     * @param preparedDelete PreparedDelete类
     * @return 影响的行数
     * @throws SQLException SQLException异常
     */
    public int delete(PreparedDelete<T> preparedDelete) throws SQLException {
        Dao<T, ID> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            int delete = dao.delete(preparedDelete);
            dao.commit(databaseConnection);
            return delete;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 改，带事务操作
     *
     * @param t 泛型实体类
     * @return 影响的行数
     * @throws SQLException SQLException异常
     */
    public int update(T t) throws SQLException {
        Dao<T, ID> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            int update = dao.update(t);
            dao.commit(databaseConnection);
            return update;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 改，带事务操作
     *
     * @param preparedUpdate PreparedUpdate对象
     * @return 影响的行数
     * @throws SQLException SQLException异常
     */
    public int update(PreparedUpdate<T> preparedUpdate) throws SQLException {
        Dao<T, ID> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            int update = dao.update(preparedUpdate);
            dao.commit(databaseConnection);
            return update;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 查，带事务操作
     *
     * @return 查询结果集合
     * @throws SQLException SQLException异常
     */
    public List<T> queryAll() throws SQLException {
        Dao<T, ID> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            List<T> query = dao.queryForAll();
            dao.commit(databaseConnection);
            return query;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return null;
    }

    /**
     * 查，带事务操作
     *
     * @param preparedQuery PreparedQuery对象
     * @return 查询结果集合
     * @throws SQLException SQLException异常
     */
    public List<T> query(PreparedQuery<T> preparedQuery) throws SQLException {
        Dao<T, ID> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            List<T> query = dao.query(preparedQuery);
            dao.commit(databaseConnection);
            return query;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return null;
    }

    /**
     * 查，带事务操作
     *
     * @param columnName  列名
     * @param columnValue 列名对应值
     * @return 查询结果集合
     * @throws SQLException SQLException异常
     */
    public List<T> query(String columnName, String columnValue) throws SQLException {
        QueryBuilder<T, ID> queryBuilder = getDao().queryBuilder();
        queryBuilder.where().eq(columnName, columnValue);
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        Dao<T, ID> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            List<T> query = dao.query(preparedQuery);
            //also can use dao.queryForEq(columnName,columnValue);
            dao.commit(databaseConnection);
            return query;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return null;
    }

    /**
     * 查，带事务操作
     *
     * @param columnNames
     * @param columnValues
     * @return 查询结果集合
     * @throws SQLException SQLException异常
     */
    public List<T> query(String[] columnNames, Object[] columnValues) throws SQLException {
        if (columnNames.length != columnNames.length) {
            throw new InvalidParameterException("params size is not equal");
        }
        QueryBuilder<T, ID> queryBuilder = getDao().queryBuilder();
        Where<T, ID> wheres = queryBuilder.where();
        for (int i = 0; i < columnNames.length; i++) {
            if (i == 0) {
                wheres.eq(columnNames[i], columnValues[i]);
            } else {
                wheres.and().eq(columnNames[i], columnValues[i]);
            }

        }
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();

        Dao<T, ID> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            List<T> query = dao.query(preparedQuery);
            dao.commit(databaseConnection);
            return query;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return null;
    }

    /**
     * 查，带事务操作
     *
     * @param map 列名与值组成的map
     * @return 查询结果集合
     * @throws SQLException SQLException异常
     */
    public List<T> query(Map<String, Object> map) throws SQLException {
        QueryBuilder<T, ID> queryBuilder = getDao().queryBuilder();
        if (!map.isEmpty()) {
            Where<T, ID> wheres = queryBuilder.where();
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
            String key = null;
            Object value = null;
            for (int i = 0; iterator.hasNext(); i++) {
                Map.Entry<String, Object> next = iterator.next();
                key = next.getKey();
                value = next.getValue();
                if (i == 0) {
                    wheres.eq(key, value);
                } else {
                    wheres.and().eq(key, value);
                }
            }
        }
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        Dao<T, ID> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            List<T> query = dao.query(preparedQuery);
            dao.commit(databaseConnection);
            return query;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return null;
    }

    /**
     * 查，带事务操作
     *
     * @param id id值
     * @return 查询结果集合
     * @throws SQLException SQLException异常
     */
    public T queryById(ID id) throws SQLException {
        Dao<T, ID> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            T t = dao.queryForId(id);
            dao.commit(databaseConnection);
            return t;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return null;
    }

    /**
     * 判断表是否存在
     *
     * @return 表是否存在
     * @throws SQLException SQLException异常
     */
    public boolean isTableExists() throws SQLException {
        return getDao().isTableExists();
    }


    /**
     * 获得记录数
     *
     * @return 记录数
     * @throws SQLException SQLException异常
     */
    public long count() throws SQLException {
        Dao<T, ID> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);
            long count = dao.countOf();
            dao.commit(databaseConnection);
            return count;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }

    /**
     * 获得记录数
     *
     * @param preparedQuery PreparedQuery类
     * @return 记录数
     * @throws SQLException SQLException异常
     */
    public long count(PreparedQuery<T> preparedQuery) throws SQLException {
        Dao<T, ID> dao = getDao();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = dao.startThreadConnection();
            dao.setAutoCommit(databaseConnection, false);

            long count = dao.countOf(preparedQuery);
            dao.commit(databaseConnection);
            return count;
        } catch (SQLException e) {
            dao.rollBack(databaseConnection);
            e.printStackTrace();
        } finally {
            dao.endThreadConnection(databaseConnection);
        }
        return 0;
    }
}
