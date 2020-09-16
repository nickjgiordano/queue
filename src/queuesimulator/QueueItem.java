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
public abstract class QueueItem {
    
    private QueueItem nextItem; // instance variable for item's adjacent item in queue
    
    public QueueItem() {} // constructor
    
    protected QueueItem getNextItem() {return nextItem;} // get next item in linked list
    protected void setNextItem(QueueItem newItem) {nextItem = newItem;} // set next item in linked list
    
} // end of QueueItem class