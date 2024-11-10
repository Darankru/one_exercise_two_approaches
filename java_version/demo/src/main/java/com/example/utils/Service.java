package com.example.utils;

public class Service{
    private final String id;
    private final Resource[]  resources;

    public Service(String id, Resource[] resources){
        this.id = id;
        this.resources = resources;
    }

    public String getId(){
        return this.id;
    }

    public Resource[] getResources(){
        return this.resources;
    }
}
