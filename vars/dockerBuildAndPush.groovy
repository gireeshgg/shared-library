def call(def registry, def dockerCredential ,def imageName,def tagNumber, def build_number){
    echo "${dockerCredential}"
    echo "${tagNumber}"
    echo "${build_number}"
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
            echo "${err}"
        }
        
        
    //build new image
    image = docker.build("${imageName}",".")
    image.push("${build_number}")    
    //push image to hub
    image.push("latest")
    
    
   }
}
