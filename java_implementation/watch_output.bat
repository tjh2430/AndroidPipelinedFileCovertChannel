@ECHO OFF
:loop
	cls
	tail -n 30 %1
	timeout /t 2	
goto :loop