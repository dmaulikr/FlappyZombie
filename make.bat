@echo off
rd /s /q bin
md bin
javac -sourcepath src -d bin src/ve/com/edgaralexanderfr/fz/Main.java -Xlint
xcopy "src/res" "bin/res" /e /i /y /s
pause