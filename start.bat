@echo off
REM Start Spring Boot backend
echo starting backend...
start cmd /k "cd backend\chess_back && mvn spring-boot:run"

REM Start Flutter frontend
echo starting frontend...
start cmd /k "cd frontend\chess_front && flutter run -d chrome --web-port 8081"

echo Both frontend and backend are starting...