import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCheck {


    public static int Check (String Date1, String Date2, JTextField tf)
    {
        try{
        SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdfo.parse(Date1);
        Date d2 = sdfo.parse(Date2);
        if(d1.compareTo(d2)>=0){
            tf.setText("Open for submission");

        }
        else
        {
            tf.setText("Under assessment");
        }
     return d1.compareTo(d2);
        }catch(ParseException e){return -10;}
    }
    public static int Check (String Date1, String Date2, JTextField tf,JButton btn)
    {
        try{
            SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sdfo.parse(Date1);
            Date d2 = sdfo.parse(Date2);
            if(d1.compareTo(d2)>=0){
                tf.setText("OPEN");
                btn.setEnabled(false);
            }
            else
            {
                tf.setText("CLOSED");
                btn.setEnabled(true);
            }
            return d1.compareTo(d2);
        }catch(ParseException e){return -10;}
    }

    public static boolean CheckBoolean (String Date1, String Date2, JTextField tf)
    {
        try{
            SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sdfo.parse(Date1);
            Date d2 = sdfo.parse(Date2);
            if(d1.compareTo(d2)>=0){
                tf.setText("Still open");
                return true;

            }
            else
            {
                tf.setText("Closed, can't delete");
                return false;
            }
        }catch(ParseException e){return false;}
    }
}
