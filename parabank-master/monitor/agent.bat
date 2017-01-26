@echo off
setlocal enabledelayedexpansion
set BASE_DIR=%cd%
set AGENT_FILE=%BASE_DIR%\agent.jar
set AGENT_SETTINGS=%BASE_DIR%\agent.properties
set RUNTIME_DATA=%BASE_DIR%\runtime_coverage
set VM_ARGS=-javaagent:"%AGENT_FILE%"=settings="%AGENT_SETTINGS%",runtimeData="%RUNTIME_DATA%"
if exist %AGENT_FILE% (
    echo Add this Java VM args to your process:
    echo ---------------------------------------------------
    echo %VM_ARGS%
    echo ---------------------------------------------------
) else (
    echo File %AGENT_FILE% does not exist^^!
)
pause
