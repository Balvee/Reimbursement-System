pipeline {
  agent any
  environment {
    CF_ORG = "'Balvare-org'"
  }
  stages {
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
  }
}