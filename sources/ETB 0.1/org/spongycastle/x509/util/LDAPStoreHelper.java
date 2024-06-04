package org.spongycastle.x509.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.asn1.x509.CertificatePair;
import org.spongycastle.jce.X509LDAPCertStoreParameters;
import org.spongycastle.jce.provider.X509AttrCertParser;
import org.spongycastle.jce.provider.X509CRLParser;
import org.spongycastle.jce.provider.X509CertPairParser;
import org.spongycastle.jce.provider.X509CertParser;
import org.spongycastle.util.StoreException;
import org.spongycastle.x509.AttributeCertificateHolder;
import org.spongycastle.x509.AttributeCertificateIssuer;
import org.spongycastle.x509.X509AttributeCertStoreSelector;
import org.spongycastle.x509.X509AttributeCertificate;
import org.spongycastle.x509.X509CRLStoreSelector;
import org.spongycastle.x509.X509CertPairStoreSelector;
import org.spongycastle.x509.X509CertStoreSelector;
import org.spongycastle.x509.X509CertificatePair;






















public class LDAPStoreHelper
{
  private X509LDAPCertStoreParameters params;
  
  public LDAPStoreHelper(X509LDAPCertStoreParameters params)
  {
    this.params = params;
  }
  



  private static String LDAP_PROVIDER = "com.sun.jndi.ldap.LdapCtxFactory";
  



  private static String REFERRALS_IGNORE = "ignore";
  


  private static final String SEARCH_SECURITY_LEVEL = "none";
  

  private static final String URL_CONTEXT_PREFIX = "com.sun.jndi.url";
  


  private DirContext connectLDAP()
    throws NamingException
  {
    Properties props = new Properties();
    props.setProperty("java.naming.factory.initial", LDAP_PROVIDER);
    props.setProperty("java.naming.batchsize", "0");
    
    props.setProperty("java.naming.provider.url", params.getLdapURL());
    props.setProperty("java.naming.factory.url.pkgs", "com.sun.jndi.url");
    props.setProperty("java.naming.referral", REFERRALS_IGNORE);
    props.setProperty("java.naming.security.authentication", "none");
    

    DirContext ctx = new InitialDirContext(props);
    return ctx;
  }
  
  private String parseDN(String subject, String dNAttributeName)
  {
    String temp = subject;
    int begin = temp.toLowerCase().indexOf(dNAttributeName
      .toLowerCase() + "=");
    if (begin == -1)
    {
      return "";
    }
    temp = temp.substring(begin + dNAttributeName.length());
    int end = temp.indexOf(',');
    if (end == -1)
    {
      end = temp.length();
    }
    while (temp.charAt(end - 1) == '\\')
    {
      end = temp.indexOf(',', end + 1);
      if (end == -1)
      {
        end = temp.length();
      }
    }
    temp = temp.substring(0, end);
    begin = temp.indexOf('=');
    temp = temp.substring(begin + 1);
    if (temp.charAt(0) == ' ')
    {
      temp = temp.substring(1);
    }
    if (temp.startsWith("\""))
    {
      temp = temp.substring(1);
    }
    if (temp.endsWith("\""))
    {
      temp = temp.substring(0, temp.length() - 1);
    }
    return temp;
  }
  
  private Set createCerts(List list, X509CertStoreSelector xselector)
    throws StoreException
  {
    Set certSet = new HashSet();
    
    Iterator it = list.iterator();
    X509CertParser parser = new X509CertParser();
    while (it.hasNext())
    {
      try
      {
        parser.engineInit(new ByteArrayInputStream(
          (byte[])it.next()));
        
        X509Certificate cert = (X509Certificate)parser.engineRead();
        if (xselector.match(cert))
        {
          certSet.add(cert);
        }
      }
      catch (Exception localException) {}
    }
    




    return certSet;
  }
  

















