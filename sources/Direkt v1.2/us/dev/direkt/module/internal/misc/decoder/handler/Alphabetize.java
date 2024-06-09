package us.dev.direkt.module.internal.misc.decoder.handler;


public class Alphabetize {
	
    public String keyMaker(String str){
    	char a= 'a';
    	String temp="";
    	for(int i = 0;i<26;i++){
            for(int s = 0; s<str.length();s++){
            	char q = str.charAt(s);
                    
            	if(q==a){
            		temp+=q;
            	}
            }
            a++;
    	}
    	return temp;
    }

    /*public static void main (String[]args){
    	Alphabetize km = new Alphabetize();
    	System.out.println(km.keyMaker("look"));
    }*/
    
}

