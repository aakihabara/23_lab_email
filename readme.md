<p align = "center">МИНИСТЕРСТВО НАУКИ И ВЫСШЕГО ОБРАЗОВАНИЯ<br>
РОССИЙСКОЙ ФЕДЕРАЦИИ<br>
ФЕДЕРАЛЬНОЕ ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ<br>
ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ ВЫСШЕГО ОБРАЗОВАНИЯ<br>
«САХАЛИНСКИЙ ГОСУДАРСТВЕННЫЙ УНИВЕРСИТЕТ»</p>
<br><br><br><br><br><br>
<p align = "center">Институт естественных наук и техносферной безопасности<br>Кафедра информатики<br>Коньков Никита Алексеевич</p>
<br><br><br>
<p align = "center"><br><strong>«Проверка адреса email»</strong><br>01.03.02 Прикладная математика и информатика</p>
<br><br><br><br><br><br><br><br><br><br><br><br>
<p align = "right">Научный руководитель<br>
Соболев Евгений Игоревич</p>
<br><br><br>
<p align = "center">г. Южно-Сахалинск<br>2022 г.</p>
<br><br><br><br><br><br><br><br><br><br><br><br>

<h1 align = "center">Введение</h1>

<p><b>Android Studio</b> — интегрированная среда разработки (IDE) для работы с платформой Android, анонсированная 16 мая 2013 года на конференции Google I/O. В последней версии Android Studio поддерживается Android 4.1 и выше.</p>
<p><b>Kotlin</b> — это кроссплатформенный статически типизированный язык программирования общего назначения высокого уровня. Kotlin предназначен для полного взаимодействия с Java, а версия стандартной библиотеки Kotlin для JVM зависит от библиотеки классов Java, но вывод типов позволяет сделать ее синтаксис более кратким. Kotlin в основном нацелен на JVM, но также компилируется в JavaScript (например, для интерфейсных веб-приложений, использующих React) или собственный код через LLVM (например, для собственных приложений iOS, разделяющих бизнес-логику с приложениями Android). Затраты на разработку языка несет JetBrains, а Kotlin Foundation защищает торговую марку Kotlin.</p>

<br>
<h1 align = "center">Цели и задачи</h1>

<h2 align = "center"><b> Создать хороший UX для пользователей, вводящих адрес электронной почты и пароль при регистрации в приложении. </b> <br>Требования:</h2>

<p>Проверка формата электронной почты. Пример: user@gmail не является действительным адресом электронной почты
Пользовательский интерфейс должен показывать, действителен или нет адрес электронной почты. При необходимости интерфейс должен указать, что не так с адресом
Автозаполнение и проверка доступности домена. Пользователи часто опечатываются при вводе адреса. Например, указывают неправильно доменное имя (gmail.con вместо gmail.com)
Проверка пароля. Нет ограничения на вводимые символы. Есть ограничение минимальной и максимальной длины
При необходимости, интерфейс должен указать, что неправильно
Проверить, что заполнены все поля, и указать, какое именно не заполнено</p>
<p>Для автозаполнения необходимо Проверить существование введённого домена. Указать, что неправильно в введённом имени. Предложить Автозаполнение доменного имени самыми вероятными и популярными доменными именами. Пример: если пользователь вводит «user@», то продолжениями могут быть «user@gmail.com», «user@yahoo.com» и т.д. Если пользователь уточняет «user@g», то продолжениями могут быть популярные домены, начинающиеся с «g». Например: «user@gmail.com», «user@gmail.co.uk»</p>



<h1 align = "center">Решение</h1>

<p>За основу я взял файл из 9 лабораторной работы</p>

<p>Изменил поле почты с EditText на AutoCompleteTextView, чтобы к нему можно было добавлять подсказки</p>

<p>Добавил домены для электронной почты и переменную для проверки корректности данных:</p>

```kotlin
    private var somethingWrong = false
    private var emailCheck = arrayOf("gmail.com", "rambler.ru", "mail.ru", "yandex.ru", "yahoo.com",)
```

<p>Изменил слушателя для кнопки входа:</p>

```kotlin
 loginButton.setOnClickListener{

            somethingWrong = false

            if(email.text.toString().isEmpty()){
                emailError.text = "Email field is empty"
                somethingWrong = true
            } else {
                for (end in emailCheck) {
                    if (email.text.toString().endsWith("@$end")) {
                        somethingWrong = false
                        emailError.text = ""
                        break
                    }
                    else{
                        emailError.text = "Your email address is incorrect"
                        somethingWrong = true
                    }
                }
            }



            if(password.text.toString().isEmpty()){
                somethingWrong = true
                passwordError.text = "Password field is empty"
            } else {
                if(password.text.toString().length < 6){
                    passwordError.text = "Password is too small (less than 6 letters)"
                    somethingWrong = true
                } else if (password.text.toString().length > 14){
                    passwordError.text = "Password is too big (more than 14 letters)"
                    somethingWrong = true
                } else {
                    passwordError.text = ""
                }
            }

            if(!somethingWrong){
                if(email.text.toString() == correctEmail && password.text.toString() == correctPassword){
                    Toast.makeText(this, "You are successfully logged in", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Email or password incorrect", Toast.LENGTH_SHORT).show()
                }
            }
        }
```

<p>Добавил слушателя для поля электронной почты, чтобы при наличии в строке @, отображались возможные варианты записи:</p>

```kotlin
        var adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, emailCheck)
        email.setAdapter(adapter)

        email.addTextChangedListener(object : TextWatcher {


            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().contains("@")) {
                    val emailList = mutableListOf<String>()
                    val domainText = s.toString().substring(s.toString().indexOf("@") + 1)
                    val temp = emailCheck.filter { it.startsWith(domainText) }
                    for (end in temp) {
                        val email = s.toString().replaceAfter("@", end)
                        emailList.add(email)
                    }
                    adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_dropdown_item_1line, emailList)
                    email.setAdapter(adapter)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //nothing
            }

            override fun afterTextChanged(s: Editable?) {
                //nothing
            }

        })
```



<h1 align = "center">Вывод</h1>
<p>По итогу проделанной лабораторной работы, я познакомился с AutoCompleteTextView и на практике научился его применять.</p>
