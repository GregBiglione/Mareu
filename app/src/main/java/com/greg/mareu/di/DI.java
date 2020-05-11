package com.greg.mareu.di;

import com.greg.mareu.service.DummyReunionApiService;
import com.greg.mareu.service.ReunionApiService;

public class DI
{
    public static ReunionApiService service = new DummyReunionApiService();

    public static ReunionApiService getReunionApiService()
    {
        return service;
    }

    /**
     * Api service used for tests
     */

    public static ReunionApiService getNewInstanceApiService()
    {
        return new DummyReunionApiService();
    }
}
