def call(def registry, def dockerCredential ,def imageName,def tagNumber){
    echo "${dockerCredential}"
    sh 'pwd'
    
    
    //login to docker hub
    docker.withRegistry('https://registry.hub.docker.com', "${dockerCredential}") {
     
        sh "docker pull ${dockerImageName}:latest}"
        sh "docker tag ${dockerImageName}:latest ${dockerImageName}:${tagNumber}"
        sh "docker push ${dockerImageName}:${tagNumber}"
        
      //build docker image
    image = docker.build("${imageName}:latest",".")
        
    //push image to hub
    image.push()
    
    
   }
}
