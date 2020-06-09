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

    @Test
    public void addReunionWithSuccess(){
        long id = 16;
        String reunionAvatar = "https://placeimg.com/640/480/any" + System.currentTimeMillis();
        String aboutReunion = "Miaou";
        Date day = new Date(154, 6, 7);
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
        List<Reunion> actualReunionsByDate = service.getReunions();
        Reunion reunionByDate = actualReunionsByDate.get(14);
        List<Reunion> expectedReunionByDate = service.getReunionByDate(new Date(120, 7, 15),
                new Date(120, 7, 22));
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

    //@Test
    //public void reunionsByRoom(){
    //    List<Reunion> actualReunionsByRoom = service.getReunions();
    //    Reunion reunionByRoom1 = actualReunionsByRoom.get(0);
    //    Reunion reunionByRoom2 = actualReunionsByRoom.get(11);
    //    Reunion reunionByRoom3 = actualReunionsByRoom.get(14);
    //    List<Reunion> expectedReunionsByRoom = service.getReunionByRoom("Salle Lion");
    //    assertThat(expectedReunionsByRoom.size(), is(3));
    //    assertTrue(expectedReunionsByRoom.contains(reunionByRoom1));
    //    assertTrue(expectedReunionsByRoom.contains(reunionByRoom2));
    //    assertTrue(expectedReunionsByRoom.contains(reunionByRoom3));
    //}
}