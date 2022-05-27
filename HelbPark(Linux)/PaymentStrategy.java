

public interface PaymentStrategy
{
  final int DEFAULT_BIKE_PRICE=10;
  final int DEFAULT_CAR_PRICE=20;
  final int DEFAULT_VAN_PRICE=30;
  public int getTotalAfterReduction();
}
