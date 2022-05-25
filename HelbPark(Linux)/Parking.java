

import javafx.scene.control.Button;

import java.util.ArrayList;

public class Parking
{
    private Button button;
    private boolean parkingState;
    private String plateNumber;
    private String vehicleType;
    private int number;
    private int totalToPay;

    private ArrayList<Integer> pricesList; // contenant les infos de paiment venant de la classe "Payment"

    public Parking(int number)
    {
        this.number = number;
        button = new Button(""+this.number);
        button.setStyle("-fx-base : lightgreen");
        parkingState = true;
        plateNumber="";
        vehicleType="";
    }


    public void initializeParking()
    {
        button.setStyle("-fx-base : lightgreen");
        parkingState = true;
        plateNumber="";
        vehicleType="";
        button.setText(""+number);
    }

    public boolean isParkingFree()
    {
        return parkingState;
    }

    public void goToParking(Vehicle v)
    {
        vehicleType = v.getType();
        plateNumber =v.getPlateNumber();
        parkingState = false;
        update();

    }

    private void update()
    {
        if (isParkingFree()==false)
        {
            if (vehicleType.equals("car"))
            {
                button.setStyle("-fx-base : red");
                button.setText(""+number+"\n"+plateNumber);
            }
            else if (vehicleType.equals("bike"))
            {
                button.setStyle("-fx-base : blue");
                button.setText(""+number+"\n"+plateNumber);
            }
            else if (vehicleType.equals("van"))
            {
                button.setStyle("-fx-base : purple");
                button.setText(""+number+"\n"+plateNumber);
            }
            else
            {
                button.setStyle("-fx-base : lightgreen");
                button.setText(""+number);
            }

        }
    }

    public Button getButton()
    {
        return button;
    }

    public int getNumber()
    {
        return number;
    }
    public String getPlateNumber() {return plateNumber;}
    public String getVehicleType(){return vehicleType;}

    public void setPlateNumber(String plateNumber)
    {
        this.plateNumber = plateNumber;
        update();
    }

    public void setVehicleType(String vehicleType)
    {
        this.vehicleType = vehicleType;
        update();
    }

    public int getTotalToPay()
    {
        return totalToPay;
    }


    private void setTotalToPay(int totalToPay)
    {
        this.totalToPay = totalToPay;
    }

    public ArrayList<Integer> getPricesList()
    {
        return pricesList;
    }

    public void setPricesList(ArrayList<Integer> pricesList)
    {
        this.pricesList = pricesList;
        int priceAfterReduction = 1;
        setTotalToPay(this.pricesList.get(priceAfterReduction));
    }
}
