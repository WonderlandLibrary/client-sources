package win.sightclient.utils.security;

public class BadProcessUtils {

	private static String[] badProcesses = new String[] {"fiddler", "wireshark", /*"eclipse",*/ "intelij"};
    
	public static boolean isBadProcessRunning() {
		try {
			java.util.List<org.jutils.jprocesses.model.ProcessInfo> processesList = org.jutils.jprocesses.JProcesses.getProcessList();
		    
		    for (final org.jutils.jprocesses.model.ProcessInfo processInfo : processesList) {
		    	for (String str : badProcesses) {
		    		if (processInfo.getName().toLowerCase().contains(str)) {
		    			return true;
		    		}
		    	}
		    }
		} catch (Exception e) {
			return true;
		}
	    return false;
    }
}
