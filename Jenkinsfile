pipeline {
    agent any

    environment {
        UTOPIA_MICROSERVICE_AUTH_PORT=credentials('auth_port')
        UTOPIA_DB_PORT=credentials('db_port')
        UTOPIA_DB_HOST=credentials('db_host')
        UTOPIA_DB_NAME=credentials('db_name')
        UTOPIA_DB_USER=credentials('db_user')
        UTOPIA_DB_PASSWORD=credentials('db_password')
        UTOPIA_JWT_SECRET=credentials('jwt_secret')
    }

    stages {
        stage('Build') {
            steps {
                sh "mvn clean package"
            }
        }
        stage('Docker') {
            steps {
                sh 'docker build -t auth .'
            }
        }
    }
}
