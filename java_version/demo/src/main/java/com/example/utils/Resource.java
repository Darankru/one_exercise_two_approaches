package com.example.utils;

public class Resource{
    private final String name;
    private int value;

    public Resource(String name, int value){
        this.name = name;
        this.value = value;
    }

    public String getName(){
        return this.name;
    }

    public int getValue(){
        return this.value;
    }

    public void setValue(int newValue){
        this.value = newValue;
    }
}
