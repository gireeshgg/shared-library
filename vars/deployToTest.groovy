 def call(def deployTo){
	    sh "scp docker-compose.yaml  devopsinfra@${deployTo}:./"
	    sh "scp nginx.conf devopsinfra@${deployTo}:./"
	    sh "ssh devopsinfra@${deployTo} 'sudo docker-compose up -d' "
}
