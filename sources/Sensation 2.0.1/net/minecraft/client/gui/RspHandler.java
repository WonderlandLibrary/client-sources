package net.minecraft.client.gui;

import net.minecraft.client.multiplayer.WorldClient;

/**
 * @author antja03
 */
public class RspHandler {
    private byte[] rsp = null;

    public synchronized boolean handleResponse(byte[] rsp) {
        this.rsp = rsp;
        this.notify();
        return true;
    }

    public synchronized void waitForResponse() {
        while (this.rsp == null) {
            try {
                this.wait();
            } catch (InterruptedException e) {
            }
        }

     //   String data = new String(this.rsp);
    //    String decryptedData = WorldClient.decrypt(data);

      //  String[] receivedData = decryptedData.split(",");
    //    if (receivedData[0].equalsIgnoreCase("authenticated")) {
      //      if (receivedData.length != 4) {
       //        System.exit(130);
     //           return;
    //        }

        //    int id = -1;
         //   String name = "";
         //   MemberGroup group = null;

       /*     String[] idData = receivedData[1].split("=");
            if (idData[0].equalsIgnoreCase("id") && Mafs.isInteger(idData[1])) {
                id = Integer.valueOf(idData[1]);
            } else {
                for (int i = 0; i < 420; i++) {
                    //Minecraft.getMinecraft().timer.timerSpeed = 1000;
                   // MinecraftServer.getServer().tick();
                }
            }*/

          //  String[] nameData = receivedData[2].split("=");
        //    if (nameData[0].equalsIgnoreCase("name")) {
        //        name = nameData[1];
        //    } else {
        //        for (int i = 0; i < 420 * 2; i++) {
                //    Minecraft.getMinecraft().thePlayer = null;
                //    Minecraft.getMinecraft().theWorld = null;
               //     Minecraft.getMinecraft().theWorld.tick();
               // }
                //Minecraft.getMinecraft().thePlayer = null;
             //   Minecraft.getMinecraft().theWorld = null;
            //    Minecraft.getMinecraft().theWorld.tick();
       //     }

        /*    String[] groupData = receivedData[3].split("=");
            if (groupData[0].equalsIgnoreCase("groupid") && Mafs.isInteger(idData[1])) {
                int groupid = Integer.valueOf(groupData[1]);

                for (MemberGroup memberGroup : MemberGroup.values()) {
                    if (memberGroup.getId() == groupid)
                        group = memberGroup;
                }
            } else {
                 //System.exit(100);
            }*/

     //       if (id != -1 && !name.equals("") && group != null) {
             //   GuiStreamIndicator.member = new Member(id, name, group);
      //      } else {
             //   Ethereal.instance.cheatManager.getCheatRegistry().get("KillAura");
    //        }
     //   }

    //if (receivedData[0].equalsIgnoreCase("quit")) {
      //    //  System.exit(0);
      //  }
    }
}
