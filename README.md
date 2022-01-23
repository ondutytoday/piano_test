# Piano test project

###Информация по приложению:
- Приложение запускается в классе **PianoApplication** 
- Для удобства работы с рестконтроллером в приложение встроен сваггер. <br>
Доступен после запуска приложения (в случае локального запуска) по адресу http://localhost:8080/swagger-ui/index.html
- Чтобы задать директорию, необходимо отправить post запрос /path, содержащий в теле **полный** путь
- Вывод из первой части задания пишется в консоль в виде лога
- Проверяется только добавление файлов с новым именем, изменение существующих не проверяется, т.к. это вроде бы не требуется <br>
(т.е. если по какой-то причине будет добавлен файл с именем, которое уже было учтено в статистике, то данный файл не будет учтён)

###Общая информация:
- Использование фреймворка spring boot обусловлено только тем, что мне он наиболее знаком.
