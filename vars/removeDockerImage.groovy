def call(def dockerImageRemove ,def dockerImageName, def userName, def applicationName,def lastSuccessfulBuild){
  sh "docker rmi -f ${dockerImageRemove}/${dockerImageName}:${BUILD_NUMBER}"
  sh "docker rmi -f ${userName}/${applicationName}:${BUILD_NUMBER}"
  sh "docker rmi -f ${userName}/${applicationName}:${lastSuccessfulBuild}"
}

