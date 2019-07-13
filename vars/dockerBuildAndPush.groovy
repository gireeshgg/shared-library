def call(def registry, def dockerCredential ,def imageName,def releaseRepo, def snapshotRepo){
    echo "${dockerCredential}"
    sh 'pwd'
     //build docker image
    image = docker.build("${imageName}:${BUILD_NUMBER}",".")
    
    //login to docker hub
    //docker.withRegistry('https://registry.hub.docker.com', "${dockerCredential}") {
      
   
    //push image to hub
    image.push()
    
    
   //}
}
