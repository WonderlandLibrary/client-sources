package net.minecraft.tileentity;

import com.google.gson.JsonParseException;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S33PacketUpdateSign;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentProcessor;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntitySign extends TileEntity {
   public final IChatComponent[] signText = new IChatComponent[]{new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText("")};
   private final CommandResultStats field_174883_i = new CommandResultStats();
   public int lineBeingEdited = -1;
   private boolean isEditable = true;
   private static final String __OBFID = "CL_00000363";
   private EntityPlayer field_145917_k;

   public void readFromNBT(NBTTagCompound var1) {
      this.isEditable = false;
      super.readFromNBT(var1);
      ICommandSender var2 = new ICommandSender(this) {
         private static final String __OBFID = "CL_00002039";
         final TileEntitySign this$0;

         public Entity getCommandSenderEntity() {
            return null;
         }

         public BlockPos getPosition() {
            return this.this$0.pos;
         }

         public World getEntityWorld() {
            return this.this$0.worldObj;
         }

         public IChatComponent getDisplayName() {
            return new ChatComponentText(this.getName());
         }

         public boolean canCommandSenderUseCommand(int var1, String var2) {
            return true;
         }

         public boolean sendCommandFeedback() {
            return false;
         }

         public void func_174794_a(CommandResultStats.Type var1, int var2) {
         }

         {
            this.this$0 = var1;
         }

         public String getName() {
            return "Sign";
         }

         public Vec3 getPositionVector() {
            return new Vec3((double)this.this$0.pos.getX() + 0.5D, (double)this.this$0.pos.getY() + 0.5D, (double)this.this$0.pos.getZ() + 0.5D);
         }

         public void addChatMessage(IChatComponent var1) {
         }
      };

      for(int var3 = 0; var3 < 4; ++var3) {
         String var4 = var1.getString(String.valueOf((new StringBuilder("Text")).append(var3 + 1)));

         try {
            IChatComponent var5 = IChatComponent.Serializer.jsonToComponent(var4);

            try {
               this.signText[var3] = ChatComponentProcessor.func_179985_a(var2, var5, (Entity)null);
            } catch (CommandException var7) {
               this.signText[var3] = var5;
            }
         } catch (JsonParseException var8) {
            this.signText[var3] = new ChatComponentText(var4);
         }
      }

      this.field_174883_i.func_179668_a(var1);
   }

   static CommandResultStats access$0(TileEntitySign var0) {
      return var0.field_174883_i;
   }

   public EntityPlayer func_145911_b() {
      return this.field_145917_k;
   }

   public CommandResultStats func_174880_d() {
      return this.field_174883_i;
   }

   public boolean func_174882_b(EntityPlayer var1) {
      ICommandSender var2 = new ICommandSender(this, var1) {
         final TileEntitySign this$0;
         private final EntityPlayer val$p_174882_1_;
         private static final String __OBFID = "CL_00002038";

         public String getName() {
            return this.val$p_174882_1_.getName();
         }

         public IChatComponent getDisplayName() {
            return this.val$p_174882_1_.getDisplayName();
         }

         public Vec3 getPositionVector() {
            return new Vec3((double)this.this$0.pos.getX() + 0.5D, (double)this.this$0.pos.getY() + 0.5D, (double)this.this$0.pos.getZ() + 0.5D);
         }

         public void func_174794_a(CommandResultStats.Type var1, int var2) {
            TileEntitySign.access$0(this.this$0).func_179672_a(this, var1, var2);
         }

         public void addChatMessage(IChatComponent var1) {
         }

         public World getEntityWorld() {
            return this.val$p_174882_1_.getEntityWorld();
         }

         {
            this.this$0 = var1;
            this.val$p_174882_1_ = var2;
         }

         public boolean sendCommandFeedback() {
            return false;
         }

         public Entity getCommandSenderEntity() {
            return this.val$p_174882_1_;
         }

         public boolean canCommandSenderUseCommand(int var1, String var2) {
            return true;
         }

         public BlockPos getPosition() {
            return this.this$0.pos;
         }
      };

      for(int var3 = 0; var3 < this.signText.length; ++var3) {
         ChatStyle var4 = this.signText[var3] == null ? null : this.signText[var3].getChatStyle();
         if (var4 != null && var4.getChatClickEvent() != null) {
            ClickEvent var5 = var4.getChatClickEvent();
            if (var5.getAction() == ClickEvent.Action.RUN_COMMAND) {
               MinecraftServer.getServer().getCommandManager().executeCommand(var2, var5.getValue());
            }
         }
      }

      return true;
   }

   public Packet getDescriptionPacket() {
      IChatComponent[] var1 = new IChatComponent[4];
      System.arraycopy(this.signText, 0, var1, 0, 4);
      return new S33PacketUpdateSign(this.worldObj, this.pos, var1);
   }

   public boolean getIsEditable() {
      return this.isEditable;
   }

   public void writeToNBT(NBTTagCompound var1) {
      super.writeToNBT(var1);

      for(int var2 = 0; var2 < 4; ++var2) {
         String var3 = IChatComponent.Serializer.componentToJson(this.signText[var2]);
         var1.setString(String.valueOf((new StringBuilder("Text")).append(var2 + 1)), var3);
      }

      this.field_174883_i.func_179670_b(var1);
   }

   public void setEditable(boolean var1) {
      this.isEditable = var1;
      if (!var1) {
         this.field_145917_k = null;
      }

   }

   public void func_145912_a(EntityPlayer var1) {
      this.field_145917_k = var1;
   }
}
