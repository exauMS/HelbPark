package com.helb.helbpark;

public class StrategyHalfPrice implements PaymentStrategy
{
    private int price;

    public StrategyHalfPrice(String vehicleType)
{
    if (vehicleType.equals("bike"))
        price=DEFAULT_BIKE_PRICE/2;
    else if (vehicleType.equals("car"))
        price=DEFAULT_CAR_PRICE/2;
    else if (vehicleType.equals("van"))
        price=DEFAULT_VAN_PRICE/2;
}
    @Override
    public int getTotalAfterReduction() {
        return price;
    }
}
