package shopping_cart_1;

public class OrderItem {
	// describes a transaction  
 
	PetArticle article;  
	int quantity ;		 	
	int price ;			// automatically calculated  
		 
	OrderItem (PetArticle article, int quantity){
		this.article = article;	 
		this.quantity = quantity; 
		this.price = article.getPrice() * quantity; 
		 
	}
	
	public String getArticleName() {			 
		return this.article.getName();		 
	}
	
	public int getQty() {		
		 return this.quantity;		 
	}	
	public double getPrice() {	 
		 return this.price;		 
	}	
}