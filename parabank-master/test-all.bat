@echo off
rem **** Setup
set PARABANK-TOMCAT=C:\tomcat-8.0.26-parabank
set BUILD_ID=Parabank-%date:~10,4%%date:~4,2%%date:~7,2%

echo ** Performing a clean Build
call mvn -Dmaven.test.skip=true clean install 
echo ** erasing old SOAtest and Manual Testing reports
call erase /Q soatest\report\*.*
call erase /Q soatest\report2\*.*
call erase /Q report.xml

rem **** Static Analysis and Unit Tests
call jtest-sa-ut.bat

rem **** Automated Functional Testing
call setup-appcoverage.bat
copy /Y target\parabank.war %PARABANK-TOMCAT%\webapps

pushd %PARABANK-TOMCAT%\bin
call startup.bat
echo Waiting for Parabank to startup
timeout 20
popd

echo DID PARABANK START??? Hit key when done ...
pause
call soatest-appcoverage.bat
echo ** Removing old Coverage data
call erase /Q target\jtest\runtime_coverage*
call soatest-appcoverage2.bat

rem **** Manual Testing
echo ** Removing old Coverage data
call erase /Q target\jtest\runtime_coverage*

echo ** PERFORM MANUAL TESTING (set BUILD-ID to %BUILD_ID% and download/save report.xml in this directory)
echo set the following values in the report download page
echo   Project: Parabank
echo   Build Id: %BUILD_ID%
echo   Session Tag: ManualTest
echo 
echo Hit any key when 'done'

pause
call manual-post.bat

pushd %PARABANK-TOMCAT%\bin
call shutdown.bat
popd

echo ** DONE-DONE

