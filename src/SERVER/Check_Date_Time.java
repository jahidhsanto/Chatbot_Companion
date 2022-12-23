package SERVER;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Check_Date_Time {
    public static String date_time() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String strDate = formatter.format(date);
        System.out.println("Current Date and Time : " + strDate);

        return strDate;
    }
}