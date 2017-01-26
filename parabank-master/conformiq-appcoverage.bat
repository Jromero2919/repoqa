REM bat script for App Coverage Demo (Conformiq)
@echo off

set BUILD_ID=Parabank-%date:~10,4%%date:~4,2%%date:~7,2%

echo =====================================================
echo Running Conformiq Functional Tests against build.id=%BUILD_ID%
echo =====================================================
set PATH=%PATH%;c:\bin;c:\Program Files\Parasoft\SOAtest\9.9

echo **1/5** Removing existing coverage data
erase /q target\jtest\runtime_coverage*.data

echo **2/5** Importing project into SOAtest
pushd soatest

call copy /Y templatesettings.properties localsettings.properties
echo build.id=%BUILD_ID% >> localsettings.properties
echo session.tag=Conformiq >> localsettings.properties
echo property.dtp.project=Parabank >> localsettings.properties
erase /q report-conformiq

echo HACK!!!!
curl.exe -k http://localhost:8050/session/stop
curl.exe -k http://localhost:8050/session/start

echo **3/5** Running SOAtest
soatestcli -config "user://Example Configuration" -data . -resource Conformiq-Parabank/all.tst -report report-conformiq -localsettings localsettings.properties

echo HACK!!!!
curl.exe -k http://localhost:8050/session/stop

echo **4/5** Uploading report to DTP
curl.exe -k --user admin:admin -F file=@report-conformiq/report.xml https://localhost:8082/api/v2/dataCollector
popd

echo **5/5** Processing Coverage
call mvn jtest:loadCoverage -Djtest.config="builtin://Calculate Application Coverage" -Dproperty.build.id=%BUILD_ID% -Dproperty.dtp.project=Parabank -Dproperty.report.coverage.images="AllTests;Conformiq;FT" -Dproperty.session.tag="Conformiq"

echo =====================================================
echo Finished testing build.id=%BUILD_ID%
echo =====================================================
