pipeline {
    agent any
    environment {
        CI = 'true'
    }
    stages {
        stage('Build') {
            steps {
                 bat 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                echo "Testing"
            }
        }
        stage('Deploy for development') {
            when {
                branch 'dev'
            }
            steps {
                echo "deploying to dev"
//                 bat 'java -jar C:\\ProgramData\\Jenkins\\.jenkins\\workspace\\254-reviews-multipipeline_dev\\target\\254-reviews-0.0.1-SNAPSHOT.jar'
            }
        }
        stage('Deploy for production') {
            when {
                branch 'production'
            }
            steps {
                 echo "Deploy to production environment"
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploying"
            }
        }
    }
}