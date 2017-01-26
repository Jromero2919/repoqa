REM bat script for App Coverage Demo (Manual Test Prep)
@echo off

echo ====================================================
echo Preparing for Manual Testing of build.id=%BUILD_ID%
echo ====================================================

echo **1/2** Removing old Coverage data
call erase /Q target\jtest\runtime_coverage*
echo **2/2** Configuring Coverage Agent
call mvn jtest:collectStaticCoverage jtest:configureAgent

echo =====================================================
echo Finished Manual Testing prep for build.id=%BUILD_ID%
echo =====================================================
