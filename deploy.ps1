$tomcatPath = "C:\Users\rakeshbalati\OneDrive\Desktop\project\software\apache-tomcat-10.1.55-windows-x64\apache-tomcat-10.1.55"
$webappDest = "$tomcatPath\webapps\food_delivery"
$projectRoot = "c:\Users\rakeshbalati\OneDrive\Desktop\project\JDBC\SERVER\food_delivery"

Write-Host "Stopping any running Tomcat processes..."
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

Write-Host "Preparing deployment files..."
$webInf = "$projectRoot\src\main\webapp\WEB-INF"
if (!(Test-Path "$webInf\classes")) { New-Item -ItemType Directory -Path "$webInf\classes" -Force | Out-Null }
if (!(Test-Path "$webInf\lib")) { New-Item -ItemType Directory -Path "$webInf\lib" -Force | Out-Null }

Write-Host "Copying compiled classes and libraries..."
Copy-Item -Path "$projectRoot\build\classes\*" -Destination "$webInf\classes" -Recurse -Force
Copy-Item -Path "$projectRoot\lib\*" -Destination "$webInf\lib" -Recurse -Force

Write-Host "Cleaning up old deployment in Tomcat..."
if (Test-Path $webappDest) { Remove-Item -Path $webappDest -Recurse -Force }

Write-Host "Deploying new version to Tomcat webapps..."
Copy-Item -Path "$projectRoot\src\main\webapp" -Destination $webappDest -Recurse -Force

Write-Host "Starting Tomcat Server..."
Start-Process -FilePath "$tomcatPath\bin\startup.bat" -WindowStyle Normal

Write-Host "Deployment complete! Application should be available at http://localhost:8080/food_delivery"
