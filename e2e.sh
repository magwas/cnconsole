#!/bin/bash
#mvn clean compile assembly:single
set -e
export TERM=vt100
cat expected.in |sed 's%HOMEDIR%'$PWD'%' >expected
echo "this is the test file" >testfile
mkfifo testfifo
(echo "this is written by the device"  >testfifo)&
sleep 1
java -cp target/cnconsole-0.0.1-SNAPSHOT-jar-with-dependencies.jar cnconsole.tests.EndToEndTest 1.gcode >testoutput 2>&1
if diff -u expected testoutput
 then
	echo test ok
 else
	exit 1
 fi
rm testfile testfifo expected testoutput
