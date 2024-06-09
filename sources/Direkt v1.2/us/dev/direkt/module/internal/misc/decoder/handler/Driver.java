package us.dev.direkt.module.internal.misc.decoder.handler;

import us.dev.direkt.Direkt;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class Driver {
	private static final Alphabetize q = new Alphabetize();
	private static final Alphabits x = new Alphabits();
	private static final Map<String,Set<String>> wordmap = new HashMap<>();
	private static String word;
	public static String UnScramble(String arg){
		word = arg;
//		fix this
		File f = new File(Direkt.getInstance().getClientDirectory() + "\\Dic.txt");
		inputFile(f);
		String message = "";
		
		String key = q.keyMaker(userInteraction());
		
		for(String r: x.makeKeys(key))
		{
			if(wordmap.get(r)!=null){
				
				
				
				for(String l :wordmap.get(r)){
					message +=l+" ";
					
				}
				
			}
			
		}
		if(!message.equals(""))
			return message;
		return null;
	}
	
	private static String userInteraction() {
//  	                      while(m.length()>16){
//                        	m =JOptionPane.showInputDialog("Enter Something less than 16 letters");
//      	                  }
		x.jumble();

		return word;
	}
	
	private static void inputFile(File f) 
	{
                        
		Scanner in;
                      
		try
		{
			in = new Scanner(f);
                   
			while(in.hasNext())
			{
                                     
				addWord(in.next());
                                    
			}
                           
		}
		catch(IOException i)
		{
			System.out.println("Error: " + i.getMessage());
		}
	}

	private static void addWord(String next) {
		// TODO Auto-generated method stub
		
		Alphabetize q = new Alphabetize();
		String tempkey = q.keyMaker(next);
		if(!wordmap.containsKey(tempkey)){
			wordmap.put(tempkey, new HashSet<>());
			wordmap.get(tempkey).add(next);
		}
		else{
			wordmap.get(tempkey).add(next);
		}
	}

}

