package org.spongycastle.i18n;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TimeZone;
import org.spongycastle.i18n.filter.Filter;
import org.spongycastle.i18n.filter.TrustedInput;
import org.spongycastle.i18n.filter.UntrustedInput;
import org.spongycastle.i18n.filter.UntrustedUrlInput;





public class LocalizedMessage
{
  protected final String id;
  protected final String resource;
  public static final String DEFAULT_ENCODING = "ISO-8859-1";
  protected String encoding = "ISO-8859-1";
  
  protected FilteredArguments arguments;
  protected FilteredArguments extraArgs = null;
  
  protected Filter filter = null;
  
  protected ClassLoader loader = null;
  






  public LocalizedMessage(String resource, String id)
    throws NullPointerException
  {
    if ((resource == null) || (id == null))
    {
      throw new NullPointerException();
    }
    this.id = id;
    this.resource = resource;
    arguments = new FilteredArguments();
  }
  








  public LocalizedMessage(String resource, String id, String encoding)
    throws NullPointerException, UnsupportedEncodingException
  {
    if ((resource == null) || (id == null))
    {
      throw new NullPointerException();
    }
    this.id = id;
    this.resource = resource;
    arguments = new FilteredArguments();
    if (!Charset.isSupported(encoding))
    {
      throw new UnsupportedEncodingException("The encoding \"" + encoding + "\" is not supported.");
    }
    this.encoding = encoding;
  }
  







  public LocalizedMessage(String resource, String id, Object[] arguments)
    throws NullPointerException
  {
    if ((resource == null) || (id == null) || (arguments == null))
    {
      throw new NullPointerException();
    }
    this.id = id;
    this.resource = resource;
    this.arguments = new FilteredArguments(arguments);
  }
  









  public LocalizedMessage(String resource, String id, String encoding, Object[] arguments)
    throws NullPointerException, UnsupportedEncodingException
  {
    if ((resource == null) || (id == null) || (arguments == null))
    {
      throw new NullPointerException();
    }
    this.id = id;
    this.resource = resource;
    this.arguments = new FilteredArguments(arguments);
    if (!Charset.isSupported(encoding))
    {
      throw new UnsupportedEncodingException("The encoding \"" + encoding + "\" is not supported.");
    }
    this.encoding = encoding;
  }
  








  public String getEntry(String key, Locale loc, TimeZone timezone)
    throws MissingEntryException
  {
    String entry = id;
    if (key != null)
    {
      entry = entry + "." + key;
    }
    try
    {
      ResourceBundle bundle;
      ResourceBundle bundle;
      if (loader == null)
      {
        bundle = ResourceBundle.getBundle(resource, loc);
      }
      else
      {
        bundle = ResourceBundle.getBundle(resource, loc, loader);
      }
      String result = bundle.getString(entry);
      if (!encoding.equals("ISO-8859-1"))
      {
        result = new String(result.getBytes("ISO-8859-1"), encoding);
      }
      if (!arguments.isEmpty())
      {
        result = formatWithTimeZone(result, arguments.getFilteredArgs(loc), loc, timezone);
      }
      return addExtraArgs(result, loc);



    }
    catch (MissingResourceException mre)
    {


      throw new MissingEntryException("Can't find entry " + entry + " in resource file " + resource + ".", resource, entry, loc, loader != null ? loader : getClassLoader());

    }
    catch (UnsupportedEncodingException use)
    {
      throw new RuntimeException(use);
    }
  }
  




  protected String formatWithTimeZone(String template, Object[] arguments, Locale locale, TimeZone timezone)
  {
    MessageFormat mf = new MessageFormat(" ");
    mf.setLocale(locale);
    mf.applyPattern(template);
    if (!timezone.equals(TimeZone.getDefault()))
    {
      Format[] formats = mf.getFormats();
      for (int i = 0; i < formats.length; i++)
      {
        if ((formats[i] instanceof DateFormat))
        {
          DateFormat temp = (DateFormat)formats[i];
          temp.setTimeZone(timezone);
          mf.setFormat(i, temp);
        }
      }
    }
    return mf.format(arguments);
  }
  
  protected String addExtraArgs(String msg, Locale locale)
  {
    if (extraArgs != null)
    {
      StringBuffer sb = new StringBuffer(msg);
      Object[] filteredArgs = extraArgs.getFilteredArgs(locale);
      for (int i = 0; i < filteredArgs.length; i++)
      {
        sb.append(filteredArgs[i]);
      }
      msg = sb.toString();
    }
    return msg;
  }
  




