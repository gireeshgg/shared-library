def call (def artifactoryServer, def mvnHome,def pom, def goal){

  rtMavenRun (
      tool: "${mvnHome}",
      pom: "${pom}",
      goals: "${goal}",
      opts: '-Xms1024m -Xmx4096m',
      deployerId: 'deployer-unique-id',
  )
}
