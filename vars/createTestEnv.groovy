def call(){
        sh "pwd"
        sh "cd terraform && sudo terraform apply -auto-approve"
        sh "cd terraform && sudo export TERRAFORMVM=`cat public_ip.txt`"
        sh "echo $terraform ->IP of the Test ENV"
        sh "sudo sh replaceip.sh"
        sh "sudo ansible-playbook ../PipeSharedLib/ansibleFiles/installpackages.yml -vv "
}
