package shopping_cart_1;
	// autor Svetoslav Kamenov
	//

	import java.awt.*;
	import java.awt.event.*;
	import javax.swing.*;
	import javax.swing.event.*;

	/*
	 * ComboBoxDemo.java uses these additional files:
	 * 	Order.java
	 * 	OrderItem.java
	 * 	PetArticle.java
	 * 	PetWawehouse.java
	 *  images/Bird.gif
	 *  images/Cat.gif
	 *  images/Dog.gif
	 *  images/Rabbit.gif
	 *  images/Pig.gif
	 */
	public class ShoppingCartSK extends JPanel
	                          implements ActionListener, ListSelectionListener {
			// GUI components
			// shows item image and some text
	    JLabel itemPicture; 
	    	// editable,  updated by the program
	    JLabel qtyInStockLabel;  			//   reflects data base
	    JLabel transactionQuantityLabel; 	//   reflects order_item_qty 
	    JLabel thisArticleTotalQty;			//   reflects data base	
	    JLabel totalSumLabel;		 		//   reflects order total	
		    // copy:  qtyInStockLabel
		    // copy:  transactionQuantityLabel
		    // copy:  thisArticleTotalQty
		    // copy:  TotalSumLabel
	    JButton addButton;  
	    	// disabled by ListSelectionListener implementation
	    JButton removeButton;  
	    JSpinner qtySpinner;
	    JComboBox itemsCB;
	    private DefaultListModel listModel;
	    private JList list;
// dead ?
	    JLabel aLabel; //  utility
	    
	    	// describes articles, quantities and provides add, remove, search operations
	    static PetWarehouse wHouse =    new PetWarehouse ();
	    	// a list of purchased wares (transactions)
	    static Order order = new Order(); 
	    	// state variables	    	   
	    int qtyOrdered ;
	    int totalSum;
	    PetArticle articleInFocus;
	    	 
	 
	    public ShoppingCartSK() {
	    		//  
	    	super(new GridBagLayout());	// 
	    		// left panel	    		 
	    	JPanel leftPane =  new JPanel (new GridBagLayout());
	    		 
	        GridBagConstraints c = new GridBagConstraints();	
	        
	        c.fill = GridBagConstraints.BOTH;
	        c.gridwidth = GridBagConstraints.REMAINDER;
	       
	        	// Create a combo box, select the item at index 4.
	        	// shows the items in the warehouse to be selected
	        String[] itemStrings = wHouse.getAllArticleNames();	                	
	        itemsCB = new JComboBox(itemStrings);
	        itemsCB.setSelectedIndex(4);
	        	 
	        itemsCB.setActionCommand("SelectItemCommand");
	        itemsCB.addActionListener(this);	        	 
	        	leftPane.add(itemsCB,c);

	        	// label = picture and text, shows the items in WH
	        itemPicture = new JLabel();
	        itemPicture.setHorizontalTextPosition(JLabel.CENTER);
	        itemPicture.setVerticalTextPosition(JLabel.BOTTOM);
	        itemPicture.setFont(itemPicture.getFont().deriveFont(Font.ITALIC));
	      
	        itemPicture.setHorizontalAlignment(JLabel.CENTER);
	        	// initialize the icon
	        updateLabelImage(itemStrings[itemsCB.getSelectedIndex()]);
	        
	        itemPicture.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
	         
		        //The preferred size is hard-coded to be the width of the
		        //widest image and the height of the tallest image + the border.
		        //A real program would compute this.
	        itemPicture.setPreferredSize(new Dimension(277, 222+10));
	        	leftPane.add(itemPicture,c);
	        
	        	// label: quantity in stock , initialize
	        qtyInStockLabel = new JLabel() ;	        	 
	        updateQtyInStockLabel ();	        	 
	        qtyInStockLabel.setFont(qtyInStockLabel.getFont().deriveFont(Font.ITALIC));
	        	// label: quantity to add 
	        	leftPane.add(qtyInStockLabel,c);
//dead	        	
	        aLabel = new JLabel("quantity to add: ");
	        	leftPane.add(qtyInStockLabel,c);
//dead
	        	// spinner
	        	// initial value, min, max, step	         
	        SpinnerNumberModel numberModel = new SpinnerNumberModel(1,1,100,1);
	        qtySpinner = new JSpinner (numberModel);
	        	leftPane.add(qtySpinner,c);
	        	// button: ADD
	        addButton = new JButton("Add to basket");
	        addButton.setActionCommand("AddToBasketCommand");
	        addButton.addActionListener(this);
	        	leftPane.add(addButton,c);
	 
	        	// borders of whole panel 
	        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	        
	        	// set up the right pane  	       	       
	        JPanel rightPane =  new JPanel (new GridBagLayout());
	        GridBagConstraints cr = new GridBagConstraints();
	        cr.fill = GridBagConstraints.BOTH;
        	cr.gridwidth = GridBagConstraints.REMAINDER; 
        	
	        	// List, not editable     
	        listModel = new DefaultListModel<List>();
	        	//Create the list and put it in a scroll pane.
	        list = new JList (listModel);
	        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        list.setSelectedIndex(0);
	        list.addListSelectionListener(this);
	        list.setVisibleRowCount(10);
	        JScrollPane listScrollPane = new JScrollPane(list);	     
	        rightPane.add(listScrollPane,cr);
	         	        	        
	        transactionQuantityLabel = new JLabel(" ordered quantity : ");
	        rightPane.add(transactionQuantityLabel,cr);
	        
	        thisArticleTotalQty = new JLabel("total  ordered quantity of this article : ");
	        rightPane.add(thisArticleTotalQty,cr);
	        
		    totalSumLabel = new JLabel(" total sum : ") ;
		    rightPane.add(totalSumLabel,cr);
		  
		    removeButton = new JButton ("remove from basket");
		    removeButton .setActionCommand("removeCommand");
		    removeButton.setEnabled(false); 
		   
		    removeButton.addActionListener(this);
		    	rightPane.add(removeButton,cr);
	        
	        	// assemble all
	        GridBagConstraints cc = new GridBagConstraints();
	        add(leftPane,cc);
	        add(rightPane,cc);
	    } // end constructor

	    /*********************************** Interface ActionListener IIIIIIIIIIIIIIIIIIIIIII
	     * combo box, buttons, ... */
	    
	    public void actionPerformed(ActionEvent e) {
	    	String itemName = null; //  
	    		// ComboBox
	    	if ("SelectItemCommand".equals(e.getActionCommand())){
	    		// this is a general aproach - if you have 10 combo boxes ... 
	        JComboBox cb = (JComboBox)e.getSource();
	        	// ComboBox was not parameterized type, SK-fix it !!
	        itemName = (String)cb.getSelectedItem();	        	 
	        updateLabelImage(itemName);	         
	        updateQtyInStockLabel ();  
	        	// if qty_in_stock == 0, disable add button  	    	
	        updateAddButton();
	    	} 
	    	
	    	if ("AddToBasketCommand".equals(e.getActionCommand())){
	    			// get requested qty
	    		int requestedQty = (int)qtySpinner.getValue();
	    			// get requested article
	    		String requestedArticleName = (String)itemsCB.getSelectedItem();
	    			// update (credit) data base 
	    			// get the ACTUAL sold qty !! 
	    		int sold = wHouse.decArtQuantity (requestedArticleName, requestedQty);
	    			// should be done automatically by wHouse
	    		wHouse.getArticleByName(requestedArticleName).addSold(sold);
	    			// update order
	    			// shortcut. rather bad stile
	    		order.addItem(wHouse.getArticleByName(requestedArticleName), sold);
 
	    		 
	    			// update labels that do not depend on JList
	    		updateQtyInStockLabel();	     		
	    		updateTotalSumLabel();
	    		updateAddButton();
	    		 
 
	    			// update JList	 
	    		 int index = list.getSelectedIndex(); //get selected index
		            if (index == -1) { //no selection, so insert at beginning
		                index = 0;
		            } else {           //add after the selected item
		                index++;
		            }	          
		            	// take from combo box to the end of the list
		            listModel.addElement(requestedArticleName);  		    
		            	//Select the new item and make it visible.
		            list.setSelectedIndex(index);
		            list.ensureIndexIsVisible(index);
		           		// update the labels that depend on JList index
		           updateTransactionQuantityLabel();  
		           updateThisArticleTotalQtyLabel();
// debug           System.out.println("JList selected index "+ index);
	    	}
	    	 
	    	if ("removeCommand".equals(e.getActionCommand())){
	    		 int index = list.getSelectedIndex();
//	    		 	System.out.println("remove command");
//	    		 	System.out.println("selected index "+ index);
	    		 	// update Order object 	first get, then delete    		 
	   		 String name = order.getItemAt(index).getArticleName();
//	   		 		System.out.println("name "+ name);
	    		 int qty = order.getQuantityAt(index); // deep
//	    		 	System.out.println("qty "+ qty);
	    		 	 // transactionQuantityLabel
	    	      	 updateTransactionQuantityLabel();  //<<<<<<<<<<<<<<<<<<<<<<<<< 	
	    	         //    thisArticleTotalQty
	    	    	 updateThisArticleTotalQtyLabel();
	    	    	 	// complex
	    	    	 order.removeAt(index); 
		            // recover wHouse object		         
  	          wHouse.getArticleByName(name).addQuantity(qty);		          
  	          wHouse.getArticleByName(name).decreaseSold(qty);
  	          	// change qtyInStockLabel
  	          updateQtyInStockLabel ();   	 
  	          updateTotalSumLabel();  
  	          updateAddButton();
		         
		        // remove from JList
	    		//This method can be called only if
	            //there's a valid selection
	            //so go ahead and remove whatever's selected.
	           
	            listModel.remove(index);

	            int size = listModel.getSize();

	            if (size == 0) { //Nobody's left, disable firing.
	            	removeButton.setEnabled(false);
	            } else { //Select an index.
	            		// == size ??
	                if (index == listModel.getSize()) {
	                    //removed item in last position
	                    index--;
	                }

	                list.setSelectedIndex(index);
	                list.ensureIndexIsVisible(index);
	            }
	            // update 
	            updateTransactionQuantityLabel();   
 	           	updateThisArticleTotalQtyLabel();	
        
	    	} // end remove command
 
	   }// end action performed
	    //****************************************************interface ListSelectionListener 
	    //This method is required by ListSelectionListener.
	    public void valueChanged(ListSelectionEvent e) {
	    		// NOTA BENE the enclosing IF !!!
	        if (e.getValueIsAdjusting() == false) {

	            if (list.getSelectedIndex() == -1) {
	            //No selection, disable remove button.
	            	removeButton.setEnabled(false);	            	

	            } else {
	            //Selection, enable the fire button.
	            	removeButton.setEnabled(true);
	            }	 
	            	// update labels
	            updateTransactionQuantityLabel();   
	 	        updateThisArticleTotalQtyLabel();		            	           	           
	        }
	    }

	  
	    protected void updateLabelImage(String name) {
	        ImageIcon icon = createImageIcon("images/" + name + ".gif");
	        itemPicture.setIcon(icon);
	        // itemPicture.setToolTipText("A drawing of a " + name.toLowerCase());	        	 
	        itemPicture.setText(wHouse.getArticleByName(name).getSpecialMarks ());	        
	    }
	    	//----------------------------------------------------------- update labels display
	    protected void updateQtyInStockLabel () {
	    	String name = (String)itemsCB.getSelectedItem();
	    	// get value from DB
	    	int stock;	    	
	    	stock = wHouse.getArticleByName(name).getQuantity();
	    	qtyInStockLabel.setText("quantity in stock: "+ stock);	 
	    }
	  
	    protected void   updateTransactionQuantityLabel() {
	    	// get index from JList
	    	// get qty from order
	    	int index = list.getSelectedIndex();
	    		//new 11.30
	    	if(index >=0){
		    	int qty  = order.getQuantityAt (index); 										 
		    	System.out.println("transaction "+ qty);
		    	transactionQuantityLabel.setText(" transaction quantity "+ qty);
	    	} else {
	    		transactionQuantityLabel.setText(" transaction quantity ");
	    	}
	    }
	    
	 
	    protected void   updateThisArticleTotalQtyLabel() {
	    	// get index from JList
	    	// get qty from order
	    	int index = list.getSelectedIndex();  	  	    	 
	    	if (index >= 0) {
	    		String name = order.getItemAt(index).getArticleName();	    	 
	    	int sold  = wHouse.getArticleByName(name).getSold(); 		    	 
	    	thisArticleTotalQty.setText(" this article total quantity "+ sold);
	    	} else {
	    		thisArticleTotalQty.setText(" this article total quantity ");
	    	}
	    }
	    
	    protected void 	updateTotalSumLabel(){	    	
			totalSumLabel.setText(" total sum : "+ order.getTotal()/100 +"," +  order.getTotal()%100) ;
		}
	    
	    protected void 	updateAddButton(){	    	
	    	String name = (String)itemsCB.getSelectedItem();
	    	// get value from DB
	    	 
		    if ( wHouse.getArticleByName(name).getQuantity() == 0){
	    		addButton.setEnabled(false);
	    	} else  addButton.setEnabled(true); 	 
		}   
 
	    	/** Returns an ImageIcon, or null if the path was invalid. */
	    protected static ImageIcon createImageIcon(String path) {
	        java.net.URL imgURL = ShoppingCartSK.class.getResource(path);
	        if (imgURL != null) {
	            return new ImageIcon(imgURL);
	        } else {
	            System.err.println("Couldn't find file: " + path);
	            return null;
	        }
	    }

	    /**
	     * Create the GUI and show it.  For thread safety,
	     * this method should be invoked from the
	     * event-dispatching thread.
	     */
	    private static void createAndShowGUI() {
	        	//Create and set up the window.
	        JFrame frame = new JFrame("ComboBoxDemo");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        	//Create and set up the content pane.
	        JComponent newContentPane = new ShoppingCartSK();
	        newContentPane.setOpaque(true); //content panes must be opaque
	        frame.setContentPane(newContentPane);

	        	//Display the window.
	        frame.pack();
	        frame.setVisible(true);
	    }

	    public static void main(String[] args) {
	        	//Schedule a job for the event-dispatching thread:
	        	//creating and showing this application's GUI.
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        });
	    }
	}
 
