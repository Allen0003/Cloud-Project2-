Introduction:
This script is included in order to automate the deployment and configuration of the project system. Please note this script is only works on Centos 7. The project script covers the following configurations:

1.Creation of Key-Pair, Security group, Storage Volume and deployment of 4 instances with CentOS7 operating system.
2.Change the Ansible inventory to EC2 External Inventory
3.Installation of all required dependencies
4.CouchDB installation and configuration.

In the script, Dynamic external inventory was used to access and control host machines.  Ansible supports a feature which connect to EC2 and gather all instances from the specified account and dynamically store them in host file. Ansible group the instances in many way. In this script, since all node has same basic configuration, Security group is used to access all required instances (4 nodes).

Instructions:
This script contains 2 part: 1-master node configuration(localhost), 2- target node configuration (security_group_cloud_project hosts). The first part is used in order to connect to Nectar and create 4 instances for the project system. The second part will configure each instance to install and configure Couchdb database. 

1-master node configuration(localhost):
This script contains 2 host file. The default file is the External EC2 Inventory. The second host file can be used to connect to desired host statically. In order to use static inventory, this script should be configured manually and replace with default host file.
In order to use ec2 to connect to Nectar on master node please download your authentication access/secret key and authentication script following these instructions:

* Login to NeCTAR Cloud Dashboard
* Click the drop-down list beside the top left nectar logo to select a project
* Click 'Access & Security'
* On the 'Access & Security' page, click tab 'API Access'
* Click button "Download EC2-Credential File"
* Click button "Download OpenStack RC File"
* Save the file into a directory
* Click the drop-down list with your email on the right top of page, then click Settings
* Click 'Reset Password' and save the password appeared on the screen

And in master node enter the following commands:

sudo source <location of ec2rc.sh>
sudo source <location of OpenStack sh>
export AWS_ACCESS_KEY_ID='CHANGE-ME'
export AWS_SECRET_ACCESS_KEY='CHANGE-ME'

Please add the following section into Ansible host file:

[localhost]
master ansible_host=127.0.0.1 ansible_user=<user> ansible_ssh_pass=<pass>

To install the dynamic inventory please copy the ec2.py script to /etc/ansible/hosts and chmod +x it. ec2.ini contains the configuration of EC2 external Inventory and must be placed in /etc/ansible/ec2.ini. Then Ansible can be run as normal. In ec2.ini fil, the “destination_variable” is configured to “private_dns_name” in order to use local DNS. This make it easy to access other nodes by ip address.

2- target node configuration (security_group_cloud_project hosts):
The second part of the script target “security_group_cloud_project” instances which just been created by master node. First the required dependencies installed. Then couchdb installed and configured. In order to install and configure CouchDB please copy the “startupCouch” to /home/ directory. “startupCouch” script is used to create systemd for couchdb service (in order to start/stop/reset the database using systemctl).  

Issues and Challenges: 
Due to the limitation of allocation of instances in Nectar, the instance creation part was not tested, however Master node (Ansible hosted machine), could successfully connect to Nectar Server API and create security-groups, Storage volumes and key-pair. The rest of the script (deployment and configuration of CouchDB, installation of dependencies) was tested and implemented several times.
    The script will install, configure CouchDB, however in this script the clustering configuration is not covered since cluster configuration may require different setting on different machines and some errors and steps can’t be easily predicted. Rather, the clustering is preferred to be manually configured to reduce the overhead of troubleshooting and error handling.
    All relevant programs (TwitteHarvast, DataProccessing, WebService, etc.)  is not included in the script. Scripting these programs are relatively simple however compile and running them without the Database publishment is useless and can cause more overheading.
