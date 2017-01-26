REM bat script for App Coverage Demo (SOAtest)
@echo off

echo =====================================================
echo Running Functional Tests against build.id=%BUILD_ID%
echo =====================================================
set PATH=%PATH%;c:\bin;c:\Program Files\Parasoft\SOAtest\9.9

pushd soatest
call copy /Y templatesettings.properties localsettings.properties
echo build.id=%BUILD_ID% >> localsettings.properties
echo property.dtp.project=Parabank >> localsettings.properties

echo **1/4** Importing project into SOAtest
soatestcli -data . -import Bookstore

echo **2/4** Running SOAtest
soatestcli -config "ApplicationCoverage.properties" -data . -resource Bookstore -report report2 -localsettings localsettings.properties

echo **3/4** Uploading report to DTP
curl.exe -k --user admin:admin -F file=@report2/report.xml https://localhost:8082/api/v2/dataCollector
popd

echo **4/4** Processing Coverage
call mvn jtest:loadCoverage -Djtest.config="builtin://Calculate Application Coverage" -Dproperty.build.id=%BUILD_ID% -Dproperty.dtp.project=Parabank -Dproperty.report.coverage.images="FT;AllTests;ST"  -Dproperty.session.tag="${scontrol_branch}-win32_x86_64-2"

echo =====================================================
echo Finished testing build.id=%BUILD_ID%
echo =====================================================
