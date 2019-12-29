package com.example.kursovoi;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.junit.Test;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
public class BooksTest {
    @Rule
    public ActivityTestRule<Books> mActivityRule = new ActivityTestRule<>(Books.class);
    @Test
    public void checkDescription() {
        //Espresso.onView(ViewMatchers.withId(R.id.listView));
        Espresso.onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(1).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.description)).check(ViewAssertions.matches(withText("Название книги : " + "Метро 2035" + "\n" + "Жанр : " + "роман" + "\n" + "Автор : " + "Дмитрий Глуховский" + "\n" +
                "Город : " + "Москва" + "\n" + "Издательство : " + "Издательство АСТ" + "\n" + "Год : " + "2015" + "\n" + "Количество страниц : " + "384" + "\n" + "ISBN : " + "978-5-17-090538-6")));
    }

    @Test
    public void update() {
        //Espresso.onView(ViewMatchers.withId(R.id.listView));
        Espresso.onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.updateBook)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.bookEditUpdate)).perform(ViewActions.replaceText("Сезон гроз 1"));
        Espresso.onView(ViewMatchers.withId(R.id.BtnUpdate)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.description)).check(ViewAssertions.matches(withText("Название книги : " + "Сезон гроз 1" + "\n" + "Жанр : " + "фантастический роман" + "\n" + "Автор : " + "Анджей Сапковский" + "\n" +
                "Город : " + "Москва" + "\n" + "Издательство : " + "Издательство АСТ" + "\n" + "Год : " + "2017" + "\n" + "Количество страниц : " + "384" + "\n" + "ISBN : " + "978-5-17-090538-6")));
    }
}