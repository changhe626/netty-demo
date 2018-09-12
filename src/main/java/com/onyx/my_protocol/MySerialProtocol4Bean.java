package com.onyx.my_protocol;

import java.util.ArrayList;
import java.util.List;

/**
 * netty  3
 */
public class MySerialProtocol4Bean extends Serializer {

    private long id;
    private String name;
    private List<String> skills =new ArrayList<>();


    @Override
    protected void read() {
        id=readInt();
        name=readString();
        skills=readList(String.class);
    }


    @Override
    protected void write() {
        writeLong(id);
        writeString(name);
        writeList(skills);

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}