set PATH="..\TestScannerParser\TestLanguage\src\main\CCompiler\tcc";%PATH%
echo off
cmd /c cls
tcc program.c && (program.exe) || (echo Compile failed)
echo.
echo.
pause
exit