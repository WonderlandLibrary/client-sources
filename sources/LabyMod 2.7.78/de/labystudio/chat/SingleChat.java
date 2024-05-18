package de.labystudio.chat;

import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.packets.PacketMessage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class SingleChat
{
    private final int id;
    private LabyModPlayer friend;
    private List<MessageChatComponent> messages;

    public SingleChat(int id, LabyModPlayer friend, List<MessageChatComponent> messages)
    {
        this.id = id;
        this.friend = friend;
        this.messages = messages;

        if (this.messages == null)
        {
            this.messages = new ArrayList();
        }
    }

    public int getId()
    {
        return this.id;
    }

    public LabyModPlayer getFriend()
    {
        return this.friend;
    }

    public List<MessageChatComponent> getMessages()
    {
        return this.messages;
    }

    public void addMessage(final MessageChatComponent message)
    {
        Collections.reverse(this.messages);
        this.messages.add(message);
        Collections.reverse(this.messages);
        (new Thread()
        {
            public void run()
            {
                try
                {
                    ChatHandler.getHandler().getConnection().prepareStatement("INSERT INTO single_chat_messages (single_chats_id, sender, sender_message, sent_time) VALUES (" + SingleChat.this.getId() + ", \'" + message.getSender() + "\', \'" + message.getMessage() + "\', " + System.currentTimeMillis() + ")").executeUpdate();
                }
                catch (SQLException sqlexception)
                {
                    sqlexception.printStackTrace();
                }
            }
        }).start();
        message.setChat(this);

        if (message.getSender().equalsIgnoreCase(LabyMod.getInstance().getPlayerName()))
        {
            if (ConfigManager.settings.playSounds)
            {
                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("random.pop"), 1.5F));
            }

            if (!(message instanceof TitleChatComponent))
            {
                LabyMod.getInstance().client.getClientConnection().sendPacket(new PacketMessage(LabyMod.getInstance().client.build(), this.getFriend(), message.getMessage(), 0L, 0.0D, System.currentTimeMillis()));
            }
        }
        else if (ConfigManager.settings.playSounds)
        {
            LabyMod.getInstance().getClient();

            if (!Client.isBusy() && LabyMod.getInstance().getClient().hasNotifications(this.getFriend()))
            {
                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("random.pop"), 2.5F));
            }
        }

        LabyMod.getInstance().newMessage = true;
    }

    public void updateFriend(LabyModPlayer player)
    {
        this.friend = player;
    }

    public void draw(int xStart, int yStart)
    {
    }

    public MessageChatComponent findDownloadableMessage(String name)
    {
        for (MessageChatComponent messagechatcomponent : this.getMessages())
        {
            if (messagechatcomponent.getMessage().equalsIgnoreCase(name))
            {
                return messagechatcomponent;
            }
        }

        return null;
    }
}
