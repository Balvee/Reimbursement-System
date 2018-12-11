pipeline {
  agent any
  stages {
    stage('PCF Deployment') {
      steps {
        pushToCloudFoundry(target: 'https://api.run.pivotal.io', organization: 'Balvare-org', cloudSpace: 'deployment', credentialsId: 'balvare@att.net')
      }
    }
  }
  environment {
    CF_ORG = 'Balvare-org'
  }
}