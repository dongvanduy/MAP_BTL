package com.example.quanlysinhvien;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class User implements Serializable {
    private int id;
    private String name;
    private int vanghoc;
    private int group;

    public User(int id, String name, int vanghoc) {
        this.id = id;
        this.name = name;
        this.vanghoc = vanghoc;
    }

    public User(int id, String name, int vanghoc, int group) {
        this.id = id;
        this.name = name;
        this.vanghoc = vanghoc;
        this.group = group;
    }

    public User(String name, int vanghoc) {

        this.name = name;
        this.vanghoc = vanghoc;
    }
    public User() {}
    public int getVanghoc() {
        return vanghoc;
    }
    public int getGroup() {
        return group;
    }
    public void setGroup(int group){
        this.group = group;
    }
    public void setVanghoc(int vanghoc) {
        this.vanghoc = vanghoc;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("vanghoc", vanghoc);
        result.put("group", group);
        return result;
    }
}
