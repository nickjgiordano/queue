package queuesimulator;
import java.awt.*; // library used for graphics and GUI controls
import java.awt.event.*; // library used for events
import javax.swing.*; // library used for GUI controls
import javax.swing.border.*; // library used for etched borders
import java.io.*; // library used to read text files
/***************************************************************************************
 * @author anonymous, for Keele University assignment submission purposes
 * @version 1.0
 * @date November 2018
 ***************************************************************************************
 * this is the top-level class, from which the program is run
 ***************************************************************************************
 *              CONTENTS
 ***************************************************************************************
 * INNER CLASS: Canvas           JPanel, onto which the queues are drawn
 * INNER CLASS: MenuListener     ActionListener; reacts to menu bar actions
 * INNER CLASS: ButtonListener   ActionListener; reacts to control panel button actions
 *      METHOD: draw()           used to draw queues onto canvas
 *      METHOD: main()           used to run program
 **************************************************************************************/
public class QueueSimulator extends JFrame {
    // instance variables for GUI dimensions
    private final int QUEUES_WIDTH_INITIAL = 560;
    private final int QUEUES_HEIGHT_INITIAL = 560;
    private final int CONTROLS_WIDTH = 240;
    private final int MESSAGE_HEIGHT = 100;
    
    // Canvas class for queue area
    class Canvas extends JPanel {
        @Override // method overrides method in superclass
        // called upon change in canvas contents
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            draw(g);
        } // end of paintComponent method
    } // end of Canvas class
    
    // instance variables
    private QueueList qBirthday, qPhone;
    private Canvas canvas;
    private JPanel pnlControl, pnlInfo, pnlToggle, pnlManager, pnlForm, pnlButtons;
    private JLabel lblInfo, lblName, lblBirthday, lblPhone;
    private JRadioButton radBirthday, radPhone;
    private ButtonGroup radToggle;
    private JTextField txtName, txtBirthday, txtPhone;
    private JButton btnAdd, btnRemove, btnDisplay, btnEmpty;
    private JTextArea areaMessage;
    private JMenuBar menuBar;
    
    // constructor
    public QueueSimulator() {
        // set frame title and layout
        super.setTitle("Queue Simulator");
        super.setLayout( new BorderLayout() );
        
        // create central queues canvas
        canvas = new Canvas();
        canvas.setBorder( new TitledBorder(new EtchedBorder(), "Queue View") );
        canvas.setPreferredSize( new Dimension(QUEUES_WIDTH_INITIAL, QUEUES_HEIGHT_INITIAL) );
        JScrollPane scrollCanvas = new JScrollPane(canvas, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollCanvas.setPreferredSize( new Dimension(QUEUES_WIDTH_INITIAL + 17, QUEUES_HEIGHT_INITIAL + 3) );
        super.add(scrollCanvas, BorderLayout.CENTER);
        
        // create left-hand control panel
        pnlControl = new JPanel();
        pnlControl.setBorder( new TitledBorder(new EtchedBorder(), "Control Panel" ) );
        pnlControl.setPreferredSize( new Dimension(CONTROLS_WIDTH, QUEUES_HEIGHT_INITIAL) );
        JScrollPane scrollControl = new JScrollPane(pnlControl, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollControl.setPreferredSize( new Dimension(CONTROLS_WIDTH + 17, QUEUES_HEIGHT_INITIAL + 3) );
        super.add(scrollControl, BorderLayout.LINE_START);
        
        // create Queue Interactions info panel
        JPanel pnlInfo = new JPanel();
        pnlInfo.setBorder( new TitledBorder( new EtchedBorder(), "Queue Interactions" ) );
        pnlInfo.setPreferredSize( new Dimension(CONTROLS_WIDTH - 20, 60) );
        lblInfo = new JLabel("no hops yet");
        pnlInfo.add(lblInfo);
        pnlControl.add(pnlInfo);
        
        // create Queue Toggler panel
        JPanel pnlToggle = new JPanel();
        pnlToggle.setLayout( new GridLayout(0, 1) );
        pnlToggle.setBorder( new TitledBorder(new EtchedBorder(), "Queue Toggler") );
        pnlToggle.setPreferredSize( new Dimension(CONTROLS_WIDTH - 20, 80) );
        radBirthday = new JRadioButton("Birthdays", true);
        radPhone = new JRadioButton("Phone numbers");
        radToggle = new ButtonGroup();
        radToggle.add(radBirthday);
        radToggle.add(radPhone);
        pnlToggle.add(radBirthday);
        pnlToggle.add(radPhone);
        pnlControl.add(pnlToggle);
        
        // create Queue Manager panel
        JPanel pnlManager = new JPanel();
        pnlManager.setLayout( new BorderLayout() );
        pnlManager.setBorder( new TitledBorder(new EtchedBorder(), "Queue Manager") );
        pnlManager.setPreferredSize( new Dimension(CONTROLS_WIDTH - 20, 360) );
        pnlControl.add(pnlManager);
        
        // create form containing textfields
        JPanel pnlForm = new JPanel();
        pnlForm.setLayout( new GridLayout(0, 1) );
        pnlManager.add(pnlForm, BorderLayout.CENTER);
        lblName = new JLabel("Name");
        pnlForm.add(lblName);
        txtName = new JTextField();
        pnlForm.add(txtName);
        lblBirthday = new JLabel("Birthdate");
        pnlForm.add(lblBirthday);
        txtBirthday = new JTextField();
        pnlForm.add(txtBirthday);
        lblPhone = new JLabel("Phone number");
        pnlForm.add(lblPhone);
        txtPhone = new JTextField();
        pnlForm.add(txtPhone);
        pnlForm.add( Box.createHorizontalStrut(5) );
        pnlForm.add( new JSeparator(SwingConstants.HORIZONTAL) );
        
        // create buttons at end of form
        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout( new GridLayout(0, 1, 0, 5) );
        pnlManager.add(pnlButtons, BorderLayout.PAGE_END);
        btnAdd = new JButton("Add to queue's tail");
        btnAdd.addActionListener( new ButtonListener() );
        pnlButtons.add(btnAdd);
        btnRemove = new JButton("Remove from queue's head");
        btnRemove.addActionListener( new ButtonListener() );
        pnlButtons.add(btnRemove);
        btnDisplay = new JButton("Display queue head");
        btnDisplay.addActionListener( new ButtonListener() );
        pnlButtons.add(btnDisplay);
        btnEmpty = new JButton("Empty queue");
        btnEmpty.addActionListener( new ButtonListener() );
        pnlButtons.add(btnEmpty);
        
        // create bottom message area
        areaMessage = new JTextArea();
        areaMessage.setEditable(false);
        areaMessage.setBackground(canvas.getBackground());
        JScrollPane scrollMessage = new JScrollPane(areaMessage);
        scrollMessage.setBorder( new TitledBorder(new EtchedBorder(),"Message Area") );
        scrollMessage.setPreferredSize( new Dimension(CONTROLS_WIDTH + QUEUES_WIDTH_INITIAL, MESSAGE_HEIGHT) );
        super.add(scrollMessage, BorderLayout.PAGE_END);
        
        // create main menu bar
        menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
            JMenuItem miBirthday = new JMenuItem("Import birthdays");
                miBirthday.addActionListener( new MenuListener() );
                menuFile.add(miBirthday);
            JMenuItem miPhone = new JMenuItem("Import phone numbers");
                miPhone.addActionListener( new MenuListener() );
                menuFile.add(miPhone);
            menuFile.addSeparator();
            JMenuItem miExit = new JMenuItem("Exit");
                miExit.addActionListener( new MenuListener() );
                menuFile.add(miExit);
        menuBar.add(menuFile);
        JMenu menuHelp = new JMenu("Help");
            JMenuItem miAbout = new JMenuItem("About");
                miAbout.addActionListener( new MenuListener() );
                menuHelp.add(miAbout);
        menuBar.add(menuHelp);
        super.add(menuBar, BorderLayout.PAGE_START);
        
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set program to exit upon frame closing
        super.pack(); // pack frame to ensure proper sizing and spacing
        super.setLocationRelativeTo(null); // center frame on display
        super.setVisible(true); // set frame to be visible
        
        // create queue objects
        qBirthday = new QueueList();
        qPhone = new QueueList();
    } // end of constructor
    
    // listener used by menu bar
    class MenuListener implements ActionListener {
        @Override // method overrides method in superclass
        public void actionPerformed(ActionEvent event) {
            // use switch statement to detect which menu item was fired
            switch( event.getActionCommand() ) {
                case "Import birthdays": {
                    // import Birthdays text file, catching exception if file not found
                    try ( BufferedReader bReader = new BufferedReader( new FileReader("BirthdayReminder.txt") ) ) {
                        qBirthday.emptyQueue(); // empty existing queue, ready to import anew
                        qBirthday.setHops(0); // clear hop counter
                        String sLine; // string to store line read from text file
                        // loop through each line in text file
                        while ( ( sLine = bReader.readLine() ) != null) {
                            // split each line by the commas, storing in array
                            String[] sArray = sLine.split(",");
                            // add new Birthday item to tail, using array contents to set variables
                            qBirthday.addToTail( new Birthday(sArray[0], sArray[1]) );
                        } // end of while loop
                        // get number of hops taken for operation, and display on GUI
                        lblInfo.setText(Integer.toString( qBirthday.getHops() ) + " hops");
                        qBirthday.setHops(0); // clear hop counter
                        canvas.repaint(); // dedraw canvas to update queue display
                        // display error message to user if file not found
                    } catch (IOException e) {areaMessage.append("ERROR: BirthdayReminder.txt can't be found!\n");}
                    break;
                } // end "Import Birthdays" case
                case "Import phone numbers": {
                    // import Phone numbers text file, catching exception if file not found
                    try ( BufferedReader bReader = new BufferedReader( new FileReader("PhoneNoInfo.txt") ) ) {
                        qPhone.emptyQueue(); // empty existing queue, ready to import anew
                        qPhone.setHops(0); // clear hop counter
                        String sLine; // string to store line read from text file
                        // loop through each line in text file
                        while ( ( sLine = bReader.readLine() ) != null) {
                            // split each line by the commas, storing in array
                            String[] sArray = sLine.split(",");
                            // add new Phone item to tail, using array contents to set variables
                            // phone number needs parsing from string to integer
                            qPhone.addToTail( new Phone(sArray[0], Integer.parseInt(sArray[1]) ) );
                        } // end of while loop
                        // get number of hops taken for operation, and display on GUI
                        lblInfo.setText(Integer.toString( qPhone.getHops() ) + " hops");
                        qPhone.setHops(0); // clear hop counter
                        canvas.repaint(); // dedraw canvas to update queue display
                        // display error message to user if file not found
                    } catch (IOException e) {areaMessage.append("ERROR: PhoneNoInfo.txt can't be found!\n");}
                    break;
                } // end "Import Phone numbers" case
                case "Exit": {
                    // display confirmation dialog upon user request to exit, storing response as integer
                    int option = JOptionPane.showConfirmDialog(canvas, "Are you sure you want to Exit the program?");
                    // if "Yes" is selected, exit program
                    if(option == JOptionPane.YES_OPTION) {System.exit(0);}
                    break;
                } // end "Exit" case
                case "About": {
                    // display message dialog containing info about program
                    JOptionPane.showMessageDialog(canvas, "Simple Queue Simulator\nversion 1.0 (November 2018)");
                    break;
                } // end "About" case
            } // end of switch statement
        } // end of actionPerformed method
    } // end of MenuListener class
    
    // listener used by buttons
    class ButtonListener implements ActionListener {
        @Override // method overrides method in superclass
        public void actionPerformed(ActionEvent event) {
            // use switch statement to detect which button was fired
            switch( event.getActionCommand() ) {
                case "Add to queue's tail": {
                    // if Birthday radio button is selected, attempt to add to that queue
                    if( radBirthday.isSelected() ) {
                        // create new Birthday item, using textfield contents to set variables
                        Birthday birthday = new Birthday(
                                txtName.getText(),
                                txtBirthday.getText()
                        );
                        qBirthday.addToTail(birthday); // add new item to queue tail
                        // display message to user indicating item successully added to queue
                        areaMessage.append("NOTICE: item successfully added to Birthdays queue!\n");
                        // get number of hops taken for operation, and display on GUI
                        lblInfo.setText(Integer.toString( qBirthday.getHops() ) + " hops");
                        qBirthday.setHops(0); // clear hop counter
                    // if Phone radio button is selected, attempt to add to that queue
                    } else if( radPhone.isSelected() ) {
                        // try following code, catching exception if integer not entered for phone number
                        try {
                            // create new Phoe item, using textfield contents to set variables
                            // phone number needs parsing from string to integer
                            Phone phone = new Phone(
                                    txtName.getText(),
                                    Integer.parseInt( txtPhone.getText() )
                            );
                            qPhone.addToTail(phone); // add new item to queue tail
                            // display message to user indicating item successully added to queue
                            areaMessage.append("NOTICE: item successfully added to Phone numbers queue!\n");
                            // get number of hops taken for operation, and display on GUI
                            lblInfo.setText(Integer.toString( qPhone.getHops() ) + " hops");
                            qPhone.setHops(0); // clear hop counter
                        } catch (NumberFormatException e) {areaMessage.append("ERROR: Phone number must be entered as integer!\n");}
                    } // end of if-else statement to get toggled queue
                    canvas.repaint(); // dedraw canvas to update queue display
                    break;
                } // end "Add to tail" case
                case "Remove from queue's head": {
                    // try following code, catching exception if queue has nothing to remove
                    try {
                        // if Birthday radio button is selected, attempt to remove Birthdays queue head
                        if( radBirthday.isSelected() ) {
                            // remove Birthday item from head, and store in variable to display info
                            Birthday birthday = (Birthday) qBirthday.removeFromHead();
                            // display message to user showing info about removed head item
                            areaMessage.append("NOTICE: head of Birthdays queue removed: "
                                    + birthday.getName() + ", "
                                    + birthday.getDate() + "\n"
                            );
                            // get number of hops taken for operation, and display on GUI
                            lblInfo.setText(Integer.toString( qBirthday.getHops() ) + " hops");
                            qBirthday.setHops(0); // clear hop counter
                        // if Phone radio button is selected, attempt to remove Phone numbers queue head
                        } else if( radPhone.isSelected() ) {
                            // remove Phone item from head, and store in variable to display contents
                            Phone phone = (Phone) qPhone.removeFromHead();
                            // display message to user showing info about removed head item
                            areaMessage.append("NOTICE: head of Phone numbers queue removed: "
                                    + phone.getName() + ", "
                                    + Integer.toString( phone.getNumber() ) + "\n"
                            );
                            // get number of hops taken for operation, and display on GUI
                            lblInfo.setText(Integer.toString( qPhone.getHops() ) + " hops");
                            qPhone.setHops(0); // clear hop counter
                        } // end of if-else statement to get toggled queue
                        canvas.repaint(); // dedraw canvas to update queue display
                    } catch(NullPointerException e) {areaMessage.append("ERROR: selected queue has nothing to remove!\n");}
                    break;
                } // end "Remove from head" case
                case "Display queue head": {
                    // try following code, catching exception if queue has no head
                    try {
                        // if Birthday radio button is selected, attempt to get Birthdays queue head
                        if( radBirthday.isSelected() ) {
                            // get Birthday queue head, and store in variable to display info
                            Birthday birthday = (Birthday) qBirthday.getHead();
                            // display message to user showing info about head
                            areaMessage.append("NOTICE: head of Birthdays queue is: "
                                    + birthday.getName() + ", "
                                    + birthday.getDate() + "\n"
                            );
                            // get number of hops taken for operation, and display on GUI
                            lblInfo.setText(Integer.toString( qBirthday.getHops() ) + " hops");
                            qBirthday.setHops(0); // clear hop counter
                        // if Phone radio button is selected, attempt to get Phone numbers queue head
                        } else if( radPhone.isSelected() ) {
                            // get Phone queue head, and store in variable to display info
                            Phone phone = (Phone) qPhone.getHead();
                            // display message to user showing info about head
                            areaMessage.append("NOTICE: head of Phone numbers queue is: "
                                    + phone.getName() + ", "
                                    + Integer.toString( phone.getNumber() ) + "\n"
                            );
                            // get number of hops taken for operation, and display on GUI
                            lblInfo.setText(Integer.toString( qPhone.getHops() ) + " hops");
                            qPhone.setHops(0); // clear hop counter
                        } // end of if-else statement to get toggled queue
                    } catch(NullPointerException e) {areaMessage.append("ERROR: selected queue has no head!\n");}
                    break;
                } // end "Display head" case
                case "Empty queue": {
                    // display confirmation dialog upon user request to empty queue, storing response as integer
                    int option = JOptionPane.showConfirmDialog(canvas, "Are you sure you want to empty the selected queue?");
                    // if "Yes" is selected, proceed to empty queue
                    if(option == JOptionPane.YES_OPTION) {
                        // try following code, catching exception WHEN queue is emptied
                        try {
                            // if Birthday radio button is selected, attempt to empty Birthdays queue
                            if( radBirthday.isSelected() ) {
                                // remove Birthday item from head, and store in variable to use in loop
                                Birthday birthday = (Birthday) qBirthday.removeFromHead();
                                // while queue head is not null, do following loop:
                                while(birthday != null) {
                                    // display message to user showing info about removed head item
                                    areaMessage.append("NOTICE: head of Birthdays queue removed: "
                                            + birthday.getName() + ", "
                                            + birthday.getDate() + "\n"
                                    );
                                    // remove Birthday item from head, and store in variable for further use in loop
                                    birthday = (Birthday) qBirthday.removeFromHead();
                                    // get number of hops taken for operation, and display on GUI
                                    lblInfo.setText(Integer.toString( qBirthday.getHops() ) + " hops");
                                } // end of while loop for checking queue head
                            // if Phone radio button is selected, attempt to empty Phone numbers queue
                            } else if( radPhone.isSelected() ) {
                                // remove Phone item from head, and store in variable to use in loop
                                Phone phone = (Phone) qPhone.removeFromHead();
                                // while queue head is not null, do following loop:
                                while(phone != null) {
                                    // display message to user showing info about removed head item
                                    areaMessage.append("NOTICE: head of Phone numbers queue removed: "
                                            + phone.getName() + ", "
                                            + Integer.toString( phone.getNumber() ) + "\n"
                                    );
                                    // remove Phone item from head, and store in variable for further use in loop
                                    phone = (Phone) qPhone.removeFromHead();
                                    // get number of hops taken for operation, and display on GUI
                                    lblInfo.setText(Integer.toString( qPhone.getHops() ) + " hops");
                                } // end of while loop for checking queue head
                            } // end of if-else statement to get toggled queue
                        // catch statement will always fire, which is why message appears as "NOTICE", not "ERROR"
                        } catch(NullPointerException e) {areaMessage.append("NOTICE: queue is empty!\n");}
                        qBirthday.setHops(0); // clear hop counter
                        qPhone.setHops(0); // clear hop counter
                        canvas.repaint(); // dedraw canvas to update queue display
                    } // end of if statement to check if "yes" was pressed to empty queue
                    break;
                } // end "Empty queue" case
            } // end of switch statement
        } // end of actionPerformed method
    } // end of ButtonListener class
    
    // called by Canvas paintComponent method whenever a change is made
    void draw(Graphics g) {
        int iX = 30, iY = 90; // set coordinates for Birthdays queue
        g.setFont(new Font ("Trebuchet MS", Font.BOLD, 16)); // set font for queue title
        g.drawString("Birthdays", iX, 60); // draw queue title
        g.setFont(new Font ("Trebuchet MS", Font.PLAIN, 16)); // set font for queue items
        // get Birthdays queue head, and store in variable to use in loop
        Birthday currBirthday = (Birthday) qBirthday.getHead();
        // while queue head is not null, do following loop:
        while (currBirthday != null) {
            g.drawString(currBirthday.getName(), iX, iY); // draw name
            g.drawString(currBirthday.getDate(), iX + 100, iY); // draw date
            currBirthday = (Birthday) currBirthday.getNextItem(); // get next queue item to continue loop
            iY = iY + 20; // increment y-coordinate, to draw next string underneath
        } // end of while loop
        g.drawRect(20, 40, 200, iY - 50); // draw rectangle shape for queue border
        
        iX = 300; // set x-coordinate for Phone queue
        iY = 90; // set y-coordinate for Phone queue
        g.setFont(new Font ("Trebuchet MS", Font.BOLD, 16)); // set font for queue title
        g.drawString("Phone numbers", iX, 60); // draw queue title
        g.setFont(new Font ("Trebuchet MS", Font.PLAIN, 16)); // set font for queue items
        // get Phone numbers queue head, and store in variable to use in loop
        Phone currPhone = (Phone) qPhone.getHead();
        // while queue head is not null, do following loop:
        while (currPhone != null) {
            g.drawString(currPhone.getName(), iX, iY); // draw name
            // draw number, converted from integer to string
            g.drawString(Integer.toString( currPhone.getNumber() ), iX + 100, iY);
            currPhone = (Phone) currPhone.getNextItem(); // get next queue item to continue loop
            iY = iY + 20; // increment y-coordinate, to draw next string underneath
        } // end of while loop
        g.drawRect(iX - 10, 40, 200, iY - 50); // draw rectangle shape for queue border
    } // end of draw method
    
    // create new object to run program
    public static void main(String[] args) {
        QueueSimulator queueInstance = new QueueSimulator();
    } // end of main method
} // end of QueueSimulator class