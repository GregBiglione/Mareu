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
}