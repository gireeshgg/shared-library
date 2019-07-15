def call(def BUILD_NUMBER){
echo "Deploying... in Prod!! :|) "
		//sh "export LAST_SUC_BUILD=${lastSuccessfulBuildID}"
		
		sh "sed -ie 's/:[0-9][0-9]/:${BUILD_NUMBER}/g'   k8deploy/app.yaml"  //dont get confused
		//sh "ssh root@localhost export IMAGE_TAG=${BUILD_NUMBER}"
		
    sh "ssh root@localhost kubectl apply -f /var/lib/jenkins/workspace/PipeSharedLib/k8deploy/"
}
