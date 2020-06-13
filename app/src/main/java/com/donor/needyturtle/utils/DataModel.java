package com.donor.needyturtle.utils;

import java.io.Serializable;

public class DataModel implements Serializable {

    private String id;
    private String age;
    private String current_address;
    private String dieases;

    public String getId() {
        return id;
    }

    public DataModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getAge() {
        return age;
    }

    public DataModel setAge(String age) {
        this.age = age;
        return this;
    }

    public String getCurrent_address() {
        return current_address;
    }

    public DataModel setCurrent_address(String current_address) {
        this.current_address = current_address;
        return this;
    }

    public String getDieases() {
        return dieases;
    }

    public DataModel setDieases(String dieases) {
        this.dieases = dieases;
        return this;
    }
}
