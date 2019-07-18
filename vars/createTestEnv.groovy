def call(){

        sh "cd terraform && terraform apply -auto-approve"
        sh "export TERRAFORMVM=`cat public_ip.txt`"
        sh "sudo sh replaceip.sh"
        sh "sudo ansible-playbook ../PipeSharedLib/ansibleFiles/installpackages.yml -vv "
  
}
