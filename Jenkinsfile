pipeline {
  agent any
  stages {
<<<<<<< HEAD
        stage('CF Push') {
          when {
            anyOf{
              branch 'master'
              branch 'development'
              environment name: 'DEBUG_BLD', value: '1'
            }
          }
          steps {
            script {
              if(env.BRANCH_NAME == 'master') {
                env.SPACE = "production"
              } else {
                env.space = "development"
              }
              env.CF_DOCKER_PASSWORD=readFile("/run/secrets/CF_DOCKER_PASSWORD").trim()
            }
            sh "cf target -o $CF_ORG -s $SPACE"
            sh "cf push"

          }
        }
=======
    stage('PCF Deployment') {
      steps {
        sh '''cf login -a https://api.run.pivotal.io \\
\'\'\'balvare@att.net\'\'\' \\
\'\'\'B2o0s!t8on\'\'\''''
        sh 'balvare@att.net'
        sh 'B2o0s!t8on'
      }
    }
>>>>>>> dc9bf37cee15ca038ec6e311c3acc69d58a1129b
  }
  environment {
    CF_ORG = '\'Balvare-org\''
  }
}