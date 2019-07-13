@Library('shared-library') _
node(label: 'master'){
    //Variables
    def gitURL = "https://github.com/gireeshgg/guns.git"
    def repoBranch = "master"
    def applicationName = "Tomguns"
    def sonarqubeServer = "Sonar"
    def sonarqubeGoal = "clean verify sonar:sonar"
    def mvnHome = "Maven"
    def pom = "pom.xml"
    def goal = "clean install"
    def artifactoryServer = "giri_art"
    def releaseRepo = "guns"
    def snapshotRepo = "guns"
    def dockerRegistry = "https://registry.hub.docker.com"
    def dockerImageRemove = "registry.hub.docker.com"
    def dockerRegistryUserName = "gireeshgg"
    def dockerCredentialID = credentials('docker_password','docker_password') 
    def dockerImageName = "${dockerRegistryUserName}/${applicationName}"
    def vmPort = 8089
    def containerPort = 8080
    def lastSuccessfulBuildID = 0
 //   try
 //   {
    //Check for Previous-Successful-Build
	    
    stage('Get Last Successful Build Number'){
        def build = currentBuild.previousBuild
        while (build != null) {	
            if (build.result == "SUCCESS")
            {
                lastSuccessfulBuildID = build.id as Integer
                break
            }
            build = build.previousBuild
        }
        echo "${lastSuccessfulBuildID}"
    }
    //Git Stage
    stage('Git-Checkout'){
        gitClone "${gitURL}","${repoBranch}"    
    }
    
    //Sonarqube Analysis
    stage('Sonarqube-scan'){
        sonarqubeScan "${mvnHome}","${sonarqubeGoal}","${pom}", "${sonarqubeServer}"
    }
    
    //Quality-gate
    stage('Quality-Gate'){
        qualityGate "${sonarqubeServer}"
    }
    
    //MVN Build
    stage('Maven Build'){
        mavenBuild "${artifactoryServer}","${mvnHome}","${pom}", "${goal}","${BUILD_NUMBER}" 
    }
    
    //docker-image-build and Push
    stage('Build Docker image and Push to Artifactory'){
        dockerBuildAndPush "${dockerRegistry}","${dockerCredentialID}","${dockerImageName}","${releaseRepo}", "${snapshotRepo}"
    }
    
    //Remove extra image
    stage('Remove image'){
        removeDockerImage "${dockerImageRemove}","${dockerImageName}","${dockerRegistryUserName}","${applicationName}","${lastSuccessfulBuildID}"
    }
    
    //Download Docker Image
    stage('Download Docker Image'){
        downloadDockerImage "${dockerImageName}", "${BUILD_NUMBER}"
    }
    
  
    
    //Delete Old running Container and run new built
    //stage('Run Docker Image'){
    //    echo "Last Successful Build = ${lastSuccessfulBuildID}"
    //    runDockerImage "${vmPort}","${containerPort}", "${applicationName}","${dockerImageName}", "${BUILD_NUMBER}", "${lastSuccessfulBuildID}"
    //}
	  try{
		  stage('Remove previous image'){
		  sh "docker-compose down"
		}
			}catch(err){
				sh "echo $err"
			}
    
    stage('Run Docker Database Image'){
            sh "docker-compose -f docker-compose.yml up -d"
		//sh "kubectl apply -f /home/dvopsinfra/k81/guns-ui-deployment.yml"
    }
   /* }
    catch(err)
	{
		currentBuild.result = 'FAILURE'
		//Mail on failure
		mail bcc: '', body:"${err}", cc: '', from: '', replyTo: '', subject: 'Job failed', to: 'gireeshgg48@yahoo.com'
	}*/
}
