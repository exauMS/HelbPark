package com.helb.helbpark;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class CinemaReduction
{
    private String vehicleType;
    private String reductionType;
    private static ArrayList<String> promoCodeList = new ArrayList<>();
    private String promoCode="";
    private String alpabet = "abcdefghijklmnopqrstuvwxyz", numbers="0123456789";

    private String game;
    private int reductionPercentage;



    public CinemaReduction(String vehicleType)
    {
        this.vehicleType = vehicleType;
        reductionType = createReductionType();

        if (reductionType.equals("standard"))
            promoCode = getStandardCode();
        else if (reductionType.equals("silver"))
            promoCode = getSilverCode();
        else if (reductionType.equals("gold"))
            promoCode = getGoldCode();
    }

    private String createReductionType()
    {
        int counter = 10;
        int favouriteReduction;//0 = standard ticket, 1 = silver ticket, 2 = gold ticket
        String ticket = "";

        if (vehicleType.equals("bike"))
        {
            favouriteReduction=0;
            ticket= createReduction(favouriteReduction,counter);
        }
        else if (vehicleType.equals("car"))
        {
            favouriteReduction=1;
            ticket= createReduction(favouriteReduction,counter);
        }
        else if (vehicleType.equals("van"))
        {
            favouriteReduction=2;
            ticket= createReduction(favouriteReduction,counter);
        }
        return ticket;
    }
    private String createReduction(int favouriteReduction, int counter)
    {
        int reduction = (int)(Math.random()*3);//0 = standard ticket, 1 = silver ticket, 2 = gold ticket
        String ticket="";

        while (counter>0)
        {
            if (reduction!=favouriteReduction)
            {
                reduction = (int)(Math.random()*3);
                counter--;
            }
            else
                counter=-1;

        }
        if (reduction==0)
            ticket = "standard";
        else if (reduction==1)
            ticket = "silver";
        else if(reduction==2)
            ticket = "gold";

        return ticket;
    }

    private String getStandardCode()
    {

        int firstPercentage = 5, secondPercentage = 10;
        String code="";

        code = createPromoCode();
        promoCodeList.add(code);
        setReductionPercentage(firstPercentage,secondPercentage);

        game = "Aucun jeu pour le ticket Standard";

        return code;
    }

    private String getSilverCode()
    {
        int firstPercentage = 10, secondPercentage = 15;
        String code="", gameCharacters="OXP";

        code = createPromoCode();
        promoCodeList.add(code);
        setReductionPercentage(firstPercentage,secondPercentage);

        game = getGameResult(gameCharacters, reductionType);

        return code;
    }

    private String getGoldCode()
    {
        int firstPercentage = 20, secondPercentage = 40;
        String code="", gameCharacters="PARKHELB";

        code = createPromoCode();
        promoCodeList.add(code);
        setReductionPercentage(firstPercentage,secondPercentage);

        game = getGameResult(gameCharacters, reductionType);

        return code;
    }

    private String createPromoCode()
    {
        int letterPosition, numberPosition;
        int counter=4;
        String code="";

        do
        {
            while (counter!=0)
            {
                letterPosition = (int)(Math.random()*alpabet.length());
                numberPosition = (int)(Math.random()*numbers.length());
                code+=alpabet.charAt(letterPosition);
                code+=numbers.charAt(numberPosition);
                counter--;
            }
            counter=4;
        }while(promoCodeList.contains(code));//assurer l'unicite des codes promos

        return code;
    }

    private String getGameResult(String gameCharacters, String _reductionType)
    {
        String gameResult = "";
        int character;
        int counter = 0;


        if(_reductionType.equals("silver"))
        {
            boolean success = true;
            while (counter!=2)// 2 : nombre de caracteres voulu
            {
                character = (int)(Math.random()*gameCharacters.length());
                gameResult+=gameCharacters.charAt(character);
                counter++;
            }

            //verification chaine gagnante ou non

            int n = gameResult.length();

            for (int i = 1; i < n ; i++)
            {
                if(gameResult.charAt(i) != gameResult.charAt(0))
                    success = false;
            }

            if (success)
                return "Jeu : \n"+"Caracteres similaires : "+gameResult+"\n Felicitations ! Nouvelle reduction cinema : "+getReductionPercentage()*2 + "%";
            else
                return "Jeu : \n"+"Caracteres differents : "+gameResult+"\n Dommage ! Vous gagnerez la prochaine fois";
        }
        else if(_reductionType.equals("gold"))
        {
            boolean success = false;
            while (counter!=gameCharacters.length()+1)// nombre de caracteres voulu autant que la taille de la chaine + 1 lettre en plus de la chaine
            {
                character = (int)(Math.random()*gameCharacters.length());
                gameResult+=gameCharacters.charAt(character);
                counter++;
            }

            //verification chaine gagnante ou non

            counter = 0;
            char[][] charArray = new char[(gameResult.length()+1)/3][(gameResult.length()+1)/3];//ici 3 correspond a 9/3, a voir au cas par cas selon la taille de la chaine

            //remplissage du tableau avec les caracteres de la chaine
            for (int i = 0; i < charArray.length; i++)
            {
                for (int j = 0; j < charArray.length; j++)
                {
                    charArray[i][j] = gameResult.charAt(counter);
                    counter++;
                }
            }

            //recuperation des lignes et colonnes en parcourant le tableau

            String rowOrColContent = "";
            ArrayList<String> rowOrColContentList = new ArrayList<>();
            int k=0,l=0;
            boolean AllRowsChecked=false, AllColsChecked=true;//valeurs par default utile au bon fonctionnement de l'algo

            //on commencera par recuperer les caracteres sur les lignes d'abord puis les colonnes
            while(AllRowsChecked!=true || AllColsChecked!=true)
            {
                if(l<charArray.length && k<charArray.length)//security
                    rowOrColContent+=charArray[k][l];

                if(AllColsChecked==true)
                    l++; //on avance sur les lignes
                else
                    k++; //on avance sur les colonnes

                if(l==charArray.length)
                {
                    if(AllRowsChecked==false)//si on arrive en fin de ligne mais que toutes les lignes ne sont pas checked
                    {
                        //on repart pour un tour sur la ligne suivante
                        rowOrColContentList.add(rowOrColContent);
                        l=0;
                        k++;
                        rowOrColContent="";
                    }
                    else
                    {
                        AllColsChecked=true;
                    }
                }

                if(k==charArray.length)
                {
                    if(AllColsChecked==false)
                    {
                        rowOrColContentList.add(rowOrColContent);
                        k=0;
                        l++;
                        rowOrColContent="";
                    }
                    else
                    {
                        //on prepare la recuperation sur les colonnes cette fois ci
                        AllColsChecked=false;
                        AllRowsChecked=true;
                        k=0;
                    }
                }
            }

            // a ce niveau ci, nous possedons une liste contenant les caracteres des lignes et ceux des colonnes {[***],[***],[***],...}

            //verifier les doublons dans les chaines recuperees
            for (String s: rowOrColContentList)
            {
                for (int i = 1; i < s.length(); i++)
                {
                    if(s.charAt(i) == s.charAt(0))
                    {
                        success = true;
                        break;
                    }
                }
            }

            //on recupere tout nos caracteres du tableau en les mettant dans une chaine formatee comme voulu
            /* FORMAT
            * XXX
            * XXX
            * XXX
            * */
            String allCharacters = "";
            for (int i = 0; i < charArray.length; i++)
            {
                for (int j = 0; j < charArray.length; j++)
                {
                    allCharacters += charArray[i][j];
                }
                allCharacters+="\n";
            }

            if(success)
                return "Jeu : \n"+allCharacters+"\n Felicitations ! Nouvelle reduction cinema :"+getReductionPercentage()*2+" %";
            else
                return "Jeu : \n"+allCharacters+" Dommage ! vous gagnerez la prochaine fois";

        }
        return "aucun jeu disponible";
    }

    private void setReductionPercentage(int firstPercentage, int secondPercentage )
    {
       int choice = (int)(Math.random()*2);//0 ou 1

       if(choice==0)
           reductionPercentage=firstPercentage;
       else
           reductionPercentage=secondPercentage;
    }

    public int getReductionPercentage()
    {
        return reductionPercentage;
    }

    public String getReductionType()
    {
        return reductionType;
    }
    public String getPromoCode()
    {
        return promoCode;
    }

    public String getGame()
    {
        return game;
    }
}