  private List certSubjectSerialSearch(X509CertStoreSelector xselector, String[] attrs, String[] attrNames, String[] subjectAttributeNames)
    throws StoreException
  {
    List list = new ArrayList();
    
    String subject = null;
    String serial = null;
    
    subject = getSubjectAsString(xselector);
    
    if (xselector.getSerialNumber() != null)
    {
      serial = xselector.getSerialNumber().toString();
    }
    if (xselector.getCertificate() != null)
    {
      subject = xselector.getCertificate().getSubjectX500Principal().getName("RFC1779");
      serial = xselector.getCertificate().getSerialNumber().toString();
    }
    
    String attrValue = null;
    if (subject != null)
    {
      for (int i = 0; i < subjectAttributeNames.length; i++)
      {
        attrValue = parseDN(subject, subjectAttributeNames[i]);
        list
          .addAll(search(attrNames, "*" + attrValue + "*", attrs));
      }
    }
    
    if ((serial != null) && (params.getSearchForSerialNumberIn() != null))
    {
      attrValue = serial;
      list.addAll(search(
        splitString(params.getSearchForSerialNumberIn()), attrValue, attrs));
    }
    
    if ((serial == null) && (subject == null))
    {
      list.addAll(search(attrNames, "*", attrs));
    }
    
    return list;
  }
  




















  private List crossCertificatePairSubjectSearch(X509CertPairStoreSelector xselector, String[] attrs, String[] attrNames, String[] subjectAttributeNames)
    throws StoreException
  {
    List list = new ArrayList();
    

    String subject = null;
    
    if (xselector.getForwardSelector() != null)
    {
      subject = getSubjectAsString(xselector.getForwardSelector());
    }
    if (xselector.getCertPair() != null)
    {
      if (xselector.getCertPair().getForward() != null)
      {

        subject = xselector.getCertPair().getForward().getSubjectX500Principal().getName("RFC1779");
      }
    }
    String attrValue = null;
    if (subject != null)
    {
      for (int i = 0; i < subjectAttributeNames.length; i++)
      {
        attrValue = parseDN(subject, subjectAttributeNames[i]);
        list
          .addAll(search(attrNames, "*" + attrValue + "*", attrs));
      }
    }
    
    if (subject == null)
    {
      list.addAll(search(attrNames, "*", attrs));
    }
    
    return list;
  }
  

















  private List attrCertSubjectSerialSearch(X509AttributeCertStoreSelector xselector, String[] attrs, String[] attrNames, String[] subjectAttributeNames)
    throws StoreException
  {
    List list = new ArrayList();
    




    String subject = null;
    String serial = null;
    
    Collection serials = new HashSet();
    Principal[] principals = null;
    if (xselector.getHolder() != null)
    {

      if (xselector.getHolder().getSerialNumber() != null)
      {
        serials.add(xselector.getHolder().getSerialNumber()
          .toString());
      }
      
      if (xselector.getHolder().getEntityNames() != null)
      {
        principals = xselector.getHolder().getEntityNames();
      }
    }
    
    if (xselector.getAttributeCert() != null)
    {
      if (xselector.getAttributeCert().getHolder().getEntityNames() != null)
      {

        principals = xselector.getAttributeCert().getHolder().getEntityNames();
      }
      
      serials.add(xselector.getAttributeCert().getSerialNumber()
        .toString());
    }
    if (principals != null)
    {

      if ((principals[0] instanceof X500Principal))
      {

        subject = ((X500Principal)principals[0]).getName("RFC1779");

      }
      else
      {
        subject = principals[0].getName();
      }
    }
    if (xselector.getSerialNumber() != null)
    {
      serials.add(xselector.getSerialNumber().toString());
    }
    
    String attrValue = null;
    if (subject != null)
    {
      for (int i = 0; i < subjectAttributeNames.length; i++)
      {
        attrValue = parseDN(subject, subjectAttributeNames[i]);
        list
          .addAll(search(attrNames, "*" + attrValue + "*", attrs));
      }
    }
    
    if ((serials.size() > 0) && 
      (params.getSearchForSerialNumberIn() != null))
    {
      Iterator it = serials.iterator();
      while (it.hasNext())
      {
        serial = (String)it.next();
        list.addAll(search(splitString(params.getSearchForSerialNumberIn()), serial, attrs));
      }
    }
    if ((serials.size() == 0) && (subject == null))
    {
      list.addAll(search(attrNames, "*", attrs));
    }
    
    return list;
  }
  














