@Library('shared-library') _
node(label: 'master'){
    //Variables
    def gitURL = "https://github.com/gireeshgg/guns.git"
    def repoBranch = "master"
    def applicationName = "tomguns"
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
    def dockerCredentialID = 'docker_password'
    def dockerImageName = "${dockerRegistryUserName}/${applicationName}"
    def vmPort = 8089
    def containerPort = 8080
    def lastSuccessfulBuildID = 0
    try
    {
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
    stage('Build Docker image and Push to DockerHub'){
        dockerBuildAndPush "${dockerRegistry}","${dockerCredentialID}","${dockerImageName}","${lastSuccessfulBuildID}","${BUILD_NUMBER}" 
    }
    
    //Remove extra image
    stage('Remove unwanted images in build ENV'){
        removeDockerImage
    }
    
    stage(' creating test environment and Deploying!! '){
	    echo "Creating TEST ENV using Terraform"
	    createTestEnv
	    echo "Deployed into TEST ENV"
    }    

    stage(' Continue to Prod Environment ? '){
    input "Do you want to Deploy in PROD ENV ?"
    }
    
    
	    
    stage('Deploy in Prod'){
	   deployToProd "${BUILD_NUMBER}"
	}
	  
	    stage(' Success Mail'){
	    mail bcc: '', body:" Hurrey !! Success, Enjoy..... ", cc: '', from: '', replyTo: '', subject: 'Job failed', to: 'gireeshgg48@yahoo.com'
	    }
    }
    catch(err)
	{
		currentBuild.result = 'FAILURE'
		//Mail on failure
		mail bcc: '', body:"${err}", cc: '', from: '', replyTo: '', subject: 'Job failed', to: 'gireeshgg48@yahoo.com'
	}
}
