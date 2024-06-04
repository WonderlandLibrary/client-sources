package org.spongycastle.jce.provider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CRL;
import java.security.cert.CRLSelector;
import java.security.cert.CertSelector;
import java.security.cert.CertStoreException;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertStoreSpi;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
import org.spongycastle.asn1.x509.CertificatePair;
import org.spongycastle.jce.X509LDAPCertStoreParameters;














public class X509LDAPCertStoreSpi
  extends CertStoreSpi
{
  private X509LDAPCertStoreParameters params;
  
  public X509LDAPCertStoreSpi(CertStoreParameters params)
    throws InvalidAlgorithmParameterException
  {
    super(params);
    
    if (!(params instanceof X509LDAPCertStoreParameters))
    {


      throw new InvalidAlgorithmParameterException(X509LDAPCertStoreSpi.class.getName() + ": parameter must be a " + X509LDAPCertStoreParameters.class.getName() + " object\n" + params.toString());
    }
    
    this.params = ((X509LDAPCertStoreParameters)params);
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
  
  private String parseDN(String subject, String subjectAttributeName)
  {
    String temp = subject;
    int begin = temp.toLowerCase().indexOf(subjectAttributeName
      .toLowerCase());
    temp = temp.substring(begin + subjectAttributeName.length());
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
  
  public Collection engineGetCertificates(CertSelector selector)
    throws CertStoreException
  {
    if (!(selector instanceof X509CertSelector))
    {
      throw new CertStoreException("selector is not a X509CertSelector");
    }
    X509CertSelector xselector = (X509CertSelector)selector;
    
    Set certSet = new HashSet();
    
    Set set = getEndCertificates(xselector);
    set.addAll(getCACertificates(xselector));
    set.addAll(getCrossCertificates(xselector));
    
    Iterator it = set.iterator();
    
    try
    {
      CertificateFactory cf = CertificateFactory.getInstance("X.509", "SC");
      Iterator it2;
      while (it.hasNext())
      {
        byte[] bytes = (byte[])it.next();
        if ((bytes != null) && (bytes.length != 0))
        {



          List bytesList = new ArrayList();
          bytesList.add(bytes);
          

          try
          {
            CertificatePair pair = CertificatePair.getInstance(new ASN1InputStream(bytes)
              .readObject());
            bytesList.clear();
            if (pair.getForward() != null)
            {
              bytesList.add(pair.getForward().getEncoded());
            }
            if (pair.getReverse() != null)
            {
              bytesList.add(pair.getReverse().getEncoded());
            }
          }
          catch (IOException localIOException) {}catch (IllegalArgumentException localIllegalArgumentException) {}
          






          for (it2 = bytesList.iterator(); it2.hasNext();)
          {

            ByteArrayInputStream bIn = new ByteArrayInputStream((byte[])it2.next());
            try
            {
              java.security.cert.Certificate cert = cf.generateCertificate(bIn);
              

              if (xselector.match(cert))
              {
                certSet.add(cert);
              }
              

            }
            catch (Exception localException1) {}
          }
        }
      }
    }
    catch (Exception e)
    {
      throw new CertStoreException("certificate cannot be constructed from LDAP result: " + e);
    }
    

    return certSet;
  }
  

  private Set certSubjectSerialSearch(X509CertSelector xselector, String[] attrs, String attrName, String subjectAttributeName)
    throws CertStoreException
  {
    Set set = new HashSet();
    try
    {
      if ((xselector.getSubjectAsBytes() != null) || 
        (xselector.getSubjectAsString() != null) || 
        (xselector.getCertificate() != null))
      {
        String subject = null;
        String serial = null;
        if (xselector.getCertificate() != null)
        {

          subject = xselector.getCertificate().getSubjectX500Principal().getName("RFC1779");
          
          serial = xselector.getCertificate().getSerialNumber().toString();


        }
        else if (xselector.getSubjectAsBytes() != null)
        {

          subject = new X500Principal(xselector.getSubjectAsBytes()).getName("RFC1779");
        }
        else
        {
          subject = xselector.getSubjectAsString();
        }
        
        String attrValue = parseDN(subject, subjectAttributeName);
        set.addAll(search(attrName, "*" + attrValue + "*", attrs));
        if ((serial != null) && 
          (params.getSearchForSerialNumberIn() != null))
        {
          attrValue = serial;
          attrName = params.getSearchForSerialNumberIn();
          set.addAll(search(attrName, "*" + attrValue + "*", attrs));
        }
      }
      else
      {
        set.addAll(search(attrName, "*", attrs));
      }
    }
    catch (IOException e)
    {
      throw new CertStoreException("exception processing selector: " + e);
    }
    
    return set;
  }
  
  private Set getEndCertificates(X509CertSelector xselector)
    throws CertStoreException
  {
    String[] attrs = { params.getUserCertificateAttribute() };
    String attrName = params.getLdapUserCertificateAttributeName();
    String subjectAttributeName = params.getUserCertificateSubjectAttributeName();
    
    Set set = certSubjectSerialSearch(xselector, attrs, attrName, subjectAttributeName);
    
    return set;
  }
  
  private Set getCACertificates(X509CertSelector xselector)
    throws CertStoreException
  {
    String[] attrs = { params.getCACertificateAttribute() };
    String attrName = params.getLdapCACertificateAttributeName();
    
    String subjectAttributeName = params.getCACertificateSubjectAttributeName();
    Set set = certSubjectSerialSearch(xselector, attrs, attrName, subjectAttributeName);
    

    if (set.isEmpty())
    {
      set.addAll(search(null, "*", attrs));
    }
    
    return set;
  }
  
  private Set getCrossCertificates(X509CertSelector xselector)
    throws CertStoreException
  {
    String[] attrs = { params.getCrossCertificateAttribute() };
    String attrName = params.getLdapCrossCertificateAttributeName();
    
    String subjectAttributeName = params.getCrossCertificateSubjectAttributeName();
    Set set = certSubjectSerialSearch(xselector, attrs, attrName, subjectAttributeName);
    

    if (set.isEmpty())
    {
      set.addAll(search(null, "*", attrs));
    }
    
    return set;
  }
  
  public Collection engineGetCRLs(CRLSelector selector)
    throws CertStoreException
  {
    String[] attrs = { params.getCertificateRevocationListAttribute() };
    if (!(selector instanceof X509CRLSelector))
    {
      throw new CertStoreException("selector is not a X509CRLSelector");
    }
    X509CRLSelector xselector = (X509CRLSelector)selector;
    
    Set crlSet = new HashSet();
    
    String attrName = params.getLdapCertificateRevocationListAttributeName();
    Set set = new HashSet();
    
    if (xselector.getIssuerNames() != null)
    {
      Iterator it = xselector.getIssuerNames().iterator();
      while (it.hasNext())
      {
        Object o = it.next();
        String attrValue = null;
        if ((o instanceof String))
        {

          String issuerAttributeName = params.getCertificateRevocationListIssuerAttributeName();
          attrValue = parseDN((String)o, issuerAttributeName);

        }
        else
        {
          String issuerAttributeName = params.getCertificateRevocationListIssuerAttributeName();
          attrValue = parseDN(new X500Principal((byte[])o)
            .getName("RFC1779"), issuerAttributeName);
        }
        set.addAll(search(attrName, "*" + attrValue + "*", attrs));
      }
    }
    else
    {
      set.addAll(search(attrName, "*", attrs));
    }
    set.addAll(search(null, "*", attrs));
    Iterator it = set.iterator();
    
    try
    {
      CertificateFactory cf = CertificateFactory.getInstance("X.509", "SC");
      
      while (it.hasNext())
      {
        CRL crl = cf.generateCRL(new ByteArrayInputStream(
          (byte[])it.next()));
        if (xselector.match(crl))
        {
          crlSet.add(crl);
        }
      }
    }
    catch (Exception e)
    {
      throw new CertStoreException("CRL cannot be constructed from LDAP result " + e);
    }
    

    return crlSet;
  }
  









  private Set search(String attributeName, String attributeValue, String[] attrs)
    throws CertStoreException
  {
    String filter = attributeName + "=" + attributeValue;
    if (attributeName == null)
    {
      filter = null;
    }
    DirContext ctx = null;
    set = new HashSet();
    
    try
    {
      ctx = connectLDAP();
      
      SearchControls constraints = new SearchControls();
      constraints.setSearchScope(2);
      constraints.setCountLimit(0L);
      for (int i = 0; i < attrs.length; i++)
      {
        String[] temp = new String[1];
        temp[0] = attrs[i];
        constraints.setReturningAttributes(temp);
        
        String filter2 = "(&(" + filter + ")(" + temp[0] + "=*))";
        if (filter == null)
        {
          filter2 = "(" + temp[0] + "=*)";
        }
        NamingEnumeration results = ctx.search(params.getBaseDN(), filter2, constraints);
        
        while (results.hasMoreElements())
        {
          SearchResult sr = (SearchResult)results.next();
          



          NamingEnumeration enumeration = ((Attribute)sr.getAttributes().getAll().next()).getAll();
          while (enumeration.hasMore())
          {
            Object o = enumeration.next();
            set.add(o);
          }
        }
      }
      



















      return set;
    }
    catch (Exception e)
    {
      throw new CertStoreException("Error getting results from LDAP directory " + e);

    }
    finally
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
}
