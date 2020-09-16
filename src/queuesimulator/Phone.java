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
public class Phone extends Contact {
    
    private int iTelephone; // instance variable for phone number
    
    // constructor
    public Phone(String name, int number) {
        super.setName(name);
        setNumber(number);
    } // end of constructor
    
    protected int getNumber() {return iTelephone;} // get phone number
    private void setNumber(int newNumber) {iTelephone = newNumber;} // set phone number
    
} // end of Phone class