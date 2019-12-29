package com.example.kursovoi;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void onClick1() {
        Espresso.onView(ViewMatchers.withId(R.id.editText2)).perform(ViewActions.replaceText("Г55 Метро 2035 : [роман]/ Дмитрий Глуховский. - Москва: Издательство АСТ, 2015. - 384 с. ISBN 978-5-17-090538-6"));
        Espresso.onView(ViewMatchers.withId(R.id.button2)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.textView)).check(ViewAssertions.matches(withText("Название книги : " + "Метро 2035" + "\n" + "Жанр : " + "роман" + "\n" + "Автор : " + "Дмитрий Глуховский" + "\n" +
                "Город : " + "Москва" + "\n" + "Издательство : " + "Издательство АСТ" + "\n" + "Год : " + "2015" + "\n" + "Количество страниц : " + "384" + "\n" + "ISBN : " + "978-5-17-090538-6")));
    }
}