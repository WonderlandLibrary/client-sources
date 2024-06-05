package gq.vapu.czfclient.IRC;

import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Util.Helper;

public class IRCThread extends Thread {
    @Override
    public void run(){
        IRC.connect();
        while(true){
            IRC.handleInput();
            if(!ModuleManager.getModuleByClass(IRC.class).isEnabled()){
                Helper.sendMessage("¡ì4IRC Disconnected");
                break;
            }
        }
    }
}
