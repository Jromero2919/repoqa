REM bat script for App Coverage Demo (Jtest)
@echo off

echo ===================================================================
echo Running Static Analysis and Unit Tests against build.id=%BUILD_ID%
echo ===================================================================

echo **1/3** Execute Static Analysis: Recommended Rules
call mvn jtest:jtest -Djtest.config="builtin://Recommended Rules" -Dproperty.build.id=%BUILD_ID% -Dproperty.dtp.project=Parabank

rem echo **X/X** (Optional) Execute Static Analysis: All Rules
rem call mvn jtest:jtest -Djtest.config="jtest.dtp://All Rules" -Dproperty.build.id=%BUILD_ID% -Dproperty.dtp.project=Parabank

echo **2/3** Execute Metrics
call mvn jtest:jtest -Djtest.config="builtin://Metrics" -Dproperty.build.id=%BUILD_ID%  -Dproperty.dtp.project=Parabank

echo **3/3** Execute Unit Tests
call mvn -Dmaven.test.failure.ignore=true jtest:coverage jtest:jtest -Djtest.config="builtin://Unit tests" -Dproperty.build.id=%BUILD_ID%  -Dproperty.dtp.project=Parabank -Dproperty.report.coverage.images="UT;AllTests"

echo =================================================================
echo Finished Static Analysis and Unit Testing of build.id=%BUILD_ID%
echo =================================================================
