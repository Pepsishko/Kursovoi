package com.example.kursovoi;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

public class SearchTest {
    @Rule
    public ActivityTestRule<Search> mActivityRule = new ActivityTestRule<>(Search.class);
    @Test
    public void searchBTNClick() {
        Espresso.onView(ViewMatchers.withId(R.id.nameEdit)).perform(ViewActions.replaceText("Анджей Сапковский"));
        Espresso.onView(ViewMatchers.withId(R.id.searchBtn)).perform(ViewActions.click());
        Espresso.onData(anything()).inAdapterView(withId(R.id.searchBo)).atPosition(0).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.description)).check(ViewAssertions.matches(withText("Название книги : " + "Сезон гроз 1" + "\n" + "Жанр : " + "фантастический роман" + "\n" + "Автор : " + "Анджей Сапковский" + "\n" +
                "Город : " + "Москва" + "\n" + "Издательство : " + "Издательство АСТ" + "\n" + "Год : " + "2017" + "\n" + "Количество страниц : " + "384" + "\n" + "ISBN : " + "978-5-17-090538-6")));
    }

}