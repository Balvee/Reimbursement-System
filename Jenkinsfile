pipeline {
  agent any
  stages {
    stage('PCF Deployment') {
      steps {
        sh 'cf login -a https://api.run.pivotal.io'
        sh 'echo \'balvare@att.net\''
        sh 'echo \'B2o0s!t8on\''
      }
    }
  }
  environment {
    CF_ORG = 'Balvare-org'
  }
}