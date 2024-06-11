package Hydro.command.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

import Hydro.command.Command;
import Hydro.command.CommandExecutor;
import Hydro.util.ChatUtils;
import net.minecraft.client.entity.EntityPlayerSP;

public class IRC implements CommandExecutor {
	
	public static String message = "";
	public boolean noname = true;

	@Override
	public void execute(EntityPlayerSP sender, List<String> args) {
		if(!noname) {
			if(args.size() == 0) {
				ChatUtils.sendMessageToPlayer(".irc [message]");
			} else {
	            StringBuilder sb = new StringBuilder();
	            int i = 0;
	            while (i < args.size()) {
	                sb.append(String.valueOf(args.get(i)) + " ");
	                ++i;
	            }
	            String st = sb.toString();
	    		  System.out.println(st); //Nachricht senden#
	    	    
	    	  /*  sc.close();
	    	    output.close(); */
			}
		  } else {
			ChatUtils.sendMessageToPlayer("Logged in!");
			noname = false;
		  }
	}
	
	public static String host;
	  public static Scanner sc;
	  public static int port;
	  public static Socket client = null;
	  public static PrintStream output = null;
	  public static String name = "Presti";


	  public static void IRC_Conn(String host, int port) {
	    host = host;
	    port = port;
	  }

	  public static void run() throws UnknownHostException, IOException {
	    client = new Socket(host, port);
	    System.out.println("Verbindung zum Server erfolgreich!"); //Verbindung bei MC Start aufbauen ok
	    output = new PrintStream(client.getOutputStream());
	    

	    new Thread(new ReceivedMessagesHandler(client.getInputStream())).start();
	    
	  }
	  
	}

	class ReceivedMessagesHandler implements Runnable {

	  private InputStream server;

	  public ReceivedMessagesHandler(InputStream server) {
	    this.server = server;
	  }

	  public void run() { //das ist der Handler wenn eine Nachricht vom Server eingeht
	    Scanner s = new Scanner(server);
	    String tmp = "";
	    while (s.hasNextLine()) {
	      tmp = s.nextLine();
	      try{
	        if (tmp.charAt(0) == '[') {
	          tmp = tmp.substring(1, tmp.length()-1);
	        }else{
	          try {
	            ChatUtils.sendMessageToPlayer("§c" + getTagValue(tmp).replaceAll("->", "§8->§2"));
	          } catch(Exception ignore){}
	        }
	      }catch (Exception e){
	        //e.printStackTrace();
	      }
	    }
	    s.close();
	  }

	  public static String getTagValue(String xml){
	    return  xml;
	  }

}
