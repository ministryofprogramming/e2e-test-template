node('zalenium') {
    env.MSG_PREFIX = "*[PROD] Naga Trader E2E Tests*"
    env.ZALENIUM_URL = "http://zalenium.devenv"
    env.REPORT_URL = "http://qa.devenv"
    env.SLACK_CHANNEL = "#qa-tests"
    env.ALERT = "*Alert:* <@U95FQAC0P>"
    env.ZALENIUM_STATUS = sh(returnStdout: true, script: 'docker container ls | grep "0.0.0.0:4444" || true').trim()

    properties([pipelineTriggers([[$class: "GitHubPushTrigger"],cron('TZ=Europe/Sarajevo \n\nH(0-1) 7 * * *')])])

    slackSend message: "${MSG_PREFIX} \n\n *Status:* Start \n\n *Zalenium:* `${ZALENIUM_URL}/live`", color: "good", channel: "${SLACK_CHANNEL}", teamDomain: "${env.SLACK_TEAM_DOMAIN}", tokenCredentialId: "${env.SLACK_CREDENTIALS}"

    stage('Clone repository') {
        try {
            checkout scm
        } catch (e) {
            slackSend message: "${MSG_PREFIX} \n\n *Status:* Failed to pull code from GitHub \n\n ${ALERT} \n\n *Jenkins:* `${env.BUILD_URL}console`", color: "danger", channel: "${SLACK_CHANNEL}", teamDomain: "${env.SLACK_TEAM_DOMAIN}", tokenCredentialId: "${env.SLACK_CREDENTIALS}"
        }
    }

    stage('Check Zalenium Status and Build') {
        try {
            if ("${ZALENIUM_STATUS}" == ""){
                sh'echo "Zalenium Status: DOWN"'
                sh'echo "Starting Zalenium \n\n" && cp -R zalenium/ /home/ubuntu && cd /home/ubuntu/zalenium && docker-compose up -d --force-recreate'
            } else {
                sh'echo "Zalenium Status: UP & RUNNING"'
            }
            docker.image ('maven:3.5.4-jdk-8-alpine').inside("-u root" + " -v /tmp:/tmp" + " -v $HOME/.m2:/root/.m2") {
                sh 'mvn clean install -DskipTests'
            }
        } catch (e) {
            slackSend message: "${MSG_PREFIX} \n\n *Status:* Failed [Check Zalenium Status and Build] \n\n ${ALERT} \n\n *Jenkins:* `${env.BUILD_URL}console`", color: "danger", channel: "${SLACK_CHANNEL}", teamDomain: "${env.SLACK_TEAM_DOMAIN}", tokenCredentialId: "${env.SLACK_CREDENTIALS}"
        }

        try {
            stage('Trader Tests') {
                docker.image ('maven:3.5.4-jdk-8-alpine').inside("-u root" + " -v /tmp:/tmp" + " -v $HOME/.m2:/root/.m2") {
                    sh 'mvn test -Pprod -Dcucumber.options="src/test/java/com/naga/trader/features --glue com.naga.trader.steps --tags @prod"'
                }
            }
        } catch (e) {
            slackSend message: "${MSG_PREFIX} \n\n *Status:* Failed [Trader Tests] \n\n ${ALERT} \n\n *Jenkins:* `${env.BUILD_URL}console` \n\n *Report:* `${REPORT_URL}`", color: "danger", channel: "${SLACK_CHANNEL}", teamDomain: "${env.SLACK_TEAM_DOMAIN}", tokenCredentialId: "${env.SLACK_CREDENTIALS}"
        }

        try {
            stage('Generate Report') {
                sh 'sudo chown -R ubuntu:ubuntu "${WORKSPACE}"/allure-results'
                sh 'rm -rf /home/ubuntu/allure/allure-results && cp -R allure-results /home/ubuntu/allure && cp -R /home/ubuntu/allure/allure-report/history /home/ubuntu/allure/allure-results'
                docker.image ('aneslozo/allure-alpine:2.8.1').inside("-u root" + " -v /home/ubuntu/allure/allure-results:/allure-results" + " -v /home/ubuntu/allure/allure-report:/allure-report") {
                    sh 'allure generate /allure-results -o /allure-report --clean'
                }
                sh 'sudo chown -R ubuntu:ubuntu /home/ubuntu/allure'
            }
        } catch (e) {
            slackSend message: "${MSG_PREFIX} \n\n *Status:* Failed [Generate Report] \n\n ${ALERT} \n\n *Jenkins:* `${env.BUILD_URL}console` \n\n *Report:* `${REPORT_URL}`", color: "danger", channel: "${SLACK_CHANNEL}", teamDomain: "${env.SLACK_TEAM_DOMAIN}", tokenCredentialId: "${env.SLACK_CREDENTIALS}"
        }
    }

    slackSend message: "${MSG_PREFIX} \n\n *Status:* Done \n\n *Jenkins:* `${env.BUILD_URL}console` \n\n *Zalenium:* `${ZALENIUM_URL}` \n\n *Report:* `${REPORT_URL}`", color: "good", channel: "${SLACK_CHANNEL}", teamDomain: "${env.SLACK_TEAM_DOMAIN}", tokenCredentialId: "${env.SLACK_CREDENTIALS}"
}