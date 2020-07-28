#!/bin/bash
# TransAdapterI1

export threadname=$(cat ./_threadname)
export lib0=./lib

PID=`ps -ef|grep D${threadname}|grep -v grep|awk '{print $2}'`
if [ -z $PID ];then
    java -D$threadname -Xms64m -Xmx256m   -cp ${lib} ${mainClass}  >/dev/null 2>err.log &
else
    echo "The program $threadname has been running.Please stop it firstly."
fi
