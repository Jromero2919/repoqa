set BUILD_ID=Parabank-%date:~10,4%%date:~4,2%%date:~7,2%-delta

echo // %BUILD_ID% >> src\com\parasoft\parabank\dao\jdbc\internal\SampleJdbcDynamicDataInserter.java
echo // %BUILD_ID% >> src\com\parasoft\parabank\domain\logic\impl\AdminManagerImpl.java
echo // %BUILD_ID% >> src\com\parasoft\parabank\domain\Account.java
echo // %BUILD_ID% >> src\com\parasoft\bookstore2\ProductInfo.java
echo // %BUILD_ID% >> src\com\parasoft\bookstore2\Order.java
echo // %BUILD_ID% >> src\com\parasoft\bookstore\Book.java
echo // %BUILD_ID% >> src\com\parasoft\bookstore\SubmittedOrder.java
echo // %BUILD_ID% >> src\com\parasoft\bookstore\TempBook.java
echo // %BUILD_ID% >> src\com\parasoft\parabank\util\WadlGenerator.java
echo // %BUILD_ID% >> src\com\parasoft\parabank\util\WadlGeneratorExtended.java
echo // %BUILD_ID% >> src\com\parasoft\parabank\util\AccessModeController.java

echo **1/3** Execute Static Analysis: Recommended Rules
call mvn jtest:jtest -Djtest.config="builtin://Recommended Rules" -Dproperty.build.id=%BUILD_ID% -Dproperty.dtp.project=Parabank

rem call mvn -Dmaven.test.failure.ignore=true jtest:coverage jtest:jtest -Djtest.config="builtin://Unit tests" -Dproperty.build.id=%BUILD_ID%  -Dproperty.dtp.project=Parabank -Dproperty.report.coverage.images="UT;AllTests"
