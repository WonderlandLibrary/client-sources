package org.json.simple.parser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;









public class JSONParser
{
  public static final int S_INIT = 0;
  public static final int S_IN_FINISHED_VALUE = 1;
  public static final int S_IN_OBJECT = 2;
  public static final int S_IN_ARRAY = 3;
  public static final int S_PASSED_PAIR_KEY = 4;
  public static final int S_IN_PAIR_VALUE = 5;
  public static final int S_END = 6;
  public static final int S_IN_ERROR = -1;
  private LinkedList handlerStatusStack;
  
  public JSONParser() {}
  
  private Yylex lexer = new Yylex(null);
  private Yytoken token = null;
  private int status = 0;
  
  private int peekStatus(LinkedList statusStack) {
    if (statusStack.size() == 0)
      return -1;
    Integer status = (Integer)statusStack.getFirst();
    return status.intValue();
  }
  



  public void reset()
  {
    token = null;
    status = 0;
    handlerStatusStack = null;
  }
  






  public void reset(Reader in)
  {
    lexer.yyreset(in);
    reset();
  }
  


  public int getPosition()
  {
    return lexer.getPosition();
  }
  
  public Object parse(String s) throws ParseException {
    return parse(s, null);
  }
  
  public Object parse(String s, ContainerFactory containerFactory) throws ParseException {
    StringReader in = new StringReader(s);
    try {
      return parse(in, containerFactory);

    }
    catch (IOException ie)
    {

      throw new ParseException(-1, 2, ie);
    }
  }
  
  public Object parse(Reader in) throws IOException, ParseException {
    return parse(in, null);
  }
  














  public Object parse(Reader in, ContainerFactory containerFactory)
    throws IOException, ParseException
  {
    reset(in);
    LinkedList statusStack = new LinkedList();
    LinkedList valueStack = new LinkedList();
    try
    {
      do {
        nextToken();
        switch (status) {
        case 0: 
          switch (token.type) {
          case 0: 
            status = 1;
            statusStack.addFirst(new Integer(status));
            valueStack.addFirst(token.value);
            break;
          case 1: 
            status = 2;
            statusStack.addFirst(new Integer(status));
            valueStack.addFirst(createObjectContainer(containerFactory));
            break;
          case 3: 
            status = 3;
            statusStack.addFirst(new Integer(status));
            valueStack.addFirst(createArrayContainer(containerFactory));
            break;
          case 2: default: 
            status = -1;
          }
          break;
        
        case 1: 
          if (token.type == -1) {
            return valueStack.removeFirst();
          }
          throw new ParseException(getPosition(), 1, token);
        
        case 2: 
          switch (token.type) {
          case 5: 
            break;
          case 0: 
            if ((token.value instanceof String)) {
              String key = (String)token.value;
              valueStack.addFirst(key);
              status = 4;
              statusStack.addFirst(new Integer(status));
            }
            else {
              status = -1;
            }
            break;
          case 2: 
            if (valueStack.size() > 1) {
              statusStack.removeFirst();
              valueStack.removeFirst();
              status = peekStatus(statusStack);
            }
            else {
              status = 1;
            }
            break;
          case 1: case 3: case 4: default: 
            status = -1;
          }
          
          break;
        
        case 4: 
          switch (token.type) {
          case 6: 
            break;
          case 0: 
            statusStack.removeFirst();
            String key = (String)valueStack.removeFirst();
            Map parent = (Map)valueStack.getFirst();
            parent.put(key, token.value);
            status = peekStatus(statusStack);
            break;
          case 3: 
            statusStack.removeFirst();
            String key = (String)valueStack.removeFirst();
            Map parent = (Map)valueStack.getFirst();
            List newArray = createArrayContainer(containerFactory);
            parent.put(key, newArray);
            status = 3;
            statusStack.addFirst(new Integer(status));
            valueStack.addFirst(newArray);
            break;
          case 1: 
            statusStack.removeFirst();
            String key = (String)valueStack.removeFirst();
            Map parent = (Map)valueStack.getFirst();
            Map newObject = createObjectContainer(containerFactory);
            parent.put(key, newObject);
            status = 2;
            statusStack.addFirst(new Integer(status));
            valueStack.addFirst(newObject);
            break;
          case 2: case 4: case 5: default: 
            status = -1;
          }
          break;
        
        case 3: 
          switch (token.type) {
          case 5: 
            break;
          case 0: 
            List val = (List)valueStack.getFirst();
            val.add(token.value);
            break;
          case 4: 
            if (valueStack.size() > 1) {
              statusStack.removeFirst();
              valueStack.removeFirst();
              status = peekStatus(statusStack);
            }
            else {
              status = 1;
            }
            break;
          case 1: 
            List val = (List)valueStack.getFirst();
            Map newObject = createObjectContainer(containerFactory);
            val.add(newObject);
            status = 2;
            statusStack.addFirst(new Integer(status));
            valueStack.addFirst(newObject);
            break;
          case 3: 
            List val = (List)valueStack.getFirst();
            List newArray = createArrayContainer(containerFactory);
            val.add(newArray);
            status = 3;
            statusStack.addFirst(new Integer(status));
            valueStack.addFirst(newArray);
            break;
          case 2: default: 
            status = -1;
          }
          break;
        case -1: 
          throw new ParseException(getPosition(), 1, token);
        }
        if (status == -1) {
          throw new ParseException(getPosition(), 1, token);
        }
      } while (token.type != -1);
    }
    catch (IOException ie) {
      throw ie;
    }
    
    throw new ParseException(getPosition(), 1, token);
  }
  
