#!/bin/bash
#mvn clean compile assembly:single
set -e
rm -f testfile testpty expected testoutput
export TERM=vt100
cat expected.in |sed 's%HOMEDIR%'$PWD'%' >expected
echo "this is the test file" >testfile
(
 echo "this is written by the device" 
sleep 5;
)|
socat PTY,link=testpty  STDIO >/dev/null&
sleep 1
java -cp target/cnconsole-0.0.1-SNAPSHOT-jar-with-dependencies.jar cnconsole.tests.EndToEndTest testpty >testoutput
if diff -u expected testoutput
 then
	echo test ok
 else
	exit 1
 fi
rm -f testfile testpty expected testoutput
