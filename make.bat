@echo off
rd /s /q bin
md bin
javac -sourcepath src -d bin src/ve/com/edgaralexanderfr/fz/Main.java
pause