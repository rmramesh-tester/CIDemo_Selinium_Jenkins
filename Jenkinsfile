pipeline {

    agent any

    tools {
        maven 'Maven'
    }

    triggers {
        pollSCM('H/2 * * * *')
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                url: 'https://github.com/rmramesh-tester/CIDemo_Selinium_Jenkins.git'
            }
        }

        stage('Execute Tests') {
            steps {
                bat 'mvn clean test'
            }
        }
    }

    post {

        always {

            emailext(
                subject: "Build Status: ${currentBuild.currentResult}",
                body: "Build completed. Check Jenkins console.",
                to: 'rmramesh.testing@gmail.com'
            )
        }
    }
}