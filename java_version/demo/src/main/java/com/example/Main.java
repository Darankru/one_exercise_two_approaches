package com.example;

import com.example.utils.Host;
import com.example.utils.HostManager;




public class Main {
    public static void main(String[] args) {
        try{
            HostManager manager = JsonFileHandler.readJson(args);
            Host[] hosts = manager.getHosts();
            JsonFileHandler.writeJson(hosts);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
}
