package com.helb.helbpark;

public class StrategyPercentagePrice implements PaymentStrategy
{
    private int price;
    public StrategyPercentagePrice(String vehicleType, int percentage)
    {
        if (vehicleType.equals("bike"))
            price=(percentage/100)*DEFAULT_BIKE_PRICE;
        else if (vehicleType.equals("car"))
            price=(percentage/100)*DEFAULT_CAR_PRICE;
        else if (vehicleType.equals("van"))
            price=(percentage/100)*DEFAULT_VAN_PRICE;
    }

    @Override
    public int getTotalAfterReduction()
    {
        return 0;
    }
}
