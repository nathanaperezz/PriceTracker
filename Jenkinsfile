pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'price-tracker-app'
    }

    stages {
        stage('Clone Repository') {
            steps {
                checkout scm
            }
        }

        stage('Build Code') {
            steps {
                // Example: If using Maven
                sh 'mvn clean install'
            }
        }

        stage('Run Tests') {
            steps {
                // Run your test suite
                sh 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                // Build Docker image
                sh 'docker build -t ${DOCKER_IMAGE} .'
            }
        }

        stage('Push Docker Image') {
            steps {
                // Push image to Docker Hub
                withCredentials([string(credentialsId: 'docker-credentials', variable: 'DOCKER_PASSWORD')]) {
                    sh 'echo $DOCKER_PASSWORD | docker login -u my-dockerhub-username --password-stdin'
                }
                sh 'docker push ${DOCKER_IMAGE}'
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