  private void nextToken() throws ParseException, IOException {
    token = lexer.yylex();
    if (token == null)
      token = new Yytoken(-1, null);
  }
  
  private Map createObjectContainer(ContainerFactory containerFactory) {
    if (containerFactory == null)
      return new JSONObject();
    Map m = containerFactory.createObjectContainer();
    
    if (m == null)
      return new JSONObject();
    return m;
  }
  
  private List createArrayContainer(ContainerFactory containerFactory) {
    if (containerFactory == null)
      return new JSONArray();
    List l = containerFactory.creatArrayContainer();
    
    if (l == null)
      return new JSONArray();
    return l;
  }
  
  public void parse(String s, ContentHandler contentHandler) throws ParseException {
    parse(s, contentHandler, false);
  }
  
  public void parse(String s, ContentHandler contentHandler, boolean isResume) throws ParseException {
    StringReader in = new StringReader(s);
    try {
      parse(in, contentHandler, isResume);

    }
    catch (IOException ie)
    {

      throw new ParseException(-1, 2, ie);
    }
  }
  
  public void parse(Reader in, ContentHandler contentHandler) throws IOException, ParseException {
    parse(in, contentHandler, false);
  }
  












  public void parse(Reader in, ContentHandler contentHandler, boolean isResume)
    throws IOException, ParseException
  {
    if (!isResume) {
      reset(in);
      handlerStatusStack = new LinkedList();

    }
    else if (handlerStatusStack == null) {
      isResume = false;
      reset(in);
      handlerStatusStack = new LinkedList();
    }
    

    LinkedList statusStack = handlerStatusStack;
    try
    {
      do {
        switch (status) {
        case 0: 
          contentHandler.startJSON();
          nextToken();
          switch (token.type) {
          case 0: 
            status = 1;
            statusStack.addFirst(new Integer(status));
            if (!contentHandler.primitive(token.value))
              return;
            break;
          case 1: 
            status = 2;
            statusStack.addFirst(new Integer(status));
            if (!contentHandler.startObject())
              return;
            break;
          case 3: 
            status = 3;
            statusStack.addFirst(new Integer(status));
            if (!contentHandler.startArray())
              return;
            break;
          case 2: default: 
            status = -1;
          }
          break;
        
        case 1: 
          nextToken();
          if (token.type == -1) {
            contentHandler.endJSON();
            status = 6;
            return;
          }
          
          status = -1;
          throw new ParseException(getPosition(), 1, token);
        

        case 2: 
          nextToken();
          switch (token.type) {
          case 5: 
            break;
          case 0: 
            if ((token.value instanceof String)) {
              String key = (String)token.value;
              status = 4;
              statusStack.addFirst(new Integer(status));
              if (contentHandler.startObjectEntry(key)) {
                break;
              }
            } else {
              status = -1;
            }
            break;
          case 2: 
            if (statusStack.size() > 1) {
              statusStack.removeFirst();
              status = peekStatus(statusStack);
            }
            else {
              status = 1;
            }
            if (!contentHandler.endObject())
              return;
            break;
          case 1: case 3: case 4: default: 
            status = -1;
          }
          
          break;
        
        case 4: 
          nextToken();
          switch (token.type) {
          case 6: 
            break;
          case 0: 
            statusStack.removeFirst();
            status = peekStatus(statusStack);
            if (!contentHandler.primitive(token.value))
              return;
            if (!contentHandler.endObjectEntry())
              return;
            break;
          case 3: 
            statusStack.removeFirst();
            statusStack.addFirst(new Integer(5));
            status = 3;
            statusStack.addFirst(new Integer(status));
            if (!contentHandler.startArray())
              return;
            break;
          case 1: 
            statusStack.removeFirst();
            statusStack.addFirst(new Integer(5));
            status = 2;
            statusStack.addFirst(new Integer(status));
            if (!contentHandler.startObject())
              return;
            break;
          case 2: case 4: case 5: default: 
            status = -1;
          }
          break;
        




        case 5: 
          statusStack.removeFirst();
          status = peekStatus(statusStack);
          if (!contentHandler.endObjectEntry()) {
            return;
          }
          break;
        case 3: 
          nextToken();
          switch (token.type) {
          case 5: 
            break;
          case 0: 
            if (!contentHandler.primitive(token.value))
              return;
            break;
          case 4: 
            if (statusStack.size() > 1) {
              statusStack.removeFirst();
              status = peekStatus(statusStack);
            }
            else {
              status = 1;
            }
            if (!contentHandler.endArray())
              return;
            break;
          case 1: 
            status = 2;
            statusStack.addFirst(new Integer(status));
            if (!contentHandler.startObject())
              return;
            break;
          case 3: 
            status = 3;
            statusStack.addFirst(new Integer(status));
            if (!contentHandler.startArray())
              return;
            break;
          case 2: default: 
            status = -1;
          }
          break;
        
        case 6: 
          return;
        
        case -1: 
          throw new ParseException(getPosition(), 1, token);
        }
        if (status == -1) {
          throw new ParseException(getPosition(), 1, token);
        }
      } while (token.type != -1);
    }
    catch (IOException ie) {
      status = -1;
      throw ie;
    }
    catch (ParseException pe) {
      status = -1;
      throw pe;
    }
    catch (RuntimeException re) {
      status = -1;
      throw re;
    }
    catch (Error e) {
      status = -1;
      throw e;
    }
    
    status = -1;
    throw new ParseException(getPosition(), 1, token);
  }
}
