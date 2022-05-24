package com.helb.helbpark;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ParkingView
{
    public static void display(Parking parking)
    {
        Stage window = new Stage();
        window.setTitle("Parking View : "+parking.getNumber());
        window.initModality(Modality.APPLICATION_MODAL);
        Label title = new Label("Emplacement : "+parking.getNumber());

        Label state = new Label();
        if (parking.isParkingFree())
            state.setText("Status : Libre");
        else
            state.setText("Status : Occupe");


        HBox hBoxType = new HBox(10);
        Label vehicleType = new Label("Type vehiule : "+parking.getVehicleType());
        String[] types = {"default : "+parking.getVehicleType(),"van","bike","car"};
        ComboBox typeChoice = new ComboBox<>(FXCollections.observableArrayList(types));
        typeChoice.getSelectionModel().selectFirst();
        typeChoice.setOnAction(event -> {
            if(typeChoice.getValue()!= types[0])
                parking.setVehicleType(typeChoice.getValue()+"");
        });
        hBoxType.getChildren().addAll(vehicleType,typeChoice);

        HBox hBoxPlate = new HBox(10);
        Label plateNumber = new Label("Immatriculation : "+parking.getPlateNumber());
        Button btnEditPlate = new Button("Editer");
        btnEditPlate.setOnAction(event -> {
            String updatePlate = editPlateView();
            if(updatePlate!=null && updatePlate!="")
                parking.setPlateNumber(updatePlate);
        });
        hBoxPlate.getChildren().addAll(plateNumber,btnEditPlate);

        Label totalPayment = new Label("Total a payer : "+ parking.getTotalToPay() +" €");
        Button releaseParking = new Button("Liberer l'emplacement");
        releaseParking.setOnAction(event -> {
            //generation de code promo + reçu
            CinemaReduction cinemaReduction = new CinemaReduction(parking.getVehicleType());
            ReceiptGenerator.createReceipt(parking, cinemaReduction);
            parking.initializeParking();
            window.close();
        });


        VBox layout = new VBox(30);
        layout.setPadding(new Insets(70));
        layout.getChildren().addAll(title,state,hBoxType,hBoxPlate,totalPayment,releaseParking);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();


        //return parking;
    }

    private static String editPlateView()
    {
        Stage window = new Stage();
        window.setTitle("Edit Plate Number");
        window.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label("Modifier la plaque :");
        TextField editPlate = new TextField();
        Button validate = new Button("Valider");

        validate.setOnAction(event -> {
            window.close();
        });



        VBox layout = new VBox(30);
        layout.setPadding(new Insets(70));
        layout.getChildren().addAll(label,editPlate,validate);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return editPlate.getText();
    }
}
