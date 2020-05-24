package com.greg.mareu.utils;

import android.view.View;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class SetErrorView {
    public static Matcher<View> withError(final String expected){
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view)
            {
                if (!(view instanceof EditText)){
                    return false;
                }
                EditText editText = (EditText) view;
                return editText.getError().toString().equals(expected);
            }
            @Override
            public void describeTo(Description description) {

            }
        };
    }
}
