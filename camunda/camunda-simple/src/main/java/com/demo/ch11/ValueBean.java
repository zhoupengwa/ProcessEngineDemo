package com.demo.ch11;

import java.io.Serializable;

/**
 * @author zhoupeng create on 2021-03-05
 */
public class ValueBean implements Serializable {

    private String value;

    public ValueBean(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
