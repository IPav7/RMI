javac -d . .\src\Server.java .\src\Client.java .\src\IMath.java
start rmiregistry
start java -cp . Server
sleep 3
java -cp . Client
pause