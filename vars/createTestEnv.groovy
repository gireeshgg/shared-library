def call(){
         sh "cd terraform && sudo terraform apply -auto-approve"
       // sh "export TERRAFORMVM=`cat /var/lib/jenkins/workspace/PipeSharedLib/terraform/public_ip.txt`"
        sh "sudo sh terraform/replaceip.sh"
       // sh "cat /etc/ansible/hosts"
        sh "sudo ansible -m ping terraformvm"
        sh "sudo ansible-playbook ../PipeSharedLib/ansibleFiles/installpackages.yaml  -vv "
} 
