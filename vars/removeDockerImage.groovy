def call(){
  //sh "docker rm -f $(docker ps -a)"
  sh "sudo docker rmi -f \$(docker images -aq|sort -u)"
}

