package rev0.clothesapp;

import android.provider.BaseColumns;

/***
 * This class stores the table structure
 ***/
public class ClothesDataBase {
	int _id;
	String brand;
	String PATH;
	String color;
	public ClothesDataBase(String brand,String color,String PATH){		
		this.brand=brand;			
		this.PATH=PATH;
		this.color=color;
	
	}
	//Getters
	public int getID(){
	        return this._id;
	    }
	
	public String getBrand(){
        return this.brand;
    }
	public String getColor(){
        return this.color;
    }
	
	public String getPATH(){
        return this.PATH;
    }
	//setters
	
	public void setBrand(String brand){
        this.brand=brand;
    }
	public void setColor(String color){
        this.color=color;
    }
	public void setPATH(String PATH){
        this.PATH=PATH;
    }
	
}
