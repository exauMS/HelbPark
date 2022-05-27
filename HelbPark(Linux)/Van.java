

public class Van extends Vehicle
{
    private String plateNumber;

    public Van (String plateNumber)
    {
        this.plateNumber = plateNumber;
    }
    @Override
    public String getType() {
        return "van";
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
