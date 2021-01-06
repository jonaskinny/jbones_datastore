echo %JAVA_HOME%
echo off
call setenv_1.8.0_73
set CLASSPATH=%CLASSPATH%;.\deploy\jbones_datastore-config.jar;.\deploy\jbones_datastore.jar;
echo using classpath ...
echo %CLASSPATH%

"%JAVA_HOME%\bin\java" -classpath %CLASSPATH% org.jbones.datastore.Main

pause
