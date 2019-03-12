build:
    mvn clean compile

test-dev:
    mvn test -Pdev -Dcucumber.options="src/test/java/com/naga/trader/features --glue com.naga.trader.steps --tags @dev"
