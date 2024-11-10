import unittest
from functional_approach import integrate_resource, embed_resource


class TestMatchingMethods(unittest.TestCase):

    def test_integrate_resource(self):
        """Tests whether capacities are reduced correctly"""
        exemplary_service = {"cpu_capacity":2, "ram_capacity":3, "network_capacity":4}
        exemplary_host = {"resources_dict": {
            "cpu_capacity":5, 
            "ram_capacity":5, 
            "network_capacity":5}
            }

        _, host = integrate_resource(exemplary_service, exemplary_host)

        self.assertEqual(host["resources_dict"]["cpu_capacity"], 3)
        self.assertEqual(host["resources_dict"]["ram_capacity"], 2)
        self.assertEqual(host["resources_dict"]["network_capacity"], 1)


    def test_embed_resource(self):
        """Tests whether service capacities are correctly subtracted from hosts, and service ids stored at the host. 
           Tests creation of hosts.
        """

        per_host_resources = {
            "services_list": [],
            "resources_dict": {
                "cpu_capacity": 5,
                "ram_capacity": 5,
                "network_capacity": 5,
            }
        }


        exemplary_service = {"cpu_capacity":2, "ram_capacity":3, "network_capacity":4}
        exemplary_host = {5:{"services_list":[], 
                             "resources_dict": {
                                 "cpu_capacity":5, 
                                 "ram_capacity":5, 
                                 "network_capacity":5}} 
                        }

        host = embed_resource(exemplary_service, exemplary_host, 1, 5, per_host_resources)

        # service was stored
        self.assertEqual(host[5]["services_list"], [1])
        # capacities were adjusted
        self.assertEqual(host[5]["resources_dict"]["cpu_capacity"], 3)
        self.assertEqual(host[5]["resources_dict"]["ram_capacity"], 2)
        self.assertEqual(host[5]["resources_dict"]["network_capacity"], 1)

        second_exemplary_host = {}
        second_host = embed_resource(exemplary_service, second_exemplary_host, 1, 1, per_host_resources)

        # host was created
        self.assertIsNotNone(second_host[1]["resources_dict"]["cpu_capacity"])
        # service was stored
        self.assertEqual(second_host[1]["services_list"], [1])
        # capacities were adjusted
        self.assertEqual(second_host[1]["resources_dict"]["cpu_capacity"], 3)
        self.assertEqual(second_host[1]["resources_dict"]["ram_capacity"], 2)
        self.assertEqual(second_host[1]["resources_dict"]["network_capacity"], 1)



    

if __name__ == '__main__':
    unittest.main(verbosity=2)