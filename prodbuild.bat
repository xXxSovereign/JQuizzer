rem @echo off

echo "Initiating production environment building..."
echo
echo "Building source code..."
echo

rem Ensure we use the correct jdk, as we specifically use corretto 1.8_472

set jdk_name=corretto-1.8.0_472

rem This is hardcoded to IntelliJ's default location for jdks.
setx /m JAVA_HOME %USERPROFILE%\.jdks\%jdk_name%\


echo "DEBUG: java home and user home"
echo %JAVA_HOME%
echo %USERPROFILE%
echo

rem hardcoded for now to be an intellij project (hence "IdeaProjects")
set workdir=%USERPROFILE%\IdeaProjects\JQuizzer
cd %workdir%

echo "DEBUG: workdir"
echo "%workdir%"
echo

rem this CALL bit is very important. Otherwise, control flow transfers to gradlew.bat and terminates.
call .\gradlew.bat :clean :jar
rem gradlew.bat turns @echo off so we must turn in back on again
@echo on

echo "Source Code has been built"
echo

rem Check if prod/ exists, and copy the built jar into the prod environment
set prod_dir=%workdir%\prod

echo "Cleaning old production environment (if applicable)"
echo

IF exist %prod_dir% ( echo "old prod env exists, removing" && rmdir prod /s /q )

mkdir %prod_dir% && echo "DEBUG: New Prod Env Created: %prod_dir%"

echo "Transferring built jar into prod"
echo

copy %workdir%\build\libs\JQuizzer-0-dev.jar %prod_dir%

echo "Jar has been transferred into the production environment"
echo "Cleaning build dir"
echo

rem See above why CALL is so important
call .\gradlew.bat clean
rem gradlew.bat turns @echo off so we must turn in back on again (same as above)
@echo on

echo "Build dir clean."
echo "Copying WinJDK over to production environment"
echo

mkdir %prod_dir%\%jdk_name%

robocopy %JAVA_HOME% "%prod_dir%\%jdk_name%" /S /E

echo "WinJDK has been copied over"
echo "Creating and copying run script (batch)"

set win_script=%prod_dir%\run.bat

echo @echo OFF > %win_script%
echo SET mypath=%%~dp0 >> %win_script%

echo %%mypath:~0,-1%%\%jdk_name%\bin\java.exe -jar JQuizzer-0-dev.jar >> %win_script%

echo pause >> %win_script%

echo "Run Script created successfully"
echo "Copying over default JQuizzerAppCtx"
echo

copy JQuizzerAppCtx.json %prod_dir%

echo "Copying Completed"
echo
echo "Production environment creation is completed"
echo "Note: This environment is only for windows machines, and will not run on linux. The JDK is win specific."


pause
