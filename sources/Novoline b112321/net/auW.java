package net;

import net.Q5;
import net.aSv;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;

public class auW implements Q5 {
   private C0FPacketConfirmTransaction a;

   public auW(C0FPacketConfirmTransaction var1) {
      this.a = var1;
   }

   @aSv
   public int getWindowID() {
      return this.a.getWindowId();
   }

   @aSv
   public short getTransactionID() {
      return this.a.getID();
   }

   @aSv
   public boolean isAccepted() {
      return this.a.isAccepted();
   }

   @aSv
   public void setWindowID(int var1) {
      this.a.setWindowId(var1);
   }

   @aSv
   public void setTransactionID(short var1) {
      this.a.setID(var1);
   }

   @aSv
   public void setAccepted(boolean var1) {
      this.a.setAccepted(true);
   }

   @aSv
   public Packet getPacket() {
      return this.a;
   }

   @aSv
   public String getName() {
      return "0x0F";
   }
}
