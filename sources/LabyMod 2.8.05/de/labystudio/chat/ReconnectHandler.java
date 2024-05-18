package de.labystudio.chat;

public class ReconnectHandler extends Thread
{
    private ClientConnection clientConnection;
    private long lastTry;

    public ReconnectHandler(ClientConnection clientConnection)
    {
        this.clientConnection = clientConnection;
        this.lastTry = System.currentTimeMillis();
    }

    public void run()
    {
        while (true)
        {
            if (this.clientConnection.ch.isOpen())
            {
                try
                {
                    this.wait();
                }
                catch (InterruptedException interruptedexception)
                {
                    interruptedexception.printStackTrace();
                }
            }
            else if (this.lastTry + 5000L < System.currentTimeMillis())
            {
                this.lastTry = System.currentTimeMillis();
                this.clientConnection.init();
                this.clientConnection.connect();
            }
        }
    }
}
