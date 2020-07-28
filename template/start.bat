@echo off
title PROGRAM

set lib0=.\lib

java -DPROGRAM -Xms64m -Xmx256m   -cp ${lib} ${mainClass}   2>err.log
pause
