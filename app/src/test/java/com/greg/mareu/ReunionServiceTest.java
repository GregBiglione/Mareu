package com.greg.mareu;

import com.greg.mareu.di.DI;
import com.greg.mareu.model.Reunion;
import com.greg.mareu.service.DummyReunionGenerator;
import com.greg.mareu.service.ReunionApiService;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 *  Unit test on Reunion service
 */

@RunWith(JUnit4.class)
public class ReunionServiceTest {

    ReunionApiService service;

    @Before
    public void setUp(){
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getReunionWithSuccess(){
        List<Reunion> reunions = service.getReunions();
        List<Reunion> expectedReunions = DummyReunionGenerator.DUMMY_REUNION;
        assertThat(reunions, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedReunions.toArray()));
    }

    @Test
    public void deleteReunionWithSuccess(){
        Reunion reunionToDelete = service.getReunions().get(0);
        service.deleteReunion(reunionToDelete);
        assertFalse(service.getReunions().contains(reunionToDelete));
    }

    public static Date setReunionDate(int year, int month, int day){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }

    @Test
    public void addReunionWithSuccess(){

        long id = 16;
        String reunionAvatar = "https://placeimg.com/640/480/any" + System.currentTimeMillis();
        String aboutReunion = "Miaou";
        Date day = setReunionDate(2054, 11, 19);
        String startTime = "13h44";
        String endTime = "13h44";
        String room = "Salle Lion";
        String participants = "seth@lamzone.com, baastet@lamazone.com, zeus@lamzone.com";
        Reunion reunionToAdd = new Reunion(id, reunionAvatar, aboutReunion, day, startTime, endTime, room, participants);
        service.createReunion(reunionToAdd);
        assertTrue(service.getReunions().contains(reunionToAdd));
    }

    @Test
    public void reunionByDates(){
        List<Reunion> actualReunionByDate = service.getReunions();
        Reunion reunionByDate = actualReunionByDate.get(13);
        List<Reunion> expectedReunionByDate = service.getReunionByDate(setReunionDate(2020, 8, 8),
                setReunionDate(2020, 8, 11));
        assertThat(expectedReunionByDate.size(), is(1));
        assertTrue(expectedReunionByDate.contains(reunionByDate));
    }

    @Test
    public void reunionsByRoom(){
        List<Reunion> actualReunionsByRoom = service.getReunions();
        Reunion reunionByRoom = actualReunionsByRoom.get(1);
        List<Reunion> expectedReunionsByRoom = service.getReunionByRoom("Salle Capricorne");
        assertThat(expectedReunionsByRoom.size(), is(1));
        assertTrue(expectedReunionsByRoom.contains(reunionByRoom));
    }
}