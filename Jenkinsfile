pipeline {
    agent { label 'aws-ready' }

    environment {
        COMMIT_HASH = sh(returnStdout: true, script: "git rev-parse --short=8 HEAD").trim()
        API_REPO_NAME = 'nr-utopia-auth'
        AWS_REGION_ID = "${sh(script:'aws configure get region', returnStdout: true).trim()}"
        AWS_ACCOUNT_ID = "${sh(script:'aws sts get-caller-identity --query "Account" --output text', returnStdout: true).trim()}"
        UTOPIA_MICROSERVICE_AUTH_PORT=credentials('nr_utopia_auth_port')
        UTOPIA_DB_PORT=credentials('nr_utopia_db_port')
        UTOPIA_DB_HOST=credentials('nr_utopia_db_host')
        UTOPIA_DB_NAME=credentials('nr_utopia_db_name')
        UTOPIA_DB_LOGIN=credentials('nr_utopia_db_login')
        UTOPIA_DB_USER="${env.UTOPIA_DB_LOGIN_USR}"
        UTOPIA_DB_PASSWORD="${env.UTOPIA_DB_LOGIN_PSW}"
        UTOPIA_JWT_SECRET=credentials('nr_utopia_jwt_secret')
        AWS_PROFILE=credentials('nr_aws_profile')
        SONARQUBE_ID = tool name: 'SonarQubeScanner-4.6.2'

        ECR_REPO="${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION_ID}.amazonaws.com"

        ART_REPO_ENDPOINT = credentials("AM_ARTIFACTORY_ENDPOINT")
        ART_REPO_LOGIN  = credentials("nr_artifactory_login")
        ARTIFACTORY_REPO="${ART_REPO_ENDPOINT}/nr-utopia/${API_REPO_NAME}"
    }

    stages {
        stage('Setup') {
            steps {
                sh 'export AWS_PROFILE=$AWS_PROFILE'
            }
        }
        stage('ECR Login') {
            steps {
               echo 'logging into ECR'
               sh 'aws ecr get-login-password --region ${AWS_REGION_ID} | docker login --username AWS --password-stdin ${ECR_REPO}'
            }
        }
        stage('Artifactory Login') {
                echo 'logging into Artifactory $ART_REPO_ENDPOINT'
                sh 'echo ${ART_REPO_LOGIN_PSW} | docker login ${ART_REPO_ENDPOINT} --username ${ART_REPO_LOGIN_USR} --password-stdin'
        }
        stage('Build JAR file') {
            steps {
                sh 'docker context use default'
                sh "mvn clean package"
            }
        }
        stage('SonarQube') {
             steps {
                echo 'Running SonarQube Quality Analysis'
                withSonarQubeEnv('SonarQube') {
                    sh """
                       ${SONARQUBE_ID}/bin/sonar-scanner \
                       -Dsonar.projectKey=nr-utopia-auth \
                       -Dsonar.sources=./src/main/java/com/smoothstack/utopia/auth \
                       -Dsonar.java.binaries=./target/classes/com/smoothstack/utopia/auth
                    """
                }
                timeout(time: 5, unit: 'MINUTES') {
                    sleep(10)
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                echo 'Building Docker Image'
                sh 'docker build -t ${API_REPO_NAME} .'
            }
        }
        stage('Push To ECR') {
            steps {
                echo 'Tagging images'
                sh 'docker tag ${API_REPO_NAME}:latest ${ECR_REPO}/${API_REPO_NAME}:latest'
                sh 'docker tag ${API_REPO_NAME}:latest ${ECR_REPO}/${API_REPO_NAME}:$COMMIT_HASH'
                echo 'Pushing images'
                sh 'docker push ${ECR_REPO}/${API_REPO_NAME}:latest'
                sh 'docker push ${ECR_REPO}/${API_REPO_NAME}:$COMMIT_HASH'
            }
        }
        stage('Push To Artifactory') {
            steps {
                echo 'Tagging images'
                sh 'docker tag ${API_REPO_NAME}:latest ${ARTIFACTORY_REPO}/${API_REPO_NAME}:latest'
                sh 'docker tag ${API_REPO_NAME}:latest ${ARTIFACTORY_REPO}/${API_REPO_NAME}:$COMMIT_HASH'
                echo 'Pushing images'
                sh 'docker push ${ARTIFACTORY_REPO}/${API_REPO_NAME}:latest'
                sh 'docker push ${ARTIFACTORY_REPO}/${API_REPO_NAME}:$COMMIT_HASH'
            }
        }
        stage('Cleanup') {
            steps {
                echo 'Removing images'
                sh 'docker rmi ${API_REPO_NAME}:latest'
                sh 'docker rmi ${ECR_REPO}/${API_REPO_NAME}:latest'
                sh 'docker rmi ${ECR_REPO}/${API_REPO_NAME}:$COMMIT_HASH'
                sh 'docker rmi ${ARTIFACTORY_REPO}/${API_REPO_NAME}:latest'
                sh 'docker rmi ${ARTIFACTORY_REPO}/${API_REPO_NAME}:$COMMIT_HASH'
                sh 'unset AWS_PROFILE'
            }
        }
    }
}
