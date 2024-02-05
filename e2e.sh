#!/bin/bash
#mvn clean compile assembly:single
set -e
export TERM=vt100
cat expected.in |sed 's-HOMEDIR-'$HOME'-' >expected
echo "this is the test file" >testfile
mkfifo testfifo
cat testfifo >testfifo.out&
(echo "this is the test fifo";sleep 5s;echo "not async!") >testfifo&
sleep 1
java -cp target/cnconsole-0.0.1-SNAPSHOT-jar-with-dependencies.jar cnconsole.tests.EndToEndTest param param pamparam >testoutput 2>&1
(echo testfifo;cat testfifo.out;echo EOF) >>testoutput
if diff -u expected testoutput
 then
	echo test ok
 else
	exit 1
 fi
rm testfile testfifo expected testoutput
