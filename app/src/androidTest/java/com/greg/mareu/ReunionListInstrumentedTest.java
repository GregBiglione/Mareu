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
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.greg.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
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
    public void check_ErrorMessage_ifAddAboutEditText_CharSequence_under3()
    {
        onView(withId(R.id.add_reunion))
                .perform(click());
        onView(withId(R.id.addAbout))
                .perform(typeText("F"), closeSoftKeyboard());
        onView(withId(R.id.create))
                .perform(click());
        onView(withId(R.id.addAbout))
                .check(matches(hasErrorText("Entrer un titre (3-30 caratères)")));
    }

    @Test
    public void check_ErrorMessage_ifAddAbout_CharSequence_upper30()
    {
        onView(withId(R.id.add_reunion))
                .perform(click());
        onView(withId(R.id.addAbout))
                .perform(typeText("abcdefghijklmnopqrstuvwxyzazertyui"), closeSoftKeyboard());
        onView(withId(R.id.create))
                .perform(click());
        onView(withId(R.id.addAbout))
                .check(matches(hasErrorText("Entrer un titre (3-30 caratères)")));
    }

    @Test
    public void check_ErrorMessage_ifAddDateEditText_isEmpty(){
        onView(withId(R.id.add_reunion))
                .perform(click());
        onView(withId(R.id.addAbout))
                .perform(typeText("abcd"), closeSoftKeyboard());
        onView(withId(R.id.create))
                .perform(scrollTo(), click());
        onView(withId(R.id.addDateEdit))
                .check(matches(hasErrorText("Sélectionner une date")));
    }

    @Test
    public void check_ErrorMessage_ifDate_beforeToday(){
        onView(withId(R.id.add_reunion))
                .perform(click());
        onView(withId(R.id.addAbout))
                .perform(typeText("abcd"), closeSoftKeyboard());
        onView(allOf(withId(R.id.addDateEdit)))
                .perform(doubleClick()); //ok jusqu'ici
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 5, 5));
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.create))
                .perform(scrollTo(), click());
        onView(withId(R.id.addDateEdit))
                .check(matches(hasErrorText("Impossible de choisir une date antérieure à aujourd'hui")));
    }

    @Test
    public void check_ErrorMessage_ifAddStartTimeEdit_isEmpty(){
        onView(withId(R.id.add_reunion))
                .perform(click());
        onView(withId(R.id.addAbout))
                .perform(typeText("abcd"), closeSoftKeyboard());
        onView(withId(R.id.create))
                .perform(scrollTo(), click());
        onView(withId(R.id.addStartTimeEdit))
                .check(matches(hasErrorText("Sélectionner une heure de début")));
    }

    @Test
    public void check_ErrorMessage_ifAddEndTimeEdit_isEmpty(){
        onView(withId(R.id.add_reunion))
                .perform(click());
        onView(withId(R.id.addAbout))
                .perform(typeText("abcd"), closeSoftKeyboard());
        onView(withId(R.id.create))
                .perform(scrollTo(), click());
        onView(withId(R.id.addEndTimeEdit))
                .check(matches(hasErrorText("Sélectionner une heure de fin")));
    }

    //heures choisies non sélectionnées
    @Test
    public void check_ErrorMessage_ifEndHour_isLowerThan_startHour(){
        onView(withId(R.id.add_reunion))
                .perform(click());
        onView(withId(R.id.addAbout))
                .perform(typeText("abcd"), closeSoftKeyboard());
        onView(withId(R.id.addStartTimeEdit))
                .perform(scrollTo(), doubleClick());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(13, 45));
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.addEndTimeEdit))
                .perform(scrollTo(),doubleClick());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(11, 42));
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.create))
                .perform(scrollTo(), click());
        onView(withId(R.id.addEndTimeEdit))
                .check(matches(hasErrorText("Heure de fin incorrecte")));
    }

    @Test //non défini dans AddReunion
    public void check_ErrorMessage_ifNoRoom_isSelected(){
        onView(withId(R.id.add_reunion))
                .perform(click());
        onView(withId(R.id.addAbout))
                .perform(typeText("Miaou"), closeSoftKeyboard());
        onView(allOf(withId(R.id.addDateEdit)))
                .perform(doubleClick()); //ok jusqu'ici
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 6, 4));
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.addStartTimeEdit))
                .perform(scrollTo(), doubleClick());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(13, 45)); // met 8h45 par défaut
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.addEndTimeEdit))
                .perform(scrollTo(),doubleClick());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(17, 45)); //met 8h45 par défaut
        onView(withId(android.R.id.button1))
                .perform(click());

        onView(withId(R.id.addParticipantsEdit))
                .perform(scrollTo(), click())
                .perform(click());
        onData(anything()).atPosition(2).atPosition(5).atPosition(9)
                .perform(click());
        onView(withText("Ok"))
                .perform(click());
        ////Click on validation button
        onView(withId(R.id.create))
                .perform(scrollTo(), click());
        onView(withId(R.id.spinnerRoom))// SEule erreur codée différemment ds autres certainement la cause du fail du test
                .check(matches(hasErrorText("Salle non sélectionnée")));
    }

    //affiche le spinner au lieu de la checkbox
    @Test
    public void check_ErrorMessage_ifNoneParticipant_selected(){
        onView(withId(R.id.add_reunion))
                .perform(click());
        onView(withId(R.id.addAbout))
                .perform(typeText("Miaou"), closeSoftKeyboard());
        onView(allOf(withId(R.id.addDateEdit)))
                .perform(doubleClick()); //ok jusqu'ici
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 6, 4));
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.addStartTimeEdit))
                .perform(scrollTo(), doubleClick());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(13, 45)); // met 8h45 par défaut
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.addEndTimeEdit))
                .perform(scrollTo(),doubleClick());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(17, 45)); //met 8h45 par défaut
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.spinnerRoom))
                .perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class))))
                .atPosition(5)
                .perform(click());
        onView(withId(R.id.create))
                .perform(scrollTo(), click());
    }

    @Test
    public void addReunion_and_check_ifContainOneMoreElement_afterAdd(){
        //COUNT ITEMS
        onView(withId(R.id.recycler_view))
                .check(withItemCount(ITEMS_COUNT_AFTER_DELETE_TEST));

        //ADD ITEM
        //Click on add button
        onView(withId(R.id.add_reunion))
                .perform(click());

        //Write Miaou on editText
        onView(withId(R.id.addAbout))
                .perform(typeText("Miaou"), closeSoftKeyboard());

        //Click on select a date button
        onView(allOf(withId(R.id.addDateEdit)))
                .perform(doubleClick()); //ok jusqu'ici

        //DatePicker shown, need to define it
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 6, 4));

        //Click on "ok" button in the DatePicker
        onView(withId(android.R.id.button1))
               .perform(click());

        //Select a start hour
        onView(withId(R.id.addStartTimeEdit))
              .perform(scrollTo(), doubleClick());

        //HourPicker shown, need to define a beginning hour
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(13, 45)); // met 8h45 par défaut

        //Click on "ok" button in the TimePicker
        onView(withId(android.R.id.button1))
                .perform(click());

        ///Select a start hour
        onView(withId(R.id.addEndTimeEdit))
               .perform(scrollTo(),doubleClick());

        //HourPicker shown, need to define a finish hour
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(17, 45)); //met 8h45 par défaut

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
                .perform(scrollTo(), click())
                .perform(click());

        //N'affiche pas les participants mais celle des salles
        //Items selection
        onData(anything()).atPosition(2).atPosition(5).atPosition(9)
                 .perform(click());

        //Click on "ok" button
        onView(withText("Ok"))
                .perform(click());

        ////Click on validation button
        onView(withId(R.id.create))
                .perform(scrollTo(), click());

        // CHECK IF LIST CONTAIN ONE MORE ITEM AFTER ADD

        //COUNT AFTER ADD
        onView(withId(R.id.recycler_view))
                .check(withItemCount(ITEMS_COUNT_AFTER_DELETE_TEST+1));

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

    @Test
    public void  checkFilterByDate_isDisplayed() {
        onView(withId(R.id.main_menu))
                .perform(click());
        onView(anyOf(withText("Par date"), withId(R.id.by_date))).perform(click()) //test fail alors que le clic se fait
                .perform(click());
        //onView(withId(R.id.dialogStartDateEdit))
        //        .perform(doubleClick());
        //onView(withId(android.R.id.button1))
        //        .perform(click());
    }

    @Test
    public void  checkFilterByRoom_isDisplayed() {
        onView(withId(R.id.main_menu))
                .perform(click());
        onView(anyOf(withText("Par salle"), withId(R.id.by_room))).perform(click()) //test fail alors que le clic se fait
                .perform(click());
        //onView(withId(android.R.id.button1))
        //        .perform(click());
    }

}
