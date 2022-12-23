package SERVER;

public class AI extends design {
    public static String rtrn(String theRest) {
        if (theRest.contains("hi"))
            return "Hi there!";
        else if (theRest.contains("hello"))
            return "Hello!";
        else if (theRest.contains("how are you") || theRest.contains("hru") || theRest.contains("how r you") || theRest.contains("how r u") || theRest.contains("h r y") || theRest.contains("how r y") || theRest.contains("how are u"))
            return "I'm doing well, thanks for asking!";
        else if (theRest.contains("what is your name") || theRest.contains("what's your name"))
            return "I'm SANTO";
        else if (theRest.contains("what do you do"))
            return "NOTHING TO DO";
        else if (theRest.contains("what is time now") || theRest.contains("what is time") || theRest.contains("time") || theRest.contains("date") || theRest.contains("what is date") || theRest.contains("what is date today")) {
            return "Current Date and Time: " + Check_Date_Time.date_time();
        } else if (theRest.contains("What is the weather like today?"))
            return "";
        else if (theRest.contains("How can I book a flight?"))
            return "";
        else if (theRest.contains("What is the best restaurant in town?"))
            return "";
        else if (theRest.contains("What is the fastest way to get to the airport?"))
            return "";
        else if (theRest.contains("What is the best way to learn a new language?"))
            return "";
        else {
            int rand = (int) (Math.random() * 4 + 1);
            if (rand == 1)
                return "I have to go. TATA BYE... BYE...";
            else if (rand == 2)
                return "I don't understand you";
            else if (rand == 3)
                return "I don't understand you, Bro";
            else if (rand == 4)
                return "Please come again";
        }
        return null;

    }

}
