package shopping_cart_1;
	 
	
	import javax.swing.*;
	 
public class PetArticle {
	//public static int soldItems;
	
	private int ID; 
	private String name;
	private int price; // the prise is in cents
	private String color;
	private String specialMarks;
	private ImageIcon image ;
	private int inStock;	
	private int sold;
	
	// methods 
	// PetArticle (i_id, S_name, int_price, S_color, S_specialMarks, ImageIcon_image, i_quantity ) 
	// String getName ()
	// int getPrice ()
	// int getSold ()
	// void addSold (int sold)
	// void decreaseSold (int sold)
	// int getQuantity ()
	// void setQuantity (int_quantity)
	// int addQuantity (int_addent)
	// int reduceQuantity (int_subtrahend)
	// String getSpecialMarks ()
	// 
	public   PetArticle (int id, String name, int price, String color, String specialMarks, 
			ImageIcon image, int quantity ) {
		ID = id;
		this.name = name;
		this.price = price;
		this.color = color;
		this.specialMarks = specialMarks;
		this.image = image;
		this.inStock = quantity;
		this.sold = 0;
	}
	
	public   PetArticle (int id, String name, int price, String color, String specialMarks ) {
		ID = id;
		this.name = name;
		this.price = price;
		this.color = color;
		this.specialMarks = specialMarks;
		this.sold = 0;
	}
	
	public   PetArticle (int id, String name, int price ) {
		ID = id;
		this.name = name;
		this.price = price;
		this.color = "usual";
		this.specialMarks = "none";	
		this.sold = 0;
	}
	
	public String getName (){
		return this.name  ;	
	}
	
	public int getPrice (){
		return this.price  ;	
	}
	
	public int getSold (){		 		 		 
		return 	this.sold;				 
	} 	
	public void addSold (int sold){		 		 		 
		this.sold += sold ;				 
	} 	
	public void decreaseSold (int sold){		 		 		 
		this.sold -= sold ;				 
	} 	
	public String getSpecialMarks (){
		return this.specialMarks  ;	
	}
		// not needed ?
	public void setQuantity (int quantity){
		if (quantity >= 0)
		 this.inStock = quantity;	
	}
	public int getQuantity (){
		 return this.inStock;	  
	}
	public int reduceQuantity (int subtrahend){
			// if insufficient number in stock
			// returns number of sold items, zero left in stock
		if (this.inStock >= subtrahend){
			this.inStock -= subtrahend;
			this.sold += subtrahend;
			return 	subtrahend;			
		} else {
			int reduce = this.inStock;
			this.inStock = 0;
			this.sold += reduce;
			return reduce; 
		}			 
	} 
	public int addQuantity (int addent){		 
		// returns new quantity in stock	 
		this.inStock += addent;
		return 	addent;				 
	} 				  		
}


