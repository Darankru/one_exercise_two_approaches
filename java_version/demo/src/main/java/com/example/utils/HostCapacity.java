package com.example.utils;

public class HostCapacity{
    private final Resource[] resources;

    public HostCapacity(Resource[] resources){
        this.resources = resources;
    }

    public Resource[] getResources(){
        return this.resources;
    }

    public boolean checkIfFits(Resource[] toFitResources){
        /**
         * Check whether a given array of service resources fits within those of the host.
         */
        for (Resource fitResource : toFitResources) {
            boolean found = false;
            for (Resource hostResource: this.resources){
                if(fitResource.getName() == hostResource.getName()){
                    found = true;
                    if(fitResource.getValue() > hostResource.getValue()){
                        return false;
                    }
                }
            }
            // service resource does not exist within host 
            if (!found){
                return false;
            }
        }
        // everything fit
        return true;
    }

    public void reduceResourceCapacity(String resourceId, int reductionValue){
        for (Resource resource : this.resources) {
            if (resourceId==resource.getName()){
                resource.setValue(resource.getValue() - reductionValue);
            } 
        }

    }

    public HostCapacity deepCopy() {
        Resource[] copiedResources = new Resource[this.resources.length];
        for (int i = 0; i < this.resources.length; i++) {
            copiedResources[i] = new Resource(this.resources[i].getName(), this.resources[i].getValue());
        }
        return new HostCapacity(copiedResources);
    }

}
