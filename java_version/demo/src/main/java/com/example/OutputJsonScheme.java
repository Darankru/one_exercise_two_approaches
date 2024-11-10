package com.example;
import java.util.List;
import java.util.Map;

public class OutputJsonScheme {
    private List<String> services_list;
    private Map<String, Integer> resources_dict;

    public OutputJsonScheme(List<String> services_list, Map<String, Integer> resources_dict) {
        this.services_list = services_list;
        this.resources_dict = resources_dict;
    }

    public List<String> getServices_list() {
        return services_list;
    }

    public void setServices_list(List<String> services_list) {
        this.services_list = services_list;
    }

    public Map<String, Integer> getResources_dict() {
        return resources_dict;
    }

    public void setResources_dict(Map<String, Integer> resources_dict) {
        this.resources_dict = resources_dict;
    }
}