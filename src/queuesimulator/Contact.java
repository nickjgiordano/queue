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
 * this class isn't instantiated anywhere, so has been implemented as an abstract class
 **************************************************************************************/
public abstract class Contact extends QueueItem {
    
    private String sName; // instance variable for contact's name
    
    public Contact() {} // constructor
    
    protected String getName() {return sName;} // get contact's name
    protected void setName(String newName) {sName = newName;} // set contact's name
    
} // end of Contact class
