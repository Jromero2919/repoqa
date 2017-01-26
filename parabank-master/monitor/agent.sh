#!/bin/bash
BASE_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
AGENT_FILE="$BASE_DIR/agent.jar"
AGENT_SETTINGS="$BASE_DIR/agent.properties"
RUNTIME_DATA="$BASE_DIR/runtime_coverage"
if [ -e "$AGENT_FILE" ] ; 
then
    echo 'Add this Java VM args to your process:'
    echo '---------------------------------------------------'
    echo "-javaagent:\"$AGENT_FILE\"=settings=\"$AGENT_SETTINGS\",runtimeData=\"$RUNTIME_DATA\""
    echo '---------------------------------------------------'
else
    echo "File $AGENT_FILE does not exist!"
fi
read -p 'Press any key to continue . . . '
exit 0
