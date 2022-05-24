package com.helb.helbpark;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class HPController implements Initializable {

    @FXML
    private final int GRID_CASES_NUMBER=20;
    private final int GRID_CASE_HEIGHT=60;
    private final int GRID_CASE_WIDTH=112;

    private final int GRID_ROW_NUMBER=4;
    private final int GRID_COL_NUMBER=5;
    private int casesCounter = 0;//grid cases
    private int lineCounter = 0;// file lines
    private int maxFileNumberOfLines=0;
    private ArrayList<Parking> parkingList = new ArrayList<>();
    private List<String> fileExtensionsList;
    private File vehiclesDataFile;
   private Timeline timeline, dateTimeline;
    private boolean exit=false;



    @FXML
    private GridPane gridParkings = new GridPane();
    @FXML
    private Button btnAddFile = new Button();
    @FXML
    private Button startButton = new Button();
    @FXML
    private Label date = new Label();
    @FXML
    protected void onStartButtonClick()
    {
        initializeGrid();
        initializeParkings();
        //realTimeClock();

    }


    @FXML
    protected void onStartVehicleStreamButtonClick()
    {
        vehiclesDataTreatment();
    }

    private void vehiclesDataTreatment()
    {
        //On recupere dans un string une ligne precise dans le fichier, puis on le scinde en tableau
        String stringVehicleData = fileReader(vehiclesDataFile, lineCounter);
        String[] tabVehicleData = stringVehicleData.split(",");// tabVehicleData structure : [time(0),vehicleType(1),plateNumber(2)]

        setTimeline(tabVehicleData);

    }

    private void setTimeline(String[] tabVehicleData)
    {
        if (timeline!=null)
            timeline.stop();
        timeline = new Timeline(new KeyFrame(Duration.seconds(Integer.parseInt(tabVehicleData[0])),event -> {

            Vehicle vehicle;
            FactoryVehicle factoryVehicle = FactoryVehicle.getInstance();

            if (tabVehicleData.length == 3)//security
            {
                if (tabVehicleData[1].equals("voiture"))
                {
                    vehicle = factoryVehicle.createVehicle("car",tabVehicleData[2]);
                    addVehicle(vehicle);
                }
                else if (tabVehicleData[1].equals("moto"))
                {
                    vehicle = factoryVehicle.createVehicle("bike",tabVehicleData[2]);
                    addVehicle(vehicle);
                }
                else if (tabVehicleData[1].equals("camionette"))
                {
                    vehicle = factoryVehicle.createVehicle("van",tabVehicleData[2]);
                    addVehicle(vehicle);
                }
                else
                    System.out.println("Type de véhicule inconnu...");
                lineCounter++;

                exit=true;
                vehiclesDataTreatment();

            }
        }));
        timeline.setCycleCount(1);
        timeline.play();

    }

    private void addVehicle(Vehicle vehicle)
    {
        for (Parking parking: parkingList)
        {
            if (parking.isParkingFree())
            {
                parking.goToParking(vehicle);
                parking.setPricesList(Payment.getTotal(parking.getVehicleType(), parking.getPlateNumber()));
                break;
            }
        }

    }

    @FXML
    protected void singleFileChooser (ActionEvent event)
    {
        //Permet au user de selectionner un fichier
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Txt Files", fileExtensionsList));
        vehiclesDataFile = fc.showOpenDialog(null);

        if( vehiclesDataFile != null)
        {
            btnAddFile.setText("OK");
            btnAddFile.setStyle("-fx-base : green;");
        }

        //on recupere le nombre de lignes dans le fichier
        try{
            BufferedReader reader = new BufferedReader(new FileReader(vehiclesDataFile.getAbsolutePath()));
            while (reader.readLine() !=null) maxFileNumberOfLines++;
            reader.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
        System.out.println(maxFileNumberOfLines);



    }

    private String fileReader(File file, int lineCounter)
    {
        String path = file.getAbsolutePath();
        String line="";

        //on recupere une ligne precise dans le fichier et on la renvoie
        try{

                if (lineCounter<maxFileNumberOfLines)//security
                {
                    line = Files.readAllLines(Paths.get(path)).get(lineCounter);
                }

        }
        catch(IOException e){
            System.out.println(e);
        }
        return line;
    }

    private void initializeGrid()
    {
        //on initialise la grille en inserant des boutons provenant des parkings
        Parking p;
        for (int i = 0; i < GRID_ROW_NUMBER ; i++)
        {
            for (int j = 0; j < GRID_COL_NUMBER; j++)
            {
                p = new Parking(casesCounter);
                p.getButton().setPrefHeight(GRID_CASE_HEIGHT);
                p.getButton().setPrefWidth(GRID_CASE_WIDTH);
                gridParkings.add(p.getButton(),j,i);
                parkingList.add(p);

                casesCounter++;
            }
            startButton.setDisable(true);
        }

    }

    private void initializeParkings()
    {
        //affichage parking view
        for (Parking parking: parkingList)
        {
            parking.getButton().setOnAction(actionEvent -> {
                ParkingView.display(parking);
                parking.setPricesList(Payment.getTotal(parking.getVehicleType(), parking.getPlateNumber()));//mettre a jour le paiement d'apres les nouvelles infos du vehicule
            });

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        fileExtensionsList = new ArrayList<>();
        fileExtensionsList.add("*.txt");
    }

    public void realTimeClock()
    {
        dateTimeline =  timeline = new Timeline(new KeyFrame(Duration.seconds(1),event -> {
            Date d = Calendar.getInstance().getTime();
            DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String today = simpleDateFormat.format(d);
            date.setText(today);
        }));
        timeline.setCycleCount(-1);
        timeline.play();

    }

}