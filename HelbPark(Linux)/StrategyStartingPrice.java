

public class StrategyStartingPrice implements PaymentStrategy
{
    private int price;
    public StrategyStartingPrice(String vehicleType)
    {
        if (vehicleType.equals("bike"))
            price=DEFAULT_BIKE_PRICE;
        else if (vehicleType.equals("car"))
            price=DEFAULT_CAR_PRICE;
        else if (vehicleType.equals("van"))
            price=DEFAULT_VAN_PRICE;
    }

    @Override
    public int getTotalAfterReduction()
    {
        return price;
    }
}