  private List cRLIssuerSearch(X509CRLStoreSelector xselector, String[] attrs, String[] attrNames, String[] issuerAttributeNames)
    throws StoreException
  {
    List list = new ArrayList();
    
    String issuer = null;
    Collection issuers = new HashSet();
    if (xselector.getIssuers() != null)
    {
      issuers.addAll(xselector.getIssuers());
    }
    if (xselector.getCertificateChecking() != null)
    {
      issuers.add(getCertificateIssuer(xselector.getCertificateChecking()));
    }
    if (xselector.getAttrCertificateChecking() != null)
    {
      Principal[] principals = xselector.getAttrCertificateChecking().getIssuer().getPrincipals();
      for (int i = 0; i < principals.length; i++)
      {
        if ((principals[i] instanceof X500Principal))
        {
          issuers.add(principals[i]);
        }
      }
    }
    Iterator it = issuers.iterator();
    while (it.hasNext())
    {
      issuer = ((X500Principal)it.next()).getName("RFC1779");
      String attrValue = null;
      
      for (int i = 0; i < issuerAttributeNames.length; i++)
      {
        attrValue = parseDN(issuer, issuerAttributeNames[i]);
        list
          .addAll(search(attrNames, "*" + attrValue + "*", attrs));
      }
    }
    
    if (issuer == null)
    {
      list.addAll(search(attrNames, "*", attrs));
    }
    
    return list;
  }
  













  private List search(String[] attributeNames, String attributeValue, String[] attrs)
    throws StoreException
  {
    String filter = null;
    if (attributeNames == null)
    {
      filter = null;
    }
    else
    {
      filter = "";
      if (attributeValue.equals("**"))
      {
        attributeValue = "*";
      }
      for (int i = 0; i < attributeNames.length; i++)
      {
        filter = filter + "(" + attributeNames[i] + "=" + attributeValue + ")";
      }
      filter = "(|" + filter + ")";
    }
    String filter2 = "";
    for (int i = 0; i < attrs.length; i++)
    {
      filter2 = filter2 + "(" + attrs[i] + "=*)";
    }
    filter2 = "(|" + filter2 + ")";
    
    String filter3 = "(&" + filter + "" + filter2 + ")";
    if (filter == null)
    {
      filter3 = filter2;
    }
    
    list = getFromCache(filter3);
    if (list != null)
    {
      return list;
    }
    DirContext ctx = null;
    list = new ArrayList();
    
    try
    {
      ctx = connectLDAP();
      
      SearchControls constraints = new SearchControls();
      constraints.setSearchScope(2);
      constraints.setCountLimit(0L);
      constraints.setReturningAttributes(attrs);
      NamingEnumeration results = ctx.search(params.getBaseDN(), filter3, constraints);
      
      while (results.hasMoreElements())
      {
        SearchResult sr = (SearchResult)results.next();
        
        NamingEnumeration enumeration = ((Attribute)sr.getAttributes().getAll().next()).getAll();
        while (enumeration.hasMore())
        {
          list.add(enumeration.next());
        }
      }
      addToCache(filter3, list);
      



















      return list;
    }
    catch (NamingException localNamingException) {}finally
    {
      try
      {
        if (null != ctx)
        {
          ctx.close();
        }
      }
      catch (Exception localException2) {}
    }
  }
  



  private Set createCRLs(List list, X509CRLStoreSelector xselector)
    throws StoreException
  {
    Set crlSet = new HashSet();
    
    X509CRLParser parser = new X509CRLParser();
    Iterator it = list.iterator();
    while (it.hasNext())
    {
      try
      {
        parser.engineInit(new ByteArrayInputStream(
          (byte[])it.next()));
        X509CRL crl = (X509CRL)parser.engineRead();
        if (xselector.match(crl))
        {
          crlSet.add(crl);
        }
      }
      catch (StreamParsingException localStreamParsingException) {}
    }
    



    return crlSet;
  }
  
  private Set createCrossCertificatePairs(List list, X509CertPairStoreSelector xselector)
    throws StoreException
  {
    Set certPairSet = new HashSet();
    
    int i = 0;
    while (i < list.size())
    {
      try
      {
        X509CertificatePair pair;
        
        try
        {
          X509CertPairParser parser = new X509CertPairParser();
          parser.engineInit(new ByteArrayInputStream(
            (byte[])list.get(i)));
          pair = (X509CertificatePair)parser.engineRead();
        }
        catch (StreamParsingException e)
        {
          X509CertificatePair pair;
          
          byte[] forward = (byte[])list.get(i);
          byte[] reverse = (byte[])list.get(i + 1);
          




          pair = new X509CertificatePair(new CertificatePair(Certificate.getInstance(new ASN1InputStream(forward).readObject()), Certificate.getInstance(new ASN1InputStream(reverse)
            .readObject())));
          i++;
        }
        if (xselector.match(pair))
        {
          certPairSet.add(pair);
        }
      }
      catch (CertificateParsingException localCertificateParsingException) {}catch (IOException localIOException) {}
      






      i++;
    }
    
    return certPairSet;
  }
  
