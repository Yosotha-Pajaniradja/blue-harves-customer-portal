pipeline {
    agent any
    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage('Maven Build') {
            steps {
                sh 'gradle assemble'
            }
        }
         stage('Unit Tests') {
            steps {
                sh 'Unit test'
            }
        }
         stage('Tests E2E') {
            steps {
                sh 'maven E2E test'
            }
        }
         stage('Sonar Quality Gate') {
            steps {
                sh 'Sonar Quality Gate'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'gradle docker'
            }
        }
        stage('Kube Deploy') {
            steps {
                sh 'Retrieve DTR Image and deploy in kube namespace'
            }
        }
    }
}
