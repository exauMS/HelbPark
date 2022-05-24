package com.helb.helbpark;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public abstract class ReceiptGenerator
{

    public static void createReceipt(Parking parking, CinemaReduction cinemaReduction)
    {
        String receipt="";
        String dayOfWeek="";

        Date d = Calendar.getInstance().getTime();
        DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String today = simpleDateFormat.format(d);
        simpleDateFormat = new SimpleDateFormat("ddMMyy");
        String directoryName = simpleDateFormat.format(d);
        String fileName = parking.getPlateNumber();
        //----------

        switch (parking.getPricesList().get(2))// position 2 pour recuperer le numero correspondant au jour de la semaine
        {
            case 1:
                dayOfWeek="dimanche";
                break;
            case 2:
                dayOfWeek="lundi";
                break;
            case 3:
                dayOfWeek="mardi";
                break;
            case 4:
                dayOfWeek="mercredi";
                break;
            case 5:
                dayOfWeek="jeudi";
                break;
            case 6:
                dayOfWeek="vendredi";
                break;
            case 7:
                dayOfWeek="samedi";
                break;
            default:
                dayOfWeek="jour non reconnu";
        }

        if(cinemaReduction.getReductionType().equals("standard"))
            fileName+= "_std";
        else if(cinemaReduction.getReductionType().equals("silver"))
            fileName+= "_sil";
        else if(cinemaReduction.getReductionType().equals("gold"))
            fileName+= "_gol";


        receipt+="-------------------------------"+
                "\nDate : "+today+
                "\nPlace occupee : "+parking.getNumber()+
                "\nType de vehicule : "+parking.getVehicleType()+
                "\nImmatriculation : "+parking.getPlateNumber()+
                "\nPrix de base : "+parking.getPricesList().get(0)+" €"+
                "\nReduction : reduction du "+dayOfWeek+
                "\nTotal a payer : "+parking.getTotalToPay()+" €"+
                "\nCode promo cinema : "+cinemaReduction.getPromoCode()+
                "\nType de ticket : "+cinemaReduction.getReductionType()+"("+cinemaReduction.getReductionPercentage()+" %)"+
                "\n"+cinemaReduction.getGame()+
                "\n-------------------------------";

        //--------------enregistrement du reçu dans un dossier et fichier-------

        String PATH = "ticket_archive\\";
        String directoryPath = PATH.concat(directoryName);
        String filePath = fileName+".txt";

        File directory = new File(directoryPath);
        if (! directory.exists()){
            directory.mkdir();
        }

        File file = new File(directoryPath + "/" + filePath);
        try{
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(receipt);
            bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }


    }
}
