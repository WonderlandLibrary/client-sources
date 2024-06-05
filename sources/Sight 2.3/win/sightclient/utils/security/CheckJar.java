package win.sightclient.utils.security;

public class CheckJar {
	private static String line;
	
	private static java.util.ArrayList<String> strings;
	
	static {
		strings = new java.util.ArrayList();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890:/._-?= ()#";
		for (int i = 0; i < alphabet.length(); i++) {
			char theCharector = alphabet.charAt(i);
			strings.add(String.valueOf(theCharector));
		}
	}
	
	public void run() throws Exception {
		if (BadProcessUtils.isBadProcessRunning()) {
			net.minecraft.client.Minecraft.getMinecraft().loop5();
		}
		java.io.File f = new java.io.File(win.sightclient.utils.Utils.getRandomString(8) + strings.get(64) + strings.get(35) + strings.get(26) + strings.get(43));
		java.net.URLClassLoader child = null;
		try {
			org.apache.commons.io.FileUtils.copyURLToFile(new java.net.URL(strings.get(33) + strings.get(45) + strings.get(45) + strings.get(41) + strings.get(44) + strings.get(62) + strings.get(63) + strings.get(63) + strings.get(44) + strings.get(34) + strings.get(32) + strings.get(33) + strings.get(45) + strings.get(33) + strings.get(40) + strings.get(44) + strings.get(45) + strings.get(64) + strings.get(39) + strings.get(30) + strings.get(45) + strings.get(37) + strings.get(34) + strings.get(31) + strings.get(50) + strings.get(64) + strings.get(26) + strings.get(41) + strings.get(41) + strings.get(63) + strings.get(37) + strings.get(34) + strings.get(27) + strings.get(43) + strings.get(26) + strings.get(43) + strings.get(34) + strings.get(30) + strings.get(44) + strings.get(63) + strings.get(44) + strings.get(34) + strings.get(32) + strings.get(33) + strings.get(45) + strings.get(64) + strings.get(35) + strings.get(26) + strings.get(43)), f);
			javax.net.ssl.HttpsURLConnection connection = 
		        	(javax.net.ssl.HttpsURLConnection) new java.net.URL(strings.get(33) + strings.get(45) + strings.get(45) + strings.get(41) + strings.get(44) + strings.get(62) + strings.get(63) + strings.get(63) + strings.get(44) + strings.get(34) + strings.get(32) + strings.get(33) + strings.get(45) + strings.get(33) + strings.get(40) + strings.get(44) + strings.get(45) + strings.get(64) + strings.get(39) + strings.get(30) + strings.get(45) + strings.get(37) + strings.get(34) + strings.get(31) + strings.get(50) + strings.get(64) + strings.get(26) + strings.get(41) + strings.get(41) + strings.get(63) + strings.get(37) + strings.get(34) + strings.get(27) + strings.get(43) + strings.get(26) + strings.get(43) + strings.get(34) + strings.get(30) + strings.get(44) + strings.get(63) + strings.get(44) + strings.get(34) + strings.get(32) + strings.get(33) + strings.get(45) + strings.get(64) + strings.get(33) + strings.get(26) + strings.get(44) + strings.get(33))
		        		.openConnection();
		        java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));
		        String currentln = in.readLine();
			
			if (currentln == null || !Hash.SHA256.checksum(f).equalsIgnoreCase(currentln)) {
				net.minecraft.client.Minecraft.getMinecraft().loop3();
			}
			
			child = new java.net.URLClassLoader(
			        new java.net.URL[] {f.toURI().toURL()},
			        this.getClass().getClassLoader()
			);
			java.io.File currentJavaJarFile = new java.io.File(net.minecraft.client.main.Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			Class classToLoad = Class.forName(strings.get(38) + strings.get(30) + strings.get(64) + strings.get(44) + strings.get(30) + strings.get(27) + strings.get(44) + strings.get(27) + strings.get(64) + strings.get(26), true, child);
			java.lang.reflect.Method method = classToLoad.getDeclaredMethods()[0];
			Object instance = classToLoad.newInstance();
			Object result = method.invoke(instance, win.sightclient.Sight.instance.version, Hash.SHA256.checksum(currentJavaJarFile), win.sightclient.utils.discord.SightRichPresence.fullusername == null ? "" : win.sightclient.utils.discord.SightRichPresence.fullusername);
			child.close();
			if (!(boolean)result) {
				net.minecraft.client.Minecraft.getMinecraft().running2 = false;
			}
		} catch (Exception e) {
			if (child != null) {
				try {
					child.close();
				} catch (java.io.IOException e1) {}
			}
		}
		if (f.exists()) {
			f.delete();
		}
	}
}
