
## STATUS 
![CircleCI](https://circleci.com/gh/Viacheslav21/backend_time.svg?style=shield&circle-token=4b9aedafef98f500bb90389a3d738b42986d19c3(https://circleci.com/gh/Viacheslav21/backend_time)
)

## Tools 
1. MySqlServer
2. Tomcat 7 or upper
3. Java 7 or upper
4. Maven

## DB Setting 
1. Create DB with name backend-db
2. login = root, password = root
3. Hibernate will create all tables which we need

## Run an application
Нам неообходимо  скомпелировать  проект в  maven  и запустить command в терминале : **java -jar backend.jar**
or use a compiler 

  server.port: 8181
 
* Address of application:
  http://localhost:8181/hello/
  
  
Запрос к contacts должен возвращать контакты из таблицы БД contacts. Параметр nameFilter обязателен. 
В него передаётся регулярное выражение.В возвращаемых данных не должно быть записей, 
в которых contacts.name совпадает с регулярным выражением.

Массив контактов возвращается в json формате
 
Принимает обязательный параметр **nameFilter** который содержит регулярное выражение по которому необходимо отсортировать контакты. В программе предусмотрен порог для выгрузки из БД - не более *1 миллиона* за запрос. Если в БД больше порогового значения записей тогда данные разобьются на страницы и вернется JSON в виде:
```
{
  "contacts":[],
  "totalCount": ,
  "currentPage": ,
  "totalPage":
}
```
* **totalCount** - количество обработаных записей
* **currentPage** - текущая страница
* **totalPage** - всего страниц

## Почему не обрабатывает более миллиона за раз?
*По условию задания необходимо фильтрацию производить в памяти Java не прибегая к возможностям SQL.*
Самый оптимальный вариант было хранить все данные в памити java.  
Например, в БД имеется 10 миллионов записей. Я выполняю даный код загрузки из БД:
```
Iterable<Contact> contacts = contactDao.findAll();
```
