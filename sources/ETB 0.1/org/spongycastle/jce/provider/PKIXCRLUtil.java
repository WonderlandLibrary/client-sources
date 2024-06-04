package org.spongycastle.jce.provider;

import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.spongycastle.jcajce.PKIXCRLStoreSelector;
import org.spongycastle.util.Store;
import org.spongycastle.util.StoreException;

class PKIXCRLUtil
{
  PKIXCRLUtil() {}
  
  public Set findCRLs(PKIXCRLStoreSelector crlselect, Date validityDate, List certStores, List pkixCrlStores)
    throws AnnotatedException
  {
    Set initialSet = new HashSet();
    

    try
    {
      initialSet.addAll(findCRLs(crlselect, pkixCrlStores));
      initialSet.addAll(findCRLs(crlselect, certStores));
    }
    catch (AnnotatedException e)
    {
      throw new AnnotatedException("Exception obtaining complete CRLs.", e);
    }
    
    Set finalSet = new HashSet();
    

    for (Iterator it = initialSet.iterator(); it.hasNext();)
    {
      X509CRL crl = (X509CRL)it.next();
      
      if (crl.getNextUpdate().after(validityDate))
      {
        X509Certificate cert = crlselect.getCertificateChecking();
        
        if (cert != null)
        {
          if (crl.getThisUpdate().before(cert.getNotAfter()))
          {
            finalSet.add(crl);
          }
          
        }
        else {
          finalSet.add(crl);
        }
      }
    }
    
    return finalSet;
  }
  













  private final Collection findCRLs(PKIXCRLStoreSelector crlSelect, List crlStores)
    throws AnnotatedException
  {
    Set crls = new HashSet();
    Iterator iter = crlStores.iterator();
    
    AnnotatedException lastException = null;
    boolean foundValidStore = false;
    
    while (iter.hasNext())
    {
      Object obj = iter.next();
      
      if ((obj instanceof Store))
      {
        Store store = (Store)obj;
        
        try
        {
          crls.addAll(store.getMatches(crlSelect));
          foundValidStore = true;
        }
        catch (StoreException e)
        {
          lastException = new AnnotatedException("Exception searching in X.509 CRL store.", e);
        }
        
      }
      else
      {
        CertStore store = (CertStore)obj;
        
        try
        {
          crls.addAll(PKIXCRLStoreSelector.getCRLs(crlSelect, store));
          foundValidStore = true;
        }
        catch (CertStoreException e)
        {
          lastException = new AnnotatedException("Exception searching in X.509 CRL store.", e);
        }
      }
    }
    
    if ((!foundValidStore) && (lastException != null))
    {
      throw lastException;
    }
    return crls;
  }
}
