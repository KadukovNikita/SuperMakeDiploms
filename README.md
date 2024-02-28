                                    Make_Diploms System(СИСТЕМА АВТОМАТИЧЕСКОЙ ВЁРСТКИ ДИПЛОМОВ)
Эта программа является внутривузовским проектом и решает проблемы связанные с печатью дипломов и грамот для соревнований, проводимых в ИЖГТУ. На данном этапе разработки программа не обладает красивым интерфейсом, но выполняет все фунцкии, которые на неё возложены.

                                              РЕАЛИЗОВАННАЯ ФУНКЦИОНАЛЬНОСТЬ
                                              
1. На НАЧАЛЬНОЙ СТРАНИЦЕ мы можем увидеть РАБОЧЕЕ ОКНО. Здесь находится список участников соревнования, для которых в дальнейшем будут распечатаны дипломы. Здесь же представлена вся функциональность для печати дипломов:
   ![image](https://github.com/KadukovNikita/SuperMakeDiploms/assets/86073191/308b911a-662c-408c-945f-31efcaa5f62c)

2. Нажав на кнопку ИЗМЕНИТЬ мы можем поменять информацию об участнике, если в данных содержится ошибка:
![image](https://github.com/KadukovNikita/SuperMakeDiploms/assets/86073191/6eac467b-7f95-4d68-9f04-9d9f39e265fb)

3. Мы также можем удалить этого участника, перед удалением возникнет предупреждение:
   ![image](https://github.com/KadukovNikita/SuperMakeDiploms/assets/86073191/4fee72d9-f026-4e15-930e-31ce070b886e)

4. Нажав на кнопку ДОБАВИТЬ УЧАСТНИКА мы видим перед собой ту же форму, что и при изменении данных об участнике, но в этом случае поля формы не заполнены. Поля формы проходят валидацию, поэтому невозможно добавить некорректные данные:
   ![image](https://github.com/KadukovNikita/SuperMakeDiploms/assets/86073191/a1899773-0c6e-4ff3-9b24-adaa30fa5af0)

5. Загружать участников в рабочее окно можно так же при помощи загрузки файлов с таблицей. Для этого нужно, чтобы искомый файл имел правильную структуру: в таблице имели столбцы с заголовками surname, name, patronymic, coach_name, coach_surname, coach_patronymic, place. Вот пример таблицы, которая может быть загружена на сайт без возникновения ошибок:
  ![image](https://github.com/KadukovNikita/SuperMakeDiploms/assets/86073191/35d3a75f-118d-419b-b510-18c4ec3a644c)

   Загружать можно файлы как в формате CSV, так и XLSX.
![image](https://github.com/KadukovNikita/SuperMakeDiploms/assets/86073191/61d6da30-5833-42a3-a414-b03af11dab2d)

7. Есть вариант загрузки участников в рабочее окно при помощи еще одного способа:
![image](https://github.com/KadukovNikita/SuperMakeDiploms/assets/86073191/6c3c041d-be84-4c41-a6bf-5245aeb59eaa)

В данном случае наша таблица может иметь произвольную структуру, все что от нас требуется это загрузить файл со всеми участниками соревнования на сайт, а потом отметить номера столбцов, которые содержат ту или иную информацю - при этом если какой-то информации в таблице нет (например информация о тренере не указана), то нужно указать 0 в поле

7. В приложении реализована "База данных". В первой версии проекта (при самом первом коммите) было взаимодействие с базой данных MySQL, но этот проект предназначался для внутривузовских целей, поэтому пришлось упростить процесс развёртывания приложения, убрав взаимодествие с БД
Однако вместо БД на сайте реализовано взаимодействие с файлом participants.txt, в котором данные об участниках хранятся в json-формате.
![image](https://github.com/KadukovNikita/SuperMakeDiploms/assets/86073191/e3976745-48d2-441a-984c-adb3c70c7d01)

8. В базу данных можно загружать участников всеми теми же способами, что в рабочее окно. Отличие заключается в том, что в базе данных информация хранится на постоянной осонове, а в рабочем окне в сессии клиента, а потому может затираться через некоторое время. Если вы не уверены, что сразу распечатаете всех участников, то лучше вначале добавить всех их в БД, а из БД уже в рабочее окно:
![image](https://github.com/KadukovNikita/SuperMakeDiploms/assets/86073191/387e8b4a-f611-41d8-90ef-3ec8415374f2)

9. Базу данных можно импортировать в рабочее окно - а также наоборот, рабочее окно можно переносить в БД:
    ![image](https://github.com/KadukovNikita/SuperMakeDiploms/assets/86073191/56f2c475-d933-4850-9125-18b886b8a67c)

10. После нажатия на кнопку СОЗДАТЬ ДИПЛОМЫ, в папке проекта создаются дипломы для всех участников. Пока они не имеют интуитивно понятных названий. Дипломы создаются в формате docx. В скором времени будет добавлен формат pdf.
![image](https://github.com/KadukovNikita/SuperMakeDiploms/assets/86073191/a4c01adb-4caf-4e73-9cef-5ae6403d1ac5)

11. Пока есть 1 шаблон для формирования скелета диплома, но в скором времени появятся и другие дизайны. Вот пример получившегося диплома:
![image](https://github.com/KadukovNikita/SuperMakeDiploms/assets/86073191/33ee4a20-cd62-4613-a7bb-e6007c48efde)

                                              ИСПОЛЬЗУЕМЫЕ ТЕХНОЛОГИИ

    В проекте были использованы следующие технологии:

    * Spring (Core, Boot, AOP)
   
    * Thymeleaf
   
    * Библиотеки Apache.Poi и OpenCSV для работы с документами Office.
   
    * Работа с JSON







