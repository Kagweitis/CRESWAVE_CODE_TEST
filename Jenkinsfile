pipeline {
    agent any
    environment {
        CI = 'true'
        dockerImageName = "kagweitis/creswave-code-test"
        dockerImage = ""
    }
    stages {
        stage('build') {
            steps {
                script {
//                     dockerImage = docker.build(dockerImageName)
                    bat "docker build -t ${dockerImageName} ."
                }
            }
        }
        stage('Pushing Image') {
            environment {
                registryCredential = 'dockerhub-credentials'
            }
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', registryCredential) {
                        dockerImage.push("latest")
                    }
                }
            }
        }
        stage('Deploying creswave-code-test container to Kubernetes') {
            steps {
                script {
                    kubernetesDeploy(configs: ["deployment.yaml", "service.yaml"])
                }
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
