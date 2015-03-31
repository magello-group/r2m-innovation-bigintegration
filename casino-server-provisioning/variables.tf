variable "aws_access_key" { 
  description = "AWS access key"
}

variable "aws_secret_key" { 
  description = "AWS secret access key"
}

variable "aws_region" {
    description = "AWS region to launch servers."
    default = "eu-west-1"
}

variable "key_name" {
    description = "Name of the SSH keypair to use in AWS."
    default = "BigIntegration"
}

variable "key_path" {
    description = "Path to the private portion of the SSH key specified."
    default = "~/.ssh/BigIntegration.pem"
}

# Ubuntu Utopic 14.10 (x64) (hvm:ebs)
variable "aws_amis" {
    default = {
        eu-central-1 = "ami-56c2f14b"
        eu-west-1 = "ami-5f9e1028"
        us-east-1 = "ami-12793a7a"
    }
}
