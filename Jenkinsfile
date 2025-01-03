pipeline {
    agent any

    stages {
        // Линтинг кода
        stage('Lint') {
            steps {
                echo 'Running code linting...'
                sh '/opt/homebrew/bin/docker-compose exec app ./gradlew checkstyleMain'
            }
        }

        // Сборка приложения
        stage('Build') {
            steps {
                echo 'Building the application...'
                sh '/opt/homebrew/bin/docker-compose build'
            }
        }

        // Запуск юнит-тестов
        stage('Test') {
            steps {
                echo 'Running unit tests...'
                sh '/opt/homebrew/bin/docker-compose exec app ./gradlew test'
            }
        }

        // Проверка покрытия тестов
        stage('Test Coverage') {
            steps {
                echo 'Checking test coverage...'
                sh '/opt/homebrew/bin/docker-compose exec app ./gradlew jacocoTestReport'
            }
        }

        // Деплой
        stage('Deploy') {
            steps {
                echo 'Deploying the application...'
                sh '/opt/homebrew/bin/docker-compose up -d'
            }
        }
    }

    post {
        always {
            echo 'Cleaning up...'
            sh '/opt/homebrew/bin/docker-compose down'
        }
    }
}
