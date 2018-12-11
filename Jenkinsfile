pipeline {
  agent any
  stages {
    stage('PCF Deployment') {
      steps {
        sh '''cf login \\
https://api.run.pivotal.io'''
      }
    }
  }
}