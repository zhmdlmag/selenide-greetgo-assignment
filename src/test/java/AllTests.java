import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.by;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class AllTests {


    //Переменные для Авторизации и Регистрации
    String username = "magzhan";
    String email = "mako7379@gmail.com";
    String password = "magzhan";

    @Test //Метод по открытию сайта
    public void openSite(){
        open("https://angular.realworld.io/"); //открыть сайт

        $(".article-preview") //Для выполнения функции, сайт должен прогурзить статью
                .shouldBe(Condition.visible);
    }

    @Test //Метод по авторизации
    public void signIn(){
        openSite();

        $(byText("Sign in")) //Нажать на Sign in
                .click();

        $x("//button") //Кнопка должна пргорузиться, чтобы выполнить дальнейшие операции
                .shouldBe(Condition.visible);

        $x("//input[@placeholder='Email']") //Заполнить поле Email
                .setValue(email)
                .shouldBe(value(email));

        $x("//input[@placeholder='Password']") //Заполнить поле Password
                .setValue(password)
                .shouldBe(value(password));

        $x("//button") //Нажать кнопку, чтобы подтвердить
                .click();

        $(".navbar").shouldHave(Condition.text(username)); //Проверка, что авторизация прошла успешно, проверяем через навигационную панель


    }

    @Test //Метод по регистрации
    public void signUp(){
        openSite();
        $(byText("Sign up"))
                .click();
        $x("//button")
                .shouldBe(Condition.visible);

        $x("//input[@placeholder='Username']")
                .setValue(username)
                .shouldBe(value(username));

        $x("//input[@placeholder='Email']")
                .setValue(email)
                .shouldBe(value(email));

        $x("//input[@placeholder='Password']")
                .setValue(password)
                .shouldBe(value(password));

        $x("//button")
                .click();

        $(".navbar").shouldHave(Condition.text(username));



    }


    // 1. Зайти на главную страницу (https://angular.realworld.io) и проверить, что на странице есть 12 превью статей
    @Test
    public void checkArticles(){
        openSite();

        // Чтобы начать проверку, нужно удостовериться, что сайт прогрузил больше 0 статьи
        $$(".article-preview")
                .shouldHave(CollectionCondition
                        .sizeGreaterThan(0));

        //Переменная с количеством статей
        int articleCount = elements(".article-preview").size();

        if (articleCount!=12) System.out.println("Тут не 12 статей, а " + articleCount);
        else System.out.println("Тут 12 статей");

        closeWebDriver();
    }


    // 2. Зарегистрироваться на сайте, перейти в настройки пользователя и проверить, что email идентичен тому, с помощью которого мы регистрировались
    @Test
    public void checkEmail(){
        openSite();
        signIn();

        //Переход в настройки
        $(byText("Settings"))
                .click();

        //Проверка того, что email совпадает
        $x("//input[@placeholder='Email']")
                .shouldBe(value(email));

        sleep(3000);

    }


    // 3. Добавить статью из Global Feed в избранное, перейти в профиль и избранные статьи, проверить что статья есть в списке избранного
    @Test
    public void globalFeed(){

        openSite();
        signIn();


        $(byText("Global Feed")).click();

        $$(".article-preview")
                .shouldHave(CollectionCondition
                        .sizeGreaterThan(0));

        $x("//button[contains(@class,'btn')]").click();
        sleep(1000);

        $(byText(username)).click();

        $(byText("Favorited Posts")).click();

        //Страница должна показать, что есть заголовок понравившейся статьи
        $(".article-preview h1")
                .shouldHave(Condition.text("If we quantify the alarm, we can get to the FTP pixel through the online SSL interface!"));



    }


// 4. Расписать в формате тест-кейса путь пользователя с регистрацией, созданием статьи и проверкой, что статья есть в Global Feed. Добавить тест-кейс в readme.md

//        Название: Создание статьи в https://angular.realworld.io
//        Предусловия: Наличие браузера и подключения в интернет
//
//        Тест-кейс для регистрации нового пользователя:
//        Шаг 1: Открыть страницу регистрации.
//        Шаг 2: Заполнить поля "Username", "Email", "Password"
//        Шаг 3: Нажать кнопку "Sign up"
//        Шаг 5: После перенаправления на главную страницу, нажать кнопку "New Article" в navbar
//        Шаг 6: Заполнить поля все необходимые поля "Article Title", "What's this article about", "Write your article", "Enter tags"
//        Шаг 7: Нажать кнопку "Publish Article"
//        Шаг 8: Перейти во вкладку Global Feed
//        Шаг 9: Проверить наличие новой статьи по автору
//
//        Ожидаемый результат: После успешной регистрации и создания статьи, в Global Feed должна быть видна наша статья


    // 5. Автоматизировать тест-кейс с регистрацией и созданием статьи
    @Test
    public void testCase(){


        String someValue = "abcdefg"; //Переменная для заполнения полей

        openSite();
        signIn();

        $(byText("New Article")).shouldBe(visible).click();
        $(byText(" New Article ")).shouldBe(visible).click();

        $x("//button[@type='button']")
                .shouldBe(visible);

        $x("//input[@placeholder='Article Title']")
                .setValue(someValue);

        $x("//input[@placeholder='Write your article (in markdown)']")
                .setValue(someValue)
                .shouldBe(value(someValue));

        $x("//textarea[@placeholder='What's this article about?']")
                .setValue(someValue)
                .shouldBe(value(someValue));

        $x("//input[@placeholder='Enter tags']")
                .setValue(someValue)
                .shouldBe(value(someValue));

        $("button").click();

        $(byText("Global Feed")).shouldBe(visible).click();

        // Шаг 10: Проверить наличие новой статьи по автору
        $(".article-preview .author")
                .shouldHave(Condition.text(username));

        sleep(3000);

        //$(byText("Global Feed")).click();


    }


    // 6. Проверить что в настройках пользователя (https://angular.realworld.io/settings) при наведении на кнопку “Or click here to logout” кнопка имеет заливку цвета #b85c5c или rgb(184,92,92)
    @Test
    public void btnColor(){
        String color = "#b85c5c"; //Переменная с цветом

        openSite(); //Открыть сайт
        signIn(); //Авторизация

        $(byText("Settings")) //Перейти в настройки
                .click();

        $(".btn-outline-danger").hover(); //Наведение на кнопку

        $(".btn-outline-danger") //Проверка на наличие нужного цвета
                .shouldHave(Condition.cssValue("background-color", color));

//        $(byText(" Or click here to logout. ")).shouldHave(Condition
//                .cssValue("background-color", color));

    }


}
