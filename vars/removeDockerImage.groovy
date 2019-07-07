def call(def dockerImageRemove ,def dockerImageName, def userName, def applicationName,def lastSuccessfulBuild){
  sh "docker rmi $(docker images -aq)"
}

