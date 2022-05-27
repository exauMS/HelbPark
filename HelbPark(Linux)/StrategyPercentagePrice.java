

public class StrategyPercentagePrice implements PaymentStrategy
{
    private double price;
    public StrategyPercentagePrice(String vehicleType, double percentage)
    {
        if (vehicleType.equals("bike"))
            price=DEFAULT_BIKE_PRICE-((percentage/100)*DEFAULT_BIKE_PRICE);
        else if (vehicleType.equals("car"))
            price=DEFAULT_CAR_PRICE-((percentage/100)*DEFAULT_CAR_PRICE);
        else if (vehicleType.equals("van"))
            price=DEFAULT_VAN_PRICE-((percentage/100)*DEFAULT_VAN_PRICE);
    }

    @Override
    public int getTotalAfterReduction()
    {
        return (int)price;
    }
}
