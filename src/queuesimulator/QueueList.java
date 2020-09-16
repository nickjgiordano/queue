package queuesimulator;
/***************************************************************************************
 * @author anonymous, for Keele University assignment submission purposes
 * @version 1.0
 * @date November 2018
 ***************************************************************************************
 * this is the class for the queue itself
 ***************************************************************************************
 *              CONTENTS
 ***************************************************************************************
 *      METHOD: getHops()        getter method for 'hops' variable
 *      METHOD: setHops()        setter method for 'hops' variable
 *      METHOD: getHead()        getter method for 'head' variable
 *      METHOD: addToTail()      enQueue new item to tail of queue
 *      METHOD: removefromHead() serve head item out of queue
 *      METHOD: emptyQueue()     remove all items from queue
 **************************************************************************************/
public class QueueList {
    
    private QueueItem head; // instance variable for item at head of queue
    private int hops; // instance variable to count number of list hops
    
    public QueueList() {head = null;} // constructor
    
    protected int getHops() {return hops;} // get number of list traversals
    protected void setHops(int i) {hops = i;} // set number of list traversals (currently only used to set it to 0)
    
    //  pre: assumes list is not empty; exceptions handled in QueueSimulator class
    // post: get item at head of queue
    protected QueueItem getHead() {
        return head;
    } // end of getHead method
    
    //  pre: no conditions, as queue has no upper limit, unlike array
    // post: enQueue new item to tail of queue
    protected void addToTail(QueueItem newItem) {
        QueueItem prevItem = null, currItem = head; // create variables for use below
        hops += 1;
        // move from head to tail within queue, doing the following:
        while (currItem != null) {
            prevItem = currItem; // set variable for previous item to current item
            hops += 1;
            currItem = currItem.getNextItem(); // set variable for current item to next item
            hops += 1;
        } // end of while loop
        // if loop has run, previous item will have value while current item will be null
        if (prevItem == null) {
            head = newItem; // if previous item is null, queue must be empty, so set new item as queue head
            hops += 1;
        } else {
            prevItem.setNextItem(newItem); // otherwise queue isn't empty, so set new item as tail's next item pointer
            hops += 1;
        } // end of if-else statement
    } // end of addToTail method
    
    //  pre: assumes list is not empty; exceptions handled in QueueSimulator class
    // post: serve head item out of queue
    protected QueueItem removeFromHead() {
        QueueItem prevHead = head; // store head item, so it can be returned later
        hops += 1;
        head = head.getNextItem(); // set queue head to next item, thus removing item from head
        hops += 1;
        return prevHead; // return removed previous head item
    } // end of removeFromHead method
    
    //  pre: no conditions, as loop only executes if head isn't null in first place
    // post: remove all items from queue (if any exist)
    protected void emptyQueue() {
        // set queue head to next item until it becomes null, thus emptying queue
        while(head != null) {
            head = head.getNextItem();
            hops += 1;
        } // end of while loop
    } // end of emptyQueue method
    
} // end of QueueList class