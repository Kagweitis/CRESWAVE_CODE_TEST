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
                bat 'java -jar C:\\ProgramData\\Jenkins\\.jenkins\\workspace\\eswave-multi-branch-pipeline_dev\\target\\CRESWAVE_CODE_TEST.jar'
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
