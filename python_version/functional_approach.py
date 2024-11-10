import copy
import json


def allocate_services(task_json):
    per_host_resources = {
        "services_list": [],
        "resources_dict": {
            "cpu_capacity": task_json["hosts"]["cpu_capacity"],
            "ram_capacity": task_json["hosts"]["ram_capacity"],
            "network_capacity": task_json["hosts"]["network_capacity"],
        },
    }

    all_host_dict = {}

    # go through each service to place
    for service in task_json["services"].keys():

        # check if max capacity of hosts is enough
        for necessary_resource in task_json["services"][service].keys():
            if task_json["services"][service][necessary_resource] > task_json["hosts"][necessary_resource]:
                print("Hosts do not have enough capacity!")
                quit()

        place_to_integrate = None

        # check each host if it can take the service
        for host in all_host_dict.keys():
            count_matching_resources = 0
            total_matching_resources = len(task_json["services"][service].keys())

            # check each host resource capacity
            for resource in task_json["services"][service].keys():
                if task_json["services"][service][resource] <= all_host_dict[host]["resources_dict"][resource]:
                    count_matching_resources += 1
            
            # service fits at host: save host index
            if count_matching_resources == total_matching_resources:
                place_to_integrate = host

        # no host found, create new at higher index
        if place_to_integrate == None:
            place_to_integrate = len(all_host_dict.keys())
        
        # update the host dictionary
        res = embed_resource(task_json["services"][service], all_host_dict, service, place_to_integrate, per_host_resources) 
        all_host_dict = res

    return all_host_dict



def integrate_resource(service_dict:dict, host_dict:dict)->dict:
    """
    Given the service resources to place within the host resources, 
    compute new leftover capacity of host.
    """
    for resource in service_dict.keys():
        host_dict["resources_dict"][resource] -= service_dict[resource]
    return service_dict, host_dict


def embed_resource(full_service_dict:dict, full_host_dict:dict, service_key:str, host_key:int, empty_host_dict:dict)->dict:
    """
    Given the location at which host to put a new service, 
    save the service into the host list and updaate remaining resources.
    """
    if host_key >= len(full_host_dict.keys()):
        # new dict
        print(f"Creating new dict at {host_key}")
        full_host_dict[host_key] = copy.deepcopy(empty_host_dict)

    # integrate new service into host
    extracted_host_dict = full_host_dict[host_key]

    _ , new_host_dict = integrate_resource(full_service_dict, extracted_host_dict)

    full_host_dict[host_key] = new_host_dict
    full_host_dict[host_key]["services_list"].append(service_key)

    return full_host_dict


def main():

    with open('to_match.json', 'r') as file:
        data = json.load(file)

    result = allocate_services(task_json=data)

    with open('matched.json', 'w') as f:
        json.dump(result, f)




if __name__ == "__main__":
    main()
