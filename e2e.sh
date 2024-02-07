#!/bin/bash
#mvn clean compile assembly:single
set -e
rm -f testfile testpty expected testoutput
export TERM=vt100
cat expected.in |sed 's%HOMEDIR%'$PWD'%' >expected
echo "this is the test file" >testfile
(
 echo "this is written by the device" 
sleep 1;
)|
socat PTY,link=testpty  STDIO >/dev/null&
java -cp target/cnconsole-0.0.1-SNAPSHOT-jar-with-dependencies.jar cnconsole.tests.EndToEndTest testpty >testoutput 2>&1 
sleep 1
if diff -u expected testoutput
 then
	echo test ok
 else
	exit 1
 fi
rm -f testfile testpty expected testoutput
