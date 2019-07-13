def call(){
  //sh "docker rm -f $(docker ps -a)"
  sh "docker rmi -f $(docker images -aq|sort -u))"
}