  public void setFilter(Filter filter)
  {
    arguments.setFilter(filter);
    if (extraArgs != null)
    {
      extraArgs.setFilter(filter);
    }
    this.filter = filter;
  }
  




  public Filter getFilter()
  {
    return filter;
  }
  





  public void setClassLoader(ClassLoader loader)
  {
    this.loader = loader;
  }
  





  public ClassLoader getClassLoader()
  {
    return loader;
  }
  




  public String getId()
  {
    return id;
  }
  




  public String getResource()
  {
    return resource;
  }
  




  public Object[] getArguments()
  {
    return arguments.getArguments();
  }
  




  public void setExtraArgument(Object extraArg)
  {
    setExtraArguments(new Object[] { extraArg });
  }
  




  public void setExtraArguments(Object[] extraArgs)
  {
    if (extraArgs != null)
    {
      this.extraArgs = new FilteredArguments(extraArgs);
      this.extraArgs.setFilter(filter);
    }
    else
    {
      this.extraArgs = null;
    }
  }
  




  public Object[] getExtraArgs()
  {
    return extraArgs == null ? null : extraArgs.getArguments();
  }
  

  protected class FilteredArguments
  {
    protected static final int NO_FILTER = 0;
    protected static final int FILTER = 1;
    protected static final int FILTER_URL = 2;
    protected Filter filter = null;
    
    protected boolean[] isLocaleSpecific;
    protected int[] argFilterType;
    protected Object[] arguments;
    protected Object[] unpackedArgs;
    protected Object[] filteredArgs;
    
    FilteredArguments()
    {
      this(new Object[0]);
    }
    
    FilteredArguments(Object[] args)
    {
      arguments = args;
      unpackedArgs = new Object[args.length];
      filteredArgs = new Object[args.length];
      isLocaleSpecific = new boolean[args.length];
      argFilterType = new int[args.length];
      for (int i = 0; i < args.length; i++)
      {
        if ((args[i] instanceof TrustedInput))
        {
          unpackedArgs[i] = ((TrustedInput)args[i]).getInput();
          argFilterType[i] = 0;
        }
        else if ((args[i] instanceof UntrustedInput))
        {
          unpackedArgs[i] = ((UntrustedInput)args[i]).getInput();
          if ((args[i] instanceof UntrustedUrlInput))
          {
            argFilterType[i] = 2;
          }
          else
          {
            argFilterType[i] = 1;
          }
        }
        else
        {
          unpackedArgs[i] = args[i];
          argFilterType[i] = 1;
        }
        

        isLocaleSpecific[i] = (unpackedArgs[i] instanceof LocaleString);
      }
    }
    
    public boolean isEmpty()
    {
      return unpackedArgs.length == 0;
    }
    
    public Object[] getArguments()
    {
      return arguments;
    }
    
    public Object[] getFilteredArgs(Locale locale)
    {
      Object[] result = new Object[unpackedArgs.length];
      for (int i = 0; i < unpackedArgs.length; i++) {
        Object arg;
        Object arg;
        if (filteredArgs[i] != null)
        {
          arg = filteredArgs[i];
        }
        else
        {
          arg = unpackedArgs[i];
          if (isLocaleSpecific[i] != 0)
          {

            arg = ((LocaleString)arg).getLocaleString(locale);
            arg = filter(argFilterType[i], arg);
          }
          else
          {
            arg = filter(argFilterType[i], arg);
            filteredArgs[i] = arg;
          }
        }
        result[i] = arg;
      }
      return result;
    }
    
    private Object filter(int type, Object obj)
    {
      if (filter != null)
      {
        Object o = null == obj ? "null" : obj;
        switch (type)
        {
        case 0: 
          return o;
        case 1: 
          return filter.doFilter(o.toString());
        case 2: 
          return filter.doFilterUrl(o.toString());
        }
        return null;
      }
      


      return obj;
    }
    

    public Filter getFilter()
    {
      return filter;
    }
    
    public void setFilter(Filter filter)
    {
      if (filter != this.filter)
      {
        for (int i = 0; i < unpackedArgs.length; i++)
        {
          filteredArgs[i] = null;
        }
      }
      this.filter = filter;
    }
  }
  

  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("Resource: \"").append(resource);
    sb.append("\" Id: \"").append(id).append("\"");
    sb.append(" Arguments: ").append(arguments.getArguments().length).append(" normal");
    if ((extraArgs != null) && (extraArgs.getArguments().length > 0))
    {
      sb.append(", ").append(extraArgs.getArguments().length).append(" extra");
    }
    sb.append(" Encoding: ").append(encoding);
    sb.append(" ClassLoader: ").append(loader);
    return sb.toString();
  }
}
