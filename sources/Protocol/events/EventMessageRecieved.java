package events;

import darkmagician6.EventCancellable;

public class EventMessageRecieved extends EventCancellable{
	 private String message;
	  private boolean cancel;
	  
	  public EventMessageRecieved(String string)
	  {
	    this.message = string;
	  }
	  
	  public String getMessage()
	  {
	    return this.message;
	  }
	  
	  public boolean isCancelled()
	  {
	    return this.cancel;
	  }
	  
	  public void setCancelled(boolean shouldCancel)
	  {
	    this.cancel = shouldCancel;
	  }
	  
	  public void setMessage(String message)
	  {
	    this.message = message;
	  }
}
