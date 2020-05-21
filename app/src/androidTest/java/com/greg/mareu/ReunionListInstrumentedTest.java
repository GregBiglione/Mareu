package com.greg.mareu;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.greg.mareu.di.DI;
import com.greg.mareu.reunion_list.ListReunionActivity;
import com.greg.mareu.service.ReunionApiService;
import com.greg.mareu.utils.DeleteViewAction;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.greg.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
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
    private static int ITEMS_COUNT_AFTER_DELETE_TEST = 14;
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

    @Test
    public void reunionList_deleteAction_shouldRemoveOneItem(){
        //Count items
        onView(withId(R.id.recycler_view))
                .check(withItemCount(ITEMS_COUNT));
        //Delete an item
        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(mPosition, new DeleteViewAction()));
        //Count after remove
        onView(withId(R.id.recycler_view))
                .check(withItemCount(ITEMS_COUNT-1));
    }

    @Test
    public void addReunion_and_check_ifContainOneMoreElement_afterAdd(){

        //COUNT ITEMS
        //onView(withId(R.id.recycler_view))
        //        .check(withItemCount(ITEMS_COUNT_AFTER_DELETE_TEST));

        //ADD ITEM
        //Click on add button
        onView(withId(R.id.add_reunion))
                .perform(click());

        //Write Miaou on editText
        onView(withId(R.id.addAbout))
                .perform(typeText("Miaou"), closeSoftKeyboard());

        //Click on select a date button
        onView(withId(R.id.addDateEdit))
                .perform(click())
                 .perform(click());//double clic ?

        //DatePicker shown, need to define it
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 5, 20));

        //Click on "ok" button in the DatePicker
         onView(withId(android.R.id.button1))
                .perform(click());

         //Select a start hour
         onView(withId(R.id.addStartTimeEdit))
                 .perform(scrollTo(),click())
                 .perform(click()); //double clic ?

         //HourPicker shown, need to define a beginning hour
         onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                 .perform(PickerActions.setTime(13, 45));

         //Click on "ok" button in the TimePicker
         onView(withId(android.R.id.button1))
                 .perform(click());

        ////Select a start hour
        onView(withId(R.id.addEndTimeEdit))
                .perform(scrollTo(),click())
                .perform(click()); //double clic ?

         //HourPicker shown, need to define a finish hour
         onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                 .perform(PickerActions.setTime(14, 45));

         //Click on "ok" button in the TimePicker
         onView(withId(android.R.id.button1))
                 .perform(click());

         //Click on spinner
         onView(withId(R.id.spinnerRoom))
                 .perform(scrollTo(), click());

         //Select item at position #5 and click it
         onData(allOf(is(instanceOf(String.class))))
                 .atPosition(5)
                 .perform(click());

//test ok jusqu'ici
         //Click on select participants
         onView(withId(R.id.addParticipantsEdit))
                 .perform(click());

         //N'affiche pas les participants mais celle des salle
         //Items selection
         onData(anything()).atPosition(2).atPosition(5).atPosition(9)
                 .perform(click());

         //Click on "ok" button
         //onView(withText("Ok"))
         //        .perform(click());

       ////Click on validation button
       //onView(withId(R.id.create))
       //        .perform(scrollTo(), click());

        // CHECK IF LIST CONTAIN ONE MORE ITEM AFTER ADD

        //COUNT AFTER ADD
        //onView(withId(R.id.recycler_view))
        //        .check(withItemCount(ITEMS_COUNT_AFTER_DELETE_TEST+1));
    }

    @Test
    public void checkParticipantsList_isDisplayed_onClick_onAReunion_andQuitItClickingOnOk(){
        //Click on an item
        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(mPosition, click()));

        //Check if Dialog box is displayed
        onView(withText("Ok"))
                .check(matches(isDisplayed()));

        //Quit clicking on "Ok"
        onView(withId(android.R.id.button1))
                .perform(click());
    }

}
