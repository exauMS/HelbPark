package com.helb.helbpark;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class Payment
{
    private static PaymentStrategy strategy;
    private  static GregorianCalendar calendar = new GregorianCalendar();
    private static int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    private static int actualDay = calendar.get(Calendar.DAY_OF_MONTH);



    public static ArrayList<Integer> getTotal(String vehicleType, String plateNumber)
    {
        ArrayList<Integer> pricesList = new ArrayList<>();
        //prix de base
        setStrategy(new StrategyStartingPrice(vehicleType));
        pricesList.add(strategy.getTotalAfterReduction());//prix de base, position [0]

        //exception mardi moitie prix pour moto
        if (dayOfWeek==Calendar.TUESDAY)
        {
            if (vehicleType.equals("bike"))
            {
                setStrategy(new StrategyHalfPrice(vehicleType));
            }
        }
        // exception mercredi reduction de 25% pour vehicule avec plaque contenant "p"
        else if(dayOfWeek==Calendar.WEDNESDAY)
        {
            if (plateNumber.contains("P"))
            {
                double percentage = 25;
                setStrategy(new StrategyPercentagePrice(vehicleType, percentage));
            }

        }
        //exception vendredi moitie prix pour camionnette
        else if (dayOfWeek==Calendar.FRIDAY)
        {
            if (vehicleType.equals("van"))
            {
                setStrategy(new StrategyHalfPrice(vehicleType));
            }
        }
        // exception samedi si jour pair moitie prix, sinon prix de base
        else if (dayOfWeek==Calendar.SATURDAY)
        {
            if(actualDay%2==0)
            {
                setStrategy(new StrategyHalfPrice(vehicleType));
            }
        }
        pricesList.add(strategy.getTotalAfterReduction());//prix apres reduction, position [1]
        pricesList.add(dayOfWeek);//le jour de la semaine, position [2]
        return pricesList;
    }

    private static void setStrategy(PaymentStrategy _strategy)
    {
        strategy = _strategy;
    }
}