  private Set createAttributeCertificates(List list, X509AttributeCertStoreSelector xselector)
    throws StoreException
  {
    Set certSet = new HashSet();
    
    Iterator it = list.iterator();
    X509AttrCertParser parser = new X509AttrCertParser();
    while (it.hasNext())
    {
      try
      {
        parser.engineInit(new ByteArrayInputStream(
          (byte[])it.next()));
        
        X509AttributeCertificate cert = (X509AttributeCertificate)parser.engineRead();
        if (xselector.match(cert))
        {
          certSet.add(cert);
        }
      }
      catch (StreamParsingException localStreamParsingException) {}
    }
    



    return certSet;
  }
  










  public Collection getAuthorityRevocationLists(X509CRLStoreSelector selector)
    throws StoreException
  {
    String[] attrs = splitString(params.getAuthorityRevocationListAttribute());
    String[] attrNames = splitString(params
      .getLdapAuthorityRevocationListAttributeName());
    String[] issuerAttributeNames = splitString(params
      .getAuthorityRevocationListIssuerAttributeName());
    
    List list = cRLIssuerSearch(selector, attrs, attrNames, issuerAttributeNames);
    
    Set resultSet = createCRLs(list, selector);
    if (resultSet.size() == 0)
    {
      X509CRLStoreSelector emptySelector = new X509CRLStoreSelector();
      list = cRLIssuerSearch(emptySelector, attrs, attrNames, issuerAttributeNames);
      

      resultSet.addAll(createCRLs(list, selector));
    }
    return resultSet;
  }
  










  public Collection getAttributeCertificateRevocationLists(X509CRLStoreSelector selector)
    throws StoreException
  {
    String[] attrs = splitString(params
      .getAttributeCertificateRevocationListAttribute());
    String[] attrNames = splitString(params
      .getLdapAttributeCertificateRevocationListAttributeName());
    String[] issuerAttributeNames = splitString(params
      .getAttributeCertificateRevocationListIssuerAttributeName());
    
    List list = cRLIssuerSearch(selector, attrs, attrNames, issuerAttributeNames);
    
    Set resultSet = createCRLs(list, selector);
    if (resultSet.size() == 0)
    {
      X509CRLStoreSelector emptySelector = new X509CRLStoreSelector();
      list = cRLIssuerSearch(emptySelector, attrs, attrNames, issuerAttributeNames);
      

      resultSet.addAll(createCRLs(list, selector));
    }
    return resultSet;
  }
  











  public Collection getAttributeAuthorityRevocationLists(X509CRLStoreSelector selector)
    throws StoreException
  {
    String[] attrs = splitString(params.getAttributeAuthorityRevocationListAttribute());
    String[] attrNames = splitString(params
      .getLdapAttributeAuthorityRevocationListAttributeName());
    String[] issuerAttributeNames = splitString(params
      .getAttributeAuthorityRevocationListIssuerAttributeName());
    
    List list = cRLIssuerSearch(selector, attrs, attrNames, issuerAttributeNames);
    
    Set resultSet = createCRLs(list, selector);
    if (resultSet.size() == 0)
    {
      X509CRLStoreSelector emptySelector = new X509CRLStoreSelector();
      list = cRLIssuerSearch(emptySelector, attrs, attrNames, issuerAttributeNames);
      

      resultSet.addAll(createCRLs(list, selector));
    }
    return resultSet;
  }
  







  public Collection getCrossCertificatePairs(X509CertPairStoreSelector selector)
    throws StoreException
  {
    String[] attrs = splitString(params.getCrossCertificateAttribute());
    String[] attrNames = splitString(params.getLdapCrossCertificateAttributeName());
    String[] subjectAttributeNames = splitString(params
      .getCrossCertificateSubjectAttributeName());
    List list = crossCertificatePairSubjectSearch(selector, attrs, attrNames, subjectAttributeNames);
    
    Set resultSet = createCrossCertificatePairs(list, selector);
    if (resultSet.size() == 0)
    {
      X509CertStoreSelector emptyCertselector = new X509CertStoreSelector();
      X509CertPairStoreSelector emptySelector = new X509CertPairStoreSelector();
      
      emptySelector.setForwardSelector(emptyCertselector);
      emptySelector.setReverseSelector(emptyCertselector);
      list = crossCertificatePairSubjectSearch(emptySelector, attrs, attrNames, subjectAttributeNames);
      
      resultSet.addAll(createCrossCertificatePairs(list, selector));
    }
    return resultSet;
  }
  











