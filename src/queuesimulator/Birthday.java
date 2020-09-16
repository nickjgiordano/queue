package queuesimulator;
/***************************************************************************************
 * @author anonymous, for Keele University assignment submission purposes
 * @version 1.0
 * @date November 2018
 ***************************************************************************************
 * queue item. inheritance hierarchy is as follows:
 * 
 *                       > Birthday
 *                     /
 * QueueItem -> Contact
 *                     \
 *                       > Phone
 * 
 **************************************************************************************/
public class Birthday extends Contact {
    
    private String sDate; // instance variable for birth date
    
    // constructor
    public Birthday(String name, String date) {
        super.setName(name);
        setDate(date);
    } // end of constructor
    
    protected String getDate() {return sDate;} // get birthday's date
    private void setDate(String newDate) {sDate = newDate;} // set birthday's date
    
} // end of Birthday class