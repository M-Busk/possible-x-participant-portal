@ECHO OFF
SETLOCAL enableextensions enabledelayedexpansion

SET PATH=C:\Projects\POSSIBLE\tools\jdk17\bin

ECHO.
ECHO build 
ECHO.


CD..

call gradlew.bat backend:cleanBuild
call gradlew.bat backend:bootRun

ECHO.
ECHO DONE
ECHO.
ECHO.
PAUSE
ECHO.

