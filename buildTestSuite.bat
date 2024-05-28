rem Build Test Code and Software Under Test using Maven
echo Building Test Code and Software Under Test...
mvn clean install

rem Run Test Code using Maven
echo Running Test Code...
mvn test

rem Check if build and tests were successful
set build_success=True
set tests_pass=True

rem Check if build failed
if %ERRORLEVEL% neq 0 (
    set build_success=False
)

rem Check if Surefire Reports exist and parse test results
set tests_pass=True
for /R target\surefire-reports %%f in (*.xml) do (
    findstr /C:"<failure" "%%f" > nul
    if %ERRORLEVEL% equ 0 (
        set tests_pass=False
        goto :end_loop
    )
)
:end_loop

rem Prepare email content
set subject=Test Status Report
set body=Build succeeded: %build_success%, Tests passed: %tests_pass%

rem Send email using Send-MailMessage cmdlet
powershell.exe -Command "$password = ConvertTo-SecureString 'pioy cwrb lmem bhzy' -AsPlainText -Force; $credential = New-Object System.Management.Automation.PSCredential ('emilynaruto8@gmail.com', $password); Send-MailMessage -To '%recipient_email%' -From 'emilynaruto8@gmail.com' -Subject '%subject%' -Body '%body%' -SmtpServer 'smtp.gmail.com' -Port 587 -Credential $credential -UseSsl -DeliveryNotificationOption OnFailure"

echo Status email sent to %recipient_email%
