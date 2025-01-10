pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                script {
                    sh './gradlew clean build'
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    sh './gradlew test'
                }
            }
        }
        stage('Docker Build') {
            steps {
                script {
                    sh 'docker-compose build'
                }
            }
        }
        stage('Docker Compose Up') {
            steps {
                script {
                    sh 'docker-compose up -d'
                }
            }
        }
    }
}