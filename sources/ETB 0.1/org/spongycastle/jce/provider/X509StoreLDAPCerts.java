package org.spongycastle.jce.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.spongycastle.jce.X509LDAPCertStoreParameters;
import org.spongycastle.util.Selector;
import org.spongycastle.util.StoreException;
import org.spongycastle.x509.X509CertPairStoreSelector;
import org.spongycastle.x509.X509CertStoreSelector;
import org.spongycastle.x509.X509CertificatePair;
import org.spongycastle.x509.X509StoreParameters;
import org.spongycastle.x509.X509StoreSpi;
import org.spongycastle.x509.util.LDAPStoreHelper;


















public class X509StoreLDAPCerts
  extends X509StoreSpi
{
  private LDAPStoreHelper helper;
  
  public X509StoreLDAPCerts() {}
  
  public void engineInit(X509StoreParameters params)
  {
    if (!(params instanceof X509LDAPCertStoreParameters))
    {


      throw new IllegalArgumentException("Initialization parameters must be an instance of " + X509LDAPCertStoreParameters.class.getName() + ".");
    }
    helper = new LDAPStoreHelper((X509LDAPCertStoreParameters)params);
  }
  
















  public Collection engineGetMatches(Selector selector)
    throws StoreException
  {
    if (!(selector instanceof X509CertStoreSelector))
    {
      return Collections.EMPTY_SET;
    }
    X509CertStoreSelector xselector = (X509CertStoreSelector)selector;
    Set set = new HashSet();
    
    if (xselector.getBasicConstraints() > 0)
    {
      set.addAll(helper.getCACertificates(xselector));
      set.addAll(getCertificatesFromCrossCertificatePairs(xselector));

    }
    else if (xselector.getBasicConstraints() == -2)
    {
      set.addAll(helper.getUserCertificates(xselector));

    }
    else
    {
      set.addAll(helper.getUserCertificates(xselector));
      set.addAll(helper.getCACertificates(xselector));
      set.addAll(getCertificatesFromCrossCertificatePairs(xselector));
    }
    return set;
  }
  
  private Collection getCertificatesFromCrossCertificatePairs(X509CertStoreSelector xselector)
    throws StoreException
  {
    Set set = new HashSet();
    X509CertPairStoreSelector ps = new X509CertPairStoreSelector();
    
    ps.setForwardSelector(xselector);
    ps.setReverseSelector(new X509CertStoreSelector());
    
    Set crossCerts = new HashSet(helper.getCrossCertificatePairs(ps));
    Set forward = new HashSet();
    Set reverse = new HashSet();
    Iterator it = crossCerts.iterator();
    while (it.hasNext())
    {
      X509CertificatePair pair = (X509CertificatePair)it.next();
      if (pair.getForward() != null)
      {
        forward.add(pair.getForward());
      }
      if (pair.getReverse() != null)
      {
        reverse.add(pair.getReverse());
      }
    }
    set.addAll(forward);
    set.addAll(reverse);
    return set;
  }
}