  public Collection getUserCertificates(X509CertStoreSelector selector)
    throws StoreException
  {
    String[] attrs = splitString(params.getUserCertificateAttribute());
    String[] attrNames = splitString(params.getLdapUserCertificateAttributeName());
    String[] subjectAttributeNames = splitString(params
      .getUserCertificateSubjectAttributeName());
    
    List list = certSubjectSerialSearch(selector, attrs, attrNames, subjectAttributeNames);
    
    Set resultSet = createCerts(list, selector);
    if (resultSet.size() == 0)
    {
      X509CertStoreSelector emptySelector = new X509CertStoreSelector();
      list = certSubjectSerialSearch(emptySelector, attrs, attrNames, subjectAttributeNames);
      
      resultSet.addAll(createCerts(list, selector));
    }
    
    return resultSet;
  }
  









  public Collection getAACertificates(X509AttributeCertStoreSelector selector)
    throws StoreException
  {
    String[] attrs = splitString(params.getAACertificateAttribute());
    String[] attrNames = splitString(params.getLdapAACertificateAttributeName());
    String[] subjectAttributeNames = splitString(params.getAACertificateSubjectAttributeName());
    
    List list = attrCertSubjectSerialSearch(selector, attrs, attrNames, subjectAttributeNames);
    
    Set resultSet = createAttributeCertificates(list, selector);
    if (resultSet.size() == 0)
    {
      X509AttributeCertStoreSelector emptySelector = new X509AttributeCertStoreSelector();
      list = attrCertSubjectSerialSearch(emptySelector, attrs, attrNames, subjectAttributeNames);
      
      resultSet.addAll(createAttributeCertificates(list, selector));
    }
    
    return resultSet;
  }
  











  public Collection getAttributeDescriptorCertificates(X509AttributeCertStoreSelector selector)
    throws StoreException
  {
    String[] attrs = splitString(params.getAttributeDescriptorCertificateAttribute());
    String[] attrNames = splitString(params
      .getLdapAttributeDescriptorCertificateAttributeName());
    String[] subjectAttributeNames = splitString(params
      .getAttributeDescriptorCertificateSubjectAttributeName());
    
    List list = attrCertSubjectSerialSearch(selector, attrs, attrNames, subjectAttributeNames);
    
    Set resultSet = createAttributeCertificates(list, selector);
    if (resultSet.size() == 0)
    {
      X509AttributeCertStoreSelector emptySelector = new X509AttributeCertStoreSelector();
      list = attrCertSubjectSerialSearch(emptySelector, attrs, attrNames, subjectAttributeNames);
      
      resultSet.addAll(createAttributeCertificates(list, selector));
    }
    
    return resultSet;
  }
  











  public Collection getCACertificates(X509CertStoreSelector selector)
    throws StoreException
  {
    String[] attrs = splitString(params.getCACertificateAttribute());
    String[] attrNames = splitString(params.getLdapCACertificateAttributeName());
    String[] subjectAttributeNames = splitString(params
      .getCACertificateSubjectAttributeName());
    List list = certSubjectSerialSearch(selector, attrs, attrNames, subjectAttributeNames);
    
    Set resultSet = createCerts(list, selector);
    if (resultSet.size() == 0)
    {
      X509CertStoreSelector emptySelector = new X509CertStoreSelector();
      list = certSubjectSerialSearch(emptySelector, attrs, attrNames, subjectAttributeNames);
      
      resultSet.addAll(createCerts(list, selector));
    }
    return resultSet;
  }
  







