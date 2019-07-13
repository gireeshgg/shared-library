def call(def registry, def dockerCredential ,def imageName,def tagNumber){
    echo "${dockerCredential}"
    sh 'pwd'
    
    
    //login to docker hub
    docker.withRegistry('https://registry.hub.docker.com', "${dockerCredential}") {
     
      //retaging the old image
        try{
            old_image=docker.image("${imageName}:latest")
     
            // sh "docker pull ${imageName}:latest"
            // sh "docker tag ${imageName}:latest ${imageName}:${tagNumber}"
     
            //pusing the old image   
            old_image.push("${tagNumber}")       
        }catch(err){
            sh "echo ${err}"
        }
        
        
    //build new image
    image = docker.build("${imageName}",".")
        
    //push image to hub
    image.push("latest")
    
    
   }
}
