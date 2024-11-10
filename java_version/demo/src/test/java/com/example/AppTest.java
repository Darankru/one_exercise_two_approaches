package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.example.utils.Host;
import com.example.utils.HostCapacity;
import com.example.utils.HostManager;
import com.example.utils.Resource;
import com.example.utils.Service;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    @Test
    public void testHostResources() {
        Resource cpu_resource = new Resource("cpu_capacity", 5);
        assertNotNull(cpu_resource);
        assertEquals(cpu_resource.getName(),  "cpu_capacity");

        Resource ram_resource = new Resource("ram_capacity", 5);
        Resource network_resource = new Resource("network_capacity", 5);

        Resource[] hostResources = {cpu_resource, ram_resource, network_resource};

        // create host with resources
        HostCapacity hostCapacity = new HostCapacity(hostResources);

        assertNotNull(hostCapacity);
        assertTrue(hostCapacity.checkIfFits(hostResources));

        // check that host capacity gets reduced correctly
        hostCapacity.reduceResourceCapacity("ram_capacity", 3);
        int get_actual = -1;
        for (Resource resource : hostCapacity.getResources()) {
            if (resource.getName() == "ram_capacity"){
                get_actual = resource.getValue();
            }
        }
        assertTrue(get_actual == 2);
        

    }

    @Test
    public void testServices() {
        Resource cpu_resource = new Resource("cpu_capacity", 5);
        Resource ram_resource = new Resource("ram_capacity", 5);
        Resource network_resource = new Resource("network_capacity", 5);

        Resource[] serviceResources = {cpu_resource, ram_resource, network_resource};

        Service service = new Service("service_73816861", serviceResources);
        assertNotNull(service);

        HostCapacity hostCapacity = new HostCapacity(serviceResources);
        Host host = new Host(0, hostCapacity);

        host.integrate_service(service);

        Service[] expected = {service};

        Service[] actual = host.getServices();


        assertTrue(actual[0] == expected[0]);

    }

    @Test
    public void createHost(){
        Resource cpu_resource = new Resource("cpu_capacity", 5);
        Resource ram_resource = new Resource("ram_capacity", 5);
        Resource network_resource = new Resource("network_capacity", 5);

        Resource[] sharedResources = {cpu_resource, ram_resource, network_resource};

        Service service = new Service("service_186316861", sharedResources);
        Service[] serviceArray = {service};
        HostCapacity capacity = new HostCapacity(sharedResources);

        HostManager hostManager = new HostManager(capacity, new Service[]{});
        assertNotNull(hostManager);

        // check if creation of hosts works using init
        HostManager newHostManager = new HostManager(capacity,serviceArray);
        Host[] viaInitCreatedHosts = newHostManager.getHosts();
        assertNotNull(viaInitCreatedHosts[0]);
        assertTrue(viaInitCreatedHosts[0].getId() == 0);
        

    }
}