  public Collection getDeltaCertificateRevocationLists(X509CRLStoreSelector selector)
    throws StoreException
  {
    String[] attrs = splitString(params.getDeltaRevocationListAttribute());
    String[] attrNames = splitString(params.getLdapDeltaRevocationListAttributeName());
    String[] issuerAttributeNames = splitString(params
      .getDeltaRevocationListIssuerAttributeName());
    List list = cRLIssuerSearch(selector, attrs, attrNames, issuerAttributeNames);
    
    Set resultSet = createCRLs(list, selector);
    if (resultSet.size() == 0)
    {
      X509CRLStoreSelector emptySelector = new X509CRLStoreSelector();
      list = cRLIssuerSearch(emptySelector, attrs, attrNames, issuerAttributeNames);
      

      resultSet.addAll(createCRLs(list, selector));
    }
    return resultSet;
  }
  









  public Collection getAttributeCertificateAttributes(X509AttributeCertStoreSelector selector)
    throws StoreException
  {
    String[] attrs = splitString(params.getAttributeCertificateAttributeAttribute());
    String[] attrNames = splitString(params
      .getLdapAttributeCertificateAttributeAttributeName());
    String[] subjectAttributeNames = splitString(params
      .getAttributeCertificateAttributeSubjectAttributeName());
    List list = attrCertSubjectSerialSearch(selector, attrs, attrNames, subjectAttributeNames);
    
    Set resultSet = createAttributeCertificates(list, selector);
    if (resultSet.size() == 0)
    {
      X509AttributeCertStoreSelector emptySelector = new X509AttributeCertStoreSelector();
      list = attrCertSubjectSerialSearch(emptySelector, attrs, attrNames, subjectAttributeNames);
      
      resultSet.addAll(createAttributeCertificates(list, selector));
    }
    
    return resultSet;
  }
  







  public Collection getCertificateRevocationLists(X509CRLStoreSelector selector)
    throws StoreException
  {
    String[] attrs = splitString(params.getCertificateRevocationListAttribute());
    String[] attrNames = splitString(params
      .getLdapCertificateRevocationListAttributeName());
    String[] issuerAttributeNames = splitString(params
      .getCertificateRevocationListIssuerAttributeName());
    List list = cRLIssuerSearch(selector, attrs, attrNames, issuerAttributeNames);
    
    Set resultSet = createCRLs(list, selector);
    if (resultSet.size() == 0)
    {
      X509CRLStoreSelector emptySelector = new X509CRLStoreSelector();
      list = cRLIssuerSearch(emptySelector, attrs, attrNames, issuerAttributeNames);
      

      resultSet.addAll(createCRLs(list, selector));
    }
    return resultSet;
  }
  
  private Map cacheMap = new HashMap(cacheSize);
  
  private static int cacheSize = 32;
  
  private static long lifeTime = 60000L;
  
  private synchronized void addToCache(String searchCriteria, List list)
  {
    Date now = new Date(System.currentTimeMillis());
    List cacheEntry = new ArrayList();
    cacheEntry.add(now);
    cacheEntry.add(list);
    if (cacheMap.containsKey(searchCriteria))
    {
      cacheMap.put(searchCriteria, cacheEntry);
    }
    else
    {
      if (cacheMap.size() >= cacheSize)
      {

        Iterator it = cacheMap.entrySet().iterator();
        long oldest = now.getTime();
        Object replace = null;
        while (it.hasNext())
        {
          Map.Entry entry = (Map.Entry)it.next();
          
          long current = ((Date)((List)entry.getValue()).get(0)).getTime();
          if (current < oldest)
          {
            oldest = current;
            replace = entry.getKey();
          }
        }
        cacheMap.remove(replace);
      }
      cacheMap.put(searchCriteria, cacheEntry);
    }
  }
  
  private List getFromCache(String searchCriteria)
  {
    List entry = (List)cacheMap.get(searchCriteria);
    long now = System.currentTimeMillis();
    if (entry != null)
    {

      if (((Date)entry.get(0)).getTime() < now - lifeTime)
      {
        return null;
      }
      return (List)entry.get(1);
    }
    return null;
  }
  



  private String[] splitString(String str)
  {
    return str.split("\\s+");
  }
  
  private String getSubjectAsString(X509CertStoreSelector xselector)
  {
    try
    {
      byte[] encSubject = xselector.getSubjectAsBytes();
      if (encSubject != null)
      {
        return new X500Principal(encSubject).getName("RFC1779");
      }
    }
    catch (IOException e)
    {
      throw new StoreException("exception processing name: " + e.getMessage(), e);
    }
    return null;
  }
  
  private X500Principal getCertificateIssuer(X509Certificate cert)
  {
    return cert.getIssuerX500Principal();
  }
}
