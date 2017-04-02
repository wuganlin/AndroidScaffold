package com.android.scaffold.db.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by wuganlin on 2017/3/31.
 */
@DatabaseTable(tableName = "tb_contact")
public class Contact {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField
    private String name;
    @DatabaseField
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
