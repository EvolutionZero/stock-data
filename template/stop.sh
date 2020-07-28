#!/bin/bash
# ${mainJar}

PID=`ps -ef|grep '${mainJar}'|grep -v grep|awk '{print $2}'`
if [ -z $PID ];then
    echo "The program ${mainJar} has been stoped."
else
    kill $PID
    echo "The program ${mainJar} is being killed, please wait for 10 seconds..."
    sleep 10

    PID=`ps -ef|grep '${mainJar}'|grep -v grep|awk '{print $2}'`
    if [ $PID ];then
        kill -9 $PID
    fi
    echo "Kill the program ${mainJar} successfully."
fi