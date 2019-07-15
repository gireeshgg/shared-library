def call (def deployTo){
      try{
		       sh "ssh devopsinfra@${deployTo} 'sudo docker-compose down' "
		       sh "whoami "
			   }catch(err){
		  	 echo "${err}"
			   }
      }
