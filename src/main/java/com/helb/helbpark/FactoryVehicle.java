package com.helb.helbpark;

public final class FactoryVehicle
{
    private static volatile FactoryVehicle instance = null;

    private FactoryVehicle()
    {
        super();
    }

    public static FactoryVehicle getInstance()
    {
        if(FactoryVehicle.instance==null)
        {
            FactoryVehicle.instance = new FactoryVehicle();
        }
        return FactoryVehicle.instance;
    }

    public Vehicle createVehicle(String vehicleType, String plateNumber)
    {
        if (vehicleType.equals("bike"))
            return new Bike(plateNumber);
        else if (vehicleType.equals("car"))
            return new Car(plateNumber);
        else if (vehicleType.equals("van"))
            return new Van(plateNumber);
        else
        {
            System.out.println("Impossible de creer un "+vehicleType);
            return null;
        }

        //throw new Exception("Impossible de creer un "+vehicleType);

    }
}
