package shopping_cart_1;
 
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class PetWarehouse {
		// data base implementation
		// requires file PetArticle.java 
	 
	private ArrayList<PetArticle>  database; 
 
	public PetWarehouse () {
		PetArticle pet;
		
		database = new ArrayList<PetArticle>();
		pet = new PetArticle (100,"Bird",250,"angry-Red","nasty girl,  price 2.50 ",createImageIcon("images/Bird.gif"), 5);
		database.add(pet);
		pet = new PetArticle (101,"Cat",480,"Black","very nice character, price 4.80 ",createImageIcon("images/Bird.gif"), 2);
		database.add(pet);
		pet = new PetArticle (102,"Dog", 500,"white in black spots","troublesome, price 5.00",createImageIcon("images/Bird.gif"),10);
		database.add(pet);
		pet = new PetArticle (103,"Rabbit",210,"white","very shy fellow, price 2.10",createImageIcon("images/Bird.gif"), 2);
		database.add(pet);
		pet = new PetArticle (103,"Pig",5500,"nice pink","good boy, price 55.00 ",createImageIcon("images/Bird.gif"),1);
		database.add(pet); 				 		  		 		 
	}
	
	public PetArticle getArticleByName(String name){
		for (int i = 0; i < database.size(); i++) {
			if (database.get(i).getName() == name){
				return database.get(i);
			}
		}
		return null;
	}
		// used to initialize ComboBox
	public String[] getAllArticleNames(){
		String[] names = new String[database.size()];
		for (int i = 0; i < database.size(); i++) {
			names[i] = database.get(i).getName();
		}
		return names;
	} 
		
	public int getArtPrice (String articleName) {	 
		PetArticle article = getArticleByName(articleName);		
		if (article != null )
			return	article.getPrice() ;
		else
			System.out.println("name not found");		
		return -1;
	}
 
  
	public int getArtQuantity (String articleName) {
		for (int i = 0; i < database.size(); i++) {
			if (database.get(i).getName() == articleName)
				return database.get(i).getQuantity () ;	  		
		}
		System.out.println("name not found");
		return -1;
	}
		 
		 
		// sells the required quantity or as much as possible 
		// returns the sold qty  		 
	public int decArtQuantity (String articleName, int itemsToSell) {
		
		for (int i = 0; i < database.size(); i++) {
			PetArticle item ;
			item = database.get(i);
			 if (item.getName() == articleName)
				  	// found
				if(item.getQuantity() >= itemsToSell) {					 
					item.setQuantity(item.getQuantity() - itemsToSell);						 
					return  itemsToSell;
				} else {
					int temp = item.getQuantity();
					item.setQuantity(0);
					return temp;  
			 }			 
		}
		return -1; // name not found				 
	}
	
		// increment article quantity
		// returns the new qty  or  -1 if name not found	 	 
	public int incArtQuantity (String articleName, int itemsToStore) {		
		for (int i = 0; i < database.size(); i++) {
			PetArticle item ;
			item = database.get(i);
			 if (item.getName() == articleName) {
				  	// found
				item.setQuantity (item.getQuantity() + itemsToStore);					 
				return item.getQuantity()  ;				   
			 }			 
		}
		return -1; // name not found				 
	}
 
	// utility, gets images from files ... 
	// copied from ComboBoxDemo
	// kind of magic for me by now
 protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = ShoppingCartSK.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
	
	public static void main(String[] args) {
		 

	}

}
