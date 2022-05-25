

public class Bike extends Vehicle
{
    private String plateNumber;

    public Bike (String plateNumber)
    {
        this.plateNumber = plateNumber;
    }

    @Override
    public String getType() {
        return "bike";
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
