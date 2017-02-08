package com.teamharambe.agris.aidll.Models;

/**
 * Created by Agris on 12.11.2016..
 */

public class ModelWithId
{
    public String model;
    public String id;

    public ModelWithId(String model, String id)
    {
        this.model = model;
        this.id = id;
    }

    @Override
    public String toString() {
        return model.toString();
    }

}
