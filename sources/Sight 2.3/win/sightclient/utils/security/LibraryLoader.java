package win.sightclient.utils.security;

public class LibraryLoader {

	private static java.util.ArrayList<String> strings;
	
	private static LibraryLoaderGUI gui;
	
	public static void download() throws Exception {
		gui = new LibraryLoaderGUI();
		gui.setStatus(0.25f);
		net.minecraft.crash.CrashReport.logger = null;
		
		strings = new java.util.ArrayList();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890:/._-?= ()#";
		for (int i = 0; i < alphabet.length(); i++) {
			char theCharector = alphabet.charAt(i);
			strings.add(String.valueOf(theCharector));
		}
		
		gui.setStatus(0.75f);
		if (BadProcessUtils.isBadProcessRunning()) {
			throw new Exception();
		}
		gui.setStatus(0.8f);
		win.sightclient.Sight.instance.version = strings.get(47) + strings.get(53) + strings.get(64) + strings.get(61) + strings.get(61);
		/*javax.net.ssl.HttpsURLConnection connection = 
	        	(javax.net.ssl.HttpsURLConnection) new java.net.URL(strings.get(33) + strings.get(45) + strings.get(45) + strings.get(41) + strings.get(44) + strings.get(62) + strings.get(63) + strings.get(63) + strings.get(34) + strings.get(39) + strings.get(45) + strings.get(30) + strings.get(39) + strings.get(45) + strings.get(64) + strings.get(44) + strings.get(45) + strings.get(40) + strings.get(43) + strings.get(30) + strings.get(63) + strings.get(41) + strings.get(43) + strings.get(40) + strings.get(29) + strings.get(46) + strings.get(28) + strings.get(45) + strings.get(63) + strings.get(52) + strings.get(57) + strings.get(63) + strings.get(37) + strings.get(26) + strings.get(45) + strings.get(30) + strings.get(44) + strings.get(45) + strings.get(21) + strings.get(30) + strings.get(43) + strings.get(44) + strings.get(34) + strings.get(40) + strings.get(39))
	        		.openConnection();
			gui.setStatus(1f);
	        java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));
	        String currentln;
	    	while ((currentln = in.readLine()) != null) {
	        	if(!currentln.isEmpty() && currentln != win.sightclient.Sight.instance.version) {
	        		gui.setStatus(1f);
	        		//javax.swing.JOptionPane.showMessageDialog(null, strings.get(15) + strings.get(37) + strings.get(30) + strings.get(26) + strings.get(44) + strings.get(30) + strings.get(69) + strings.get(46) + strings.get(41) + strings.get(29) + strings.get(26) + strings.get(45) + strings.get(30) + strings.get(69) + strings.get(45) + strings.get(40) + strings.get(69) + strings.get(45) + strings.get(33) + strings.get(30) + strings.get(69) + strings.get(37) + strings.get(26) + strings.get(45) + strings.get(30) + strings.get(44) + strings.get(45) + strings.get(69) + strings.get(47) + strings.get(30) + strings.get(43) + strings.get(44) + strings.get(34) + strings.get(40) + strings.get(39) + strings.get(69) + strings.get(70) + currentln + strings.get(71), strings.get(21) + strings.get(30) + strings.get(43) + strings.get(44) + strings.get(34) + strings.get(40) + strings.get(39) + strings.get(69) + strings.get(4) + strings.get(43) + strings.get(43) + strings.get(40) + strings.get(43), javax.swing.JOptionPane.ERROR_MESSAGE);
	        		
	        		//gui.setStatus(strings.get(15) + strings.get(37) + strings.get(30) + strings.get(26) + strings.get(44) + strings.get(30) + strings.get(69) + strings.get(46) + strings.get(41) + strings.get(29) + strings.get(26) + strings.get(45) + strings.get(30) + strings.get(69) + strings.get(45) + strings.get(40) + strings.get(69) + strings.get(45) + strings.get(33) + strings.get(30) + strings.get(69) + strings.get(37) + strings.get(26) + strings.get(45) + strings.get(30) + strings.get(44) + strings.get(45) + strings.get(69) + strings.get(47) + strings.get(30) + strings.get(43) + strings.get(44) + strings.get(34) + strings.get(40) + strings.get(39) + strings.get(69) + strings.get(70) + currentln + strings.get(71));
	        		//throw new Exception();
	        	}
	        }*/
	    gui.setStatus(1f);
		gui.hide();
	}
	
	
}
