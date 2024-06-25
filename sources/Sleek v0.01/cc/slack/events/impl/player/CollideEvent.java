package cc.slack.events.impl.player;

import cc.slack.events.Event;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;

public class CollideEvent extends Event {
   public Block block;
   public AxisAlignedBB boundingBox;
   public double x;
   public double y;
   public double z;

   public Block getBlock() {
      return this.block;
   }

   public AxisAlignedBB getBoundingBox() {
      return this.boundingBox;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public void setBlock(Block block) {
      this.block = block;
   }

   public void setBoundingBox(AxisAlignedBB boundingBox) {
      this.boundingBox = boundingBox;
   }

   public void setX(double x) {
      this.x = x;
   }

   public void setY(double y) {
      this.y = y;
   }

   public void setZ(double z) {
      this.z = z;
   }

   public CollideEvent(Block block, AxisAlignedBB boundingBox, double x, double y, double z) {
      this.block = block;
      this.boundingBox = boundingBox;
      this.x = x;
      this.y = y;
      this.z = z;
   }
}
