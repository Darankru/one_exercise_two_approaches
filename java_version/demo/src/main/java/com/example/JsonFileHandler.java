package com.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.utils.Host;
import com.example.utils.HostCapacity;
import com.example.utils.HostManager;
import com.example.utils.Resource;
import com.example.utils.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonFileHandler {
    public static HostManager readJson(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode toMatchNode = objectMapper.readTree(new File("to_match.json"));
        
        // start with hosts
        JsonNode hostsNode = toMatchNode.get("hosts");
        Resource[] hostResources = new Resource[]{};

        // Iterate over hostsNode
        Iterator<Map.Entry<String, JsonNode>> hostsFields = hostsNode.fields();
        while (hostsFields.hasNext()) {
            Map.Entry<String, JsonNode> hostResource = hostsFields.next();

            //System.out.println("Host Key: " + hostResource.getKey() + ", Host Value: " + hostResource.getValue());

            Resource resource = new Resource(hostResource.getKey(), hostResource.getValue().asInt());
            hostResources = HostManager.expandArray(resource, hostResources); // expand array of resources

        }

        // now we have a filled resource array for our hosts
        HostCapacity capacity = new HostCapacity(hostResources);

        // on to the services
        JsonNode servicesNode = toMatchNode.get("services");
        Service[] services = new Service[]{};

        // Iterate over servicesNode
        Iterator<Map.Entry<String, JsonNode>> servicesFields = servicesNode.fields();
        while (servicesFields.hasNext()) {
            Map.Entry<String, JsonNode> service = servicesFields.next();
            String serviceId  = service.getKey();
            Resource[] serviceResources = new Resource[]{};

            // System.out.println("Service Key: " + service.getKey() + ", Service Value: " + service.getValue());
            JsonNode perServiceResources = service.getValue();

            //service resources: repeat host procedure
            Iterator<Map.Entry<String, JsonNode>> servicesResourcesFields = perServiceResources.fields();
            while (servicesResourcesFields.hasNext()) {
                Map.Entry<String, JsonNode> serviceResource = servicesResourcesFields.next();
                Resource newServiceResource = new Resource(serviceResource.getKey(), serviceResource.getValue().asInt());
                serviceResources = HostManager.expandArray(newServiceResource, serviceResources); // expand array of resources
            }

            // collect services
            Service newService = new Service(serviceId, serviceResources);
            services = HostManager.expandArray(newService, services);
        }

        HostManager manager = new HostManager(capacity, services);

        return manager;
    }

    public static void writeJson(Host[] hosts) throws IOException {

        // Create the custom JSON structure
        Map<String, OutputJsonScheme> outputJson = new HashMap<>();
        for (Host host : hosts) {
            List<String> servicesList = new ArrayList<>();
            // which services are saved at the host
            for (Service service : host.getServices()) {
                servicesList.add(service.getId());
            }
            Map<String, Integer> resourcesDict = new HashMap<>();
            // which host resources remain
            for (Resource resource : host.getCapacity().getResources()) {
                resourcesDict.put(resource.getName(), resource.getValue());
            }
            outputJson.put(String.valueOf(host.getId()), new OutputJsonScheme(servicesList, resourcesDict));
        }

        // Convert the custom JSON structure to JSON and write to file
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File("matched.json"), outputJson);
    }
}
