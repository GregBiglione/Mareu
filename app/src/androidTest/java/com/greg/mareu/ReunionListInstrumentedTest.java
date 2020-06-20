package com.greg.mareu;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.rule.ActivityTestRule;

import com.greg.mareu.di.DI;
import com.greg.mareu.reunion_list.ListReunionActivity;
import com.greg.mareu.service.ReunionApiService;
import com.greg.mareu.utils.DeleteViewAction;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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

    private void addReunion(){
        onView(withId(R.id.add_reunion))
                .perform(click());
    }

    @Test
    public void A_ReunionList_shouldNotBeEmpty() {
        onView(withId(R.id.recycler_view))
                .check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void B_ReunionList_deleteAction_shouldRemoveOneItem(){
        onView(withId(R.id.recycler_view))
                .check(withItemCount(ITEMS_COUNT))
                .perform(actionOnItemAtPosition(mPosition, new DeleteViewAction()))
                .check(withItemCount(ITEMS_COUNT-1));
    }

    @Test
    public void C_AddReunion_and_check_ifContainOneMoreElement_afterAdd(){
        onView(withId(R.id.recycler_view))
                .check(withItemCount(ITEMS_COUNT-1));
        addReunion();
        onView(withId(R.id.addAbout))
                .perform(typeText("Miaou"), closeSoftKeyboard());
        onView(allOf(withId(R.id.addDateEdit)))
                .perform(doubleClick());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 12, 21));
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.addStartTimeEdit))
                .perform(scrollTo(), doubleClick());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(setTime(13, 30));
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.addEndTimeEdit))
                .perform(scrollTo(),doubleClick());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(setTime(17, 55));
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.spinnerRoom))
                .perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class))))
                .atPosition(4)
                .perform(click());
        onView(withId(R.id.addParticipantsEdit))
                .perform(scrollTo(), click())
                .perform(click());
        onData(anything()).atPosition(2).atPosition(5).atPosition(9)
                .perform(click());
        onView(withText("Ok"))
                .perform(click());
        onView(withId(R.id.create))
                .perform(scrollTo(), click());
        onView(withId(R.id.recycler_view))
                .check(withItemCount(ITEMS_COUNT));
    }

    @Test
    public void D_Check_ErrorMessage_ifAddAboutEditText_CharSequence_under3()
    {
        onView(withId(R.id.add_reunion))
                .perform(click());
        onView(withId(R.id.addAbout))
                .perform(typeText("F"), closeSoftKeyboard());
        onView(withId(R.id.create))
                .perform(scrollTo(), click());
        onView(withId(R.id.addAbout))
                .check(matches(hasErrorText("Entrer un titre (3-30 caratères)")));
    }

    @Test
    public void E_check_ErrorMessage_ifAddAbout_CharSequence_upper30()
    {
        onView(withId(R.id.add_reunion))
                .perform(click());
        onView(withId(R.id.addAbout))
                .perform(typeText("abcdefghijklmnopqrstuvwxyzazertyui"), closeSoftKeyboard());
        onView(withId(R.id.create))
                .perform(scrollTo(), click());
        onView(withId(R.id.addAbout))
                .check(matches(hasErrorText("Entrer un titre (3-30 caratères)")));
    }

    @Test
    public void F_Check_ErrorMessage_ifAddDateEditText_isEmpty(){
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
    public void G_Check_ErrorMessage_ifDate_beforeToday(){
        onView(withId(R.id.add_reunion))
                .perform(click());
        onView(withId(R.id.addAbout))
                .perform(typeText("Miaou"), closeSoftKeyboard());
        onView(allOf(withId(R.id.addDateEdit)))
                .perform(doubleClick()); //ok jusqu'ici
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 3, 4));
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.addStartTimeEdit))
                .perform(scrollTo(), doubleClick());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(setTime(13, 10)); // met 8h45 par défaut
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.addEndTimeEdit))
                .perform(scrollTo(),doubleClick());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(setTime(13, 58)); //met 8h45 par défaut
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.spinnerRoom))
                .perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class))))
                .atPosition(4)
                .perform(click());
        onView(withId(R.id.addParticipantsEdit))
                .perform(scrollTo(), click())
                .perform(click());
        onData(anything()).atPosition(2).atPosition(5).atPosition(9)
                .perform(click());
        onView(withText("Ok"))
                .perform(click());
        onView(withId(R.id.create))
                .perform(scrollTo(), click());
        onView(withId(R.id.addDateEdit))
                .check(matches(hasErrorText("Sélectionner une date")));
    }

    @Test
    public void H_Check_ErrorMessage_ifAddStartTimeEdit_isEmpty(){
        onView(withId(R.id.add_reunion))
                .perform(click());
        onView(withId(R.id.addAbout))
                .perform(typeText("Miaou"), closeSoftKeyboard());
        onView(withId(R.id.create))
                .perform(scrollTo(), click());
        onView(withId(R.id.addStartTimeEdit))
                .check(matches(hasErrorText("Sélectionner une heure de début")));
    }

    @Test
    public void I_Check_ErrorMessage_ifAddEndTimeEdit_isEmpty(){
        onView(withId(R.id.add_reunion))
                .perform(click());
        onView(withId(R.id.addAbout))
                .perform(typeText("Miaou"), closeSoftKeyboard());
        onView(withId(R.id.create))
                .perform(scrollTo(), click());
        onView(withId(R.id.addEndTimeEdit))
                .check(matches(hasErrorText("Sélectionner une heure de fin")));
    }

    @Test
    public void J_Check_ErrorMessage_ifEndHour_isLowerThan_startHour(){
        addReunion();
        onView(withId(R.id.addAbout))
                .perform(typeText("Miaou"), closeSoftKeyboard());
        onView(withId(R.id.addDateEdit))
                .perform(doubleClick());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 12, 17));
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.addStartTimeEdit))
                .perform(doubleClick());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(setTime(22, 53));
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.addEndTimeEdit))
                .perform(doubleClick());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(setTime(15, 00));
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.addEndTimeEdit))
                .check(matches(hasErrorText("Heure de fin incorrecte")));
    }

    @Test
    public void K_Check_ErrorMessage_ifNoneParticipant_selected(){
        addReunion();
        onView(withId(R.id.addAbout))
                .perform(typeText("Miaou"), closeSoftKeyboard());
        onView(allOf(withId(R.id.addDateEdit)))
                .perform(doubleClick()); //ok jusqu'ici
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 12, 17));
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.addStartTimeEdit))
                .perform(scrollTo(), doubleClick());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(setTime(13, 20)); // met 8h45 par défaut
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.addEndTimeEdit))
                .perform(scrollTo(),doubleClick());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(setTime(14, 45)); //met 8h45 par défaut
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
    public void L_CheckParticipantsList_isDisplayed_onClick_onAReunion_andQuitItClickingOnOk(){
        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(mPosition, click()));
        onView(withText("Ok"))
                .check(matches(isDisplayed()));
        onView(withId(android.R.id.button1))
                .perform(click());
    }

    @Test
    public void M_CheckFilterByDate_isDisplayed() {
        onView(withId(R.id.main_menu))
                .perform(click());
        onView(withText("Par date"))
                .perform(click());
        onView(withId(R.id.dialogStartDateEdit))
                 .perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 6, 15));
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.dialogEndDateEdit))
                .perform(doubleClick());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 6, 25)); //Fail ici du au problème dans le pickDate
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.okDateDialog))
                .perform(click());
        onView(withId(R.id.recycler_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void N_CheckFilterByRoom_isDisplayed() {
        onView(withId(R.id.main_menu))
                .perform(click());
        onView(withText("Par salle"))
                .perform(click());
        onView(withId(R.id.dialogRoomSpinner))
                .perform(click());
        onData(allOf(is(instanceOf(String.class))))
                .atPosition(4)
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());
        onView(withId(R.id.okRoomDialog))
                .perform(click());
        onView(withId(R.id.recycler_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void O_CheckFullList_clickingOnAll_inMenu(){
        onView(withId(R.id.main_menu))
                .perform(click());
        onView(withText("Toutes"))
                .perform(click());
        onView(withId(R.id.recycler_view))
                .check(matches(isDisplayed()));
    }
}
