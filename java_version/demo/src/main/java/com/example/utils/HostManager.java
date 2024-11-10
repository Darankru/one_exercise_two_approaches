package com.example.utils;

import java.util.Arrays;

public class HostManager{
    private Host[] hosts;

    public HostManager(HostCapacity capacities, Service[] services){
        this.hosts = new Host[0];

        /** main logic:
        *   for each service, go through the list of hosts
        *   and check if the service can be hosted on the host
        *   if it can, add the service to the host
        *   if there are no hosts, create a new one
        */

        for (Service service : services) {
            boolean integrated = false;
            for (Host host : this.hosts) {
                if (host.getCapacity().checkIfFits(service.getResources())) {
                    host.integrate_service(service);
                    integrated = true;
                    break; // Service integrated, no need to check other hosts
                }     
            }

            // new host
            if (!integrated) {
                Host newHost = new Host(this.hosts.length, capacities.deepCopy());
                newHost.integrate_service(service);
                this.hosts = expandArray(newHost, this.hosts);
            }
        }

    }

    public Host[] getHosts(){
        return this.hosts;
    }

    public static <T> T[] expandArray(T entry, T[] oldArray){
        T[] newArray = Arrays.copyOf(oldArray, oldArray.length + 1);
        newArray[oldArray.length] = entry;
        return newArray;
    }
}
