def call(def dockerImageRemove ,def dockerImageName, def userName, def applicationName,def lastSuccessfulBuild){
  sh "docker rm -f $(docker ps -a)"
  sh "docker rmi -f $(docker images -aq|sort -u))"
}

