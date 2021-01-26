package com.demo.demo.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhoupeng
 */
public class LeaveBillDTO  implements Serializable {

    private Integer id;

    private Integer cost;
    private String applyPerson;
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getApplyPerson() {
        return applyPerson;
    }

    public void setApplyPerson(String applyPerson) {
        this.applyPerson = applyPerson;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "LeaveBillDTO{" +
                "id=" + id +
                ", cost=" + cost +
                ", applyPerson='" + applyPerson + '\'' +
                ", date=" + date +
                '}';
    }
}
