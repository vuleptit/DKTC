/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Win7
 */
public class Subject {
    private int id;
    private String name;
    private int TCNum;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTCNum() {
        return TCNum;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTCNum(int TCNum) {
        this.TCNum = TCNum;
    }
    
    
}
