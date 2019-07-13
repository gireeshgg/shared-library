def call(def registry, def dockerCredential ,def imageName,def releaseRepo, def snapshotRepo){
    echo "${dockerCredential}"
    
    docker.withRegistry('https://registry.hub.docker.com', "[dockerCredential]") {
      
    //build docker image
    image = docker.build("${imageName}:${BUILD_NUMBER}","./PipeSharedLib/Dockerfile")
    
    //push image to hub
    image.push()
    
    
   }
}
