REM bat script for App Coverage Demo (Manual Test Post-processing)
@echo off

echo ===========================================================
echo Post processing Manual Test session for build.id=%BUILD_ID%
echo ===========================================================

echo **1/2** Uploading report to DTP
curl.exe -k --user admin:admin -F file=@report.xml https://localhost:8082/api/v2/dataCollector

echo **2/2** Processing Coverage
call mvn jtest:loadCoverage -Djtest.config="builtin://Calculate Application Coverage" -Dproperty.build.id=%BUILD_ID% -Dproperty.dtp.project=Parabank -Dproperty.report.coverage.images="Manual;AllTests" -Dproperty.session.tag=ManualTest

echo =====================================================
echo Finished Manual testing of build.id=%BUILD_ID%
echo =====================================================
