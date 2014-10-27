mongo-calendar-java
===================
Това е сървър за [mongo-calendar-app-web](https://github.com/stormbreakerbg/mongo-calendar-app-web). Базиран е на Jersey и Grizzly и осигурява необходимите за клиента REST услуги.

Организация
-----------
* [Server](/src/main/java/Server.java) е главният клас, който трябва да пуснете за да тръгне приложението
* [TasksService](/src/main/java/service/TasksService.java) съдържа REST услугата и осигурява операциите
* [pom.xml](/pom.xml) съдържа Maven конфигурацията
* [/di](/src/main/java/di) съдържа класове, необходими за dependency injection
* [/utils](/src/main/java/utils) съдържа различни utility класове за приложението
