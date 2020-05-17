package com.greg.mareu;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.greg.mareu.di.DI;
import com.greg.mareu.reunion_list.ListReunionActivity;
import com.greg.mareu.service.ReunionApiService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ReunionListInstrumentedTest {

    private static int ITEMS_COUNT = 15;
    private int mPosition = 1;

    private ListReunionActivity mListActivity;
    private ReunionApiService mApiService;

    @Rule
    public ActivityTestRule<ListReunionActivity> mActivityTestRule = new ActivityTestRule<>(ListReunionActivity.class);

    @Before
    public void setUp(){
        mListActivity = mActivityTestRule.getActivity();
        assertThat(mListActivity, notNullValue());
        mApiService = DI.getReunionApiService();
    }

    @Test
    public void reunionList_shouldNotBeEmpty(){
        onView(withId(R.id.recycler_view))
                .check(matches(hasMinimumChildCount(1)));
    }

    /*@Test
    public void reunionList_deleteAction_shouldRemoveOneItem(){
        //Count items
        onView(withId(R.id.recycler_view))
                .check(withItemCount(ITEMS_COUNT));
        //Delete an item
        onView(withId(R.id.recycler_view))
                .perform();
        //Count after remove
        onView(withId(R.id.recycler_view))
                .check(withItemCount(ITEMS_COUNT-1));
    }*/

}
