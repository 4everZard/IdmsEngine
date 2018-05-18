@echo off
set CP=.
set CP=%CP%;lib/rebate.jar
set CP=%CP%;lib/ojdbc6.jar
set CP=%CP%;./lib/jxlpoi.jar
set CP=%CP%;./lib/SystemConfig.jar
set CP=%CP%;./lib/poi-3.17.jar
set CP=%CP%;./lib/poi-excelant-3.17.jar
set CP=%CP%;./lib/poi-ooxml-3.17.jar
set CP=%CP%;./lib/poi-ooxml-schemas-3.17.jar
set CP=%CP%;./lib/poi-scratchpad-3.17.jar
set CP=%CP%;./lib/poi/xmlbeans-2.3.0.jar
set CP=%CP%;./lib/poi/log4j-1.2.13.jar
set CP=%CP%;./lib/poi/j2ee.jar


set PATH=%PATH%;.\jre\bin\

REM jre1.5\bin\java -version

REM -Xdebug -Xnoagent -Djava.compiler=NONE
java -Xms128M -Xmx2048M -classpath "%CP%;%CLASSPATH%" ca.on.moh.idms.gui.RebateGui %1 %2 %3 %4 %5 %6 %7 %8 %9
