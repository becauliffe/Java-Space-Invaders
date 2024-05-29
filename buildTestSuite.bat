@echo off
rem Build Test Code and Software Under Test using Maven
echo Building Test Code and Software Under Test...

set /p recipient_email=Enter your email:
set /p password=Enter your password:

set build_success=False
set tests_pass=False

call mvn clean install
if %ERRORLEVEL% neq 0 (
    echo Build failed.
) else (
    echo Build succeeded.
    set build_success=True
)

if "%build_success%"=="True" (
    echo Running Test Code...
    call mvn test
    if %ERRORLEVEL% neq 0 (
        echo Tests failed.
    ) else (
        echo Tests passed.
        set tests_pass=True
    )
)

if "%tests_pass%"=="False" (
    for /R target\surefire-reports %%f in (*.xml) do (
        findstr /C:"<failure" "%%f" > nul
        if %ERRORLEVEL% equ 0 (
            echo Test failures found in %%f.
            set tests_pass=False
        )
    )
)

:send_email
set subject=Test Status Report
set body=Build succeeded: %build_success%, Tests passed: %tests_pass%

powershell.exe -Command "$password = ConvertTo-SecureString '%password%' -AsPlainText -Force; $credential = New-Object System.Management.Automation.PSCredential ('%recipient_email%', $password); Send-MailMessage -To '%recipient_email%' -From '%recipient_email%' -Subject '%subject%' -Body '%body%' -SmtpServer 'smtp.gmail.com' -Port 587 -Credential $credential -UseSsl -DeliveryNotificationOption OnFailure"

echo Status email sent to %recipient_email%
