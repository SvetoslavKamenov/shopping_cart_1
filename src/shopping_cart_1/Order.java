package shopping_cart_1;

import java.util.ArrayList;

public class Order {
		// requires OrderItem.java
		// add / remove from the list methods  automatically update the total !
	
	private ArrayList<OrderItem>  list; 
		// accumulated sum in cents to be paid
		// automatically updated
	private int total ; 				 
	 	
	public Order (){
		list =  new ArrayList<OrderItem> () ; 	 
		total = 0; 	// 							 
	}
	
	public int getSize (){
		return list.size(); 	 
	}
	
	public OrderItem getItemAt (int index ) {		 		 
		return list.get(index );
	}
	 	
	public int getQuantityAt(int index){	 
		return list.get(index).getQty ();
	}
	
		// deep access ( shortcut )
		// adds OrderItem to the list and updates the total  
	 void addItem(PetArticle article, int qty) {	 
		OrderItem item = new OrderItem (article, qty);		
		list.add(item);
		total += item.getPrice();	 
	}
	 
	 	// removes OrderItem from list and decrements the total  
	 void removeAt(  int index) {		 
		 OrderItem item = list.get(index);	 
		 total -= item.getPrice();
		 list.remove(index);					 
	}
 
	 int getTotal() {
		 return this.total;
	 }
	 
}
