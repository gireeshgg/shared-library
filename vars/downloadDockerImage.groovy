def call(def dockerImageName, def tagNumber){
  sh "docker pull ${dockerImageName}:${tagNumber}"
  sh "docker tag ${dockerImageName}:${tagNumber} ${dockerImageName}:latest"
}
