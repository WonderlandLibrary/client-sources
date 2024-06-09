package me.hexxed.mercury.web;

import java.util.Date;

public class WebData { private Date time;
  private String username;
  private int x;
  private int y;
  private int z;
  private String server;
  
  public WebData(Date time, String username, int x, int y, int z, String server) { this.time = time;
    this.username = username;
    this.x = x;
    this.y = y;
    this.z = z;
    this.server = server;
  }
  
  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
  public int getZ() {
    return z;
  }
  
  public String getUsername() {
    return username;
  }
  
  public Date getTime() {
    return time;
  }
  
  public String getServer() {
    return server;
  }
}
