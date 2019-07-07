@Library('shared-library') _
node(label: 'master'){
    //Variables
    def gitURL = "https://github.com/srinivasbv22/sl.git"
    def repoBranch = "master"
    def applicationName = "guns"
    def sonarqubeServer = "sonarqube"
    def sonarqubeGoal = "clean verify sonar:sonar"
    def mvnHome = "Maven"
    def pom = "pom.xml"
    def goal = "clean install"
    def artifactoryServer = "artifactory"
    def releaseRepo = "guns"
    def snapshotRepo = "guns"
    def dockerRegistry = "https://registry.hub.docker.com"
    def dockerImageRemove = "registry.hub.docker.com"
    def dockerRegistryUserName = "srinivasbv22"
    def dockerCredentialID = "dockerID" 
    def dockerImageName = "${dockerRegistryUserName}/${applicationName}"
    def vmPort = 8089
    def containerPort = 8080
    def lastSuccessfulBuildID = 0
    
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
    stage('Maven Build and Push to Artifactory'){
        mavenBuild "${artifactoryServer}","${mvnHome}","${pom}", "${goal}", "${releaseRepo}", "${snapshotRepo}"
    }
    
    //docker-image-build and Push
    stage('Build Docker image and Push'){
        dockerBuildAndPush "${dockerRegistry}","${dockerCredentialID}","${dockerImageName}"
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
    stage('Run Docker Image'){
        echo "Last Successful Build = ${lastSuccessfulBuildID}"
        runDockerImage "${vmPort}","${containerPort}", "${applicationName}","${dockerImageName}", "${BUILD_NUMBER}", "${lastSuccessfulBuildID}"
    }
    
	stage('Run Docker Database Image'){
	    sh "cd /home/devopsinfra/docker201"
        sh "docker-compose -f /home/devopsinfra/docker201/docker-compose.yml up -d"
    }
	
}
