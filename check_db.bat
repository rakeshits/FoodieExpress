@echo off
set MYSQL="C:\Program Files\MySQL\MySQL Server 8.1\bin\mysql.exe"
%MYSQL% -u root -proot1234@ food_app -e "SHOW TABLES;" > db_check.txt 2>&1
%MYSQL% -u root -proot1234@ food_app -e "SELECT restaurantid, name, cuisinetype, isactive FROM restaurant;" >> db_check.txt 2>&1
%MYSQL% -u root -proot1234@ food_app -e "SELECT userid, username, email FROM user LIMIT 5;" >> db_check.txt 2>&1
echo Done >> db_check.txt
