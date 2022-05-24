package com.helb.helbpark;

public class Car extends Vehicle
{
    private String plateNumber;

    public Car (String plateNumber)
    {
        this.plateNumber = plateNumber;
    }

    @Override
    public String getType()
    {
        return "car";
    }

    @Override
    public void setPlateNumber(String newPlateNumber)
    {
        plateNumber = newPlateNumber;
    }

    @Override
    public String getPlateNumber()
    {
        return plateNumber;
    }
}
