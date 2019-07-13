def call(def registry, def dockerCredential ,def imageName,def tagNumber){
    echo "${dockerCredential}"
    sh 'pwd'
    
    
    //login to docker hub
    docker.withRegistry('https://registry.hub.docker.com', "${dockerCredential}") {
     
        sh "docker pull ${imageName}:latest}"
        sh "docker tag ${imageName}:latest ${imageName}:${tagNumber}"
        sh "docker push ${imageName}:${tagNumber}"
        
      //build docker image
    image = docker.build("${imageName}:latest",".")
        
    //push image to hub
    image.push()
    
    
   }
}
