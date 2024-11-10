package com.example.utils;

public class Host{
    private final int id;
    private Service[] saved_services;
    private final HostCapacity capacity;

    public Host(int id, HostCapacity capacity){
        this.id = id;
        this.saved_services = new Service[0];
        this.capacity = capacity;
    }

    public int getId(){
        return this.id;
    }

    public Service[] getServices(){
        return this.saved_services;
    }

    public HostCapacity getCapacity(){
        return this.capacity;
    }

    public void integrate_service(Service service){
        // update Host capacity
        for (Resource serviceResource : service.getResources()) {
            capacity.reduceResourceCapacity(serviceResource.getName(), serviceResource.getValue());
        }
        // update saved services array
        this.saved_services = HostManager.expandArray(service, this.saved_services);
    }
}
