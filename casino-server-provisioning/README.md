Provisioning
------------

The following needs to be installed to provision the instances

 * Terraform (https://www.terraform.io)
 * Ansible (http://www.ansible.com)

Installation
------------

 1. Create a file named terraform.tfvars, containing the following lines (replace XXX with secret keys)

# Do *NOT* check in this file into Git
aws_access_key = "XXX"
aws_secret_key = "XXX"
key_name = "BigIntegration"
key_path = "~/.ssh/BigIntegration.pem"

 2. Create a file ~/.boto (only works on linux or Mac) containing:

[Credentials]
aws_access_key_id = XXX
aws_secret_access_key = XXX

 2. Create EC2 instances with "terraform apply"
 3. Provision instances with "ansible-playbook -i ec2.py ansible.yml"
 4. Finally destroy EC2 instances with "terraform destroy"

