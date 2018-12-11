pipeline {
  agent any
  stages {
    stage('CF Push') {
        steps {
            sh "cf target -o $CF_ORG -s $SPACE"
            sh "cf push"
        }
    }
  }
}