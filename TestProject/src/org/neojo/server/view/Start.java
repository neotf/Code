package org.neojo.server.view;

import java.io.File;
import java.io.IOException;

public class Start {
	private static boolean check(){
		File file=new File("config\\conn.properties");    
		if(!file.exists())
		{    
		    try {    
		        file.createNewFile();
		    } catch (IOException e) {    
		        // TODO Auto-generated catch block    
		        e.printStackTrace();    
		    }    
		}    
		return false;
	}
	public static void main(String[] args) {
		check();
		
	}
}
