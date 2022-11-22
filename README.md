# AvitoInternshipWeatherApp
## Архитектура
[MainViewModel](https://github.com/Mechamanul/AvitoInternshipWeatherApp/blob/master/app/src/main/java/com/mechamanul/avitointernshipweatherapp/ui/MainViewModel.kt) содержит результат запроса к [WeatherAPI](https://www.weatherapi.com/). Scope этой вьюмодели распространяется на всё приложение.
[AppRepository](https://github.com/Mechamanul/AvitoInternshipWeatherApp/blob/master/app/src/main/java/com/mechamanul/avitointernshipweatherapp/domain/AppRepository.kt) - интерфейс, содержащий функции доступные для вызова из вьюмоделей.

Решил не переусложнять архитектуру UseCaseами, поэтому вьюмодели напрямую получают инстанс repository в конструктор.
## Компиляция
Для компиляции необходимо создать файл apikey.properties с ключом WEATHER_API="Апи ключ с сайта weatherapi"
## Скриншоты
<p float="left">
      <img alt="Initial Screen" src="https://user-images.githubusercontent.com/59366804/203367915-6d55b809-c748-4397-bdd2-419388ba5272.jpg" height="350px" width="160px" />
      <img alt="Initial Screen" src="https://user-images.githubusercontent.com/59366804/203378021-12519e0b-0002-4366-b2ea-d7f45a63f886.jpg"  height="350px" width="160px"/>
      <img alt="Initial Screen" src="https://user-images.githubusercontent.com/59366804/203378517-7fe511e3-f363-466b-a19c-efbbee3bfac8.jpg"  height="350px" width="160px"/>
      <img alt="Initial Screen" src="https://user-images.githubusercontent.com/59366804/203378789-ad9a4911-8774-40ac-973f-e24d6e3f1084.jpg" height="350px" width="160px" />
</p>
