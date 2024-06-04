package org.silvertunnel_ng.netlib.api.impl;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;


















public class DataNetSocketUtil
{
  public DataNetSocketUtil() {}
  
  public static DataNetSocketPair createDataNetSocketPair()
    throws IOException
  {
    DataNetSocketPair result = new DataNetSocketPair();
    

    PipedInputStream fromHigherLayerIS = new PipedInputStream();
    PipedOutputStream fromHigherLayerOS = new PipedOutputStream(fromHigherLayerIS);
    


    PipedInputStream toHigherLayerIS = new PipedInputStream();
    PipedOutputStream toHigherLayerOS = new PipedOutputStream(toHigherLayerIS);
    


    result.setSocket(new DataNetSocketImpl(toHigherLayerIS, fromHigherLayerOS));
    
    result.setInvertedSocked(new DataNetSocketImpl(fromHigherLayerIS, toHigherLayerOS));
    

    return result;
  }
}
