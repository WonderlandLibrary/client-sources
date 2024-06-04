package org.spongycastle.jce.provider;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.x509.CRLDistPoint;
import org.spongycastle.asn1.x509.DistributionPoint;
import org.spongycastle.asn1.x509.DistributionPointName;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.TargetInformation;
import org.spongycastle.asn1.x509.X509Extensions;
import org.spongycastle.jcajce.PKIXCRLStore;
import org.spongycastle.jcajce.PKIXCertStoreSelector.Builder;
import org.spongycastle.jcajce.PKIXExtendedBuilderParameters.Builder;
import org.spongycastle.jcajce.PKIXExtendedParameters;
import org.spongycastle.jcajce.PKIXExtendedParameters.Builder;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.jce.exception.ExtCertPathValidatorException;
import org.spongycastle.x509.AttributeCertificateHolder;
import org.spongycastle.x509.PKIXAttrCertChecker;
import org.spongycastle.x509.X509AttributeCertificate;
import org.spongycastle.x509.X509CertStoreSelector;


class RFC3281CertPathUtilities
{
  private static final String TARGET_INFORMATION = Extension.targetInformation
    .getId();
  
  private static final String NO_REV_AVAIL = Extension.noRevAvail
    .getId();
  
  private static final String CRL_DISTRIBUTION_POINTS = Extension.cRLDistributionPoints
    .getId();
  
  private static final String AUTHORITY_INFO_ACCESS = Extension.authorityInfoAccess
    .getId();
  


  RFC3281CertPathUtilities() {}
  

  protected static void processAttrCert7(X509AttributeCertificate attrCert, CertPath certPath, CertPath holderCertPath, PKIXExtendedParameters pkixParams, Set attrCertCheckers)
    throws CertPathValidatorException
  {
    Set set = attrCert.getCriticalExtensionOIDs();
    



    if (set.contains(TARGET_INFORMATION))
    {
      try
      {
        TargetInformation.getInstance(
          CertPathValidatorUtilities.getExtensionValue(attrCert, TARGET_INFORMATION));
      }
      catch (AnnotatedException e)
      {
        throw new ExtCertPathValidatorException("Target information extension could not be read.", e);

      }
      catch (IllegalArgumentException e)
      {
        throw new ExtCertPathValidatorException("Target information extension could not be read.", e);
      }
    }
    
    set.remove(TARGET_INFORMATION);
    Iterator it = attrCertCheckers.iterator();
    while (it.hasNext())
    {
      ((PKIXAttrCertChecker)it.next()).check(attrCert, certPath, holderCertPath, set);
    }
    
    if (!set.isEmpty())
    {
      throw new CertPathValidatorException("Attribute certificate contains unsupported critical extensions: " + set);
    }
  }
  


















  protected static void checkCRLs(X509AttributeCertificate attrCert, PKIXExtendedParameters paramsPKIX, X509Certificate issuerCert, Date validDate, List certPathCerts, JcaJceHelper helper)
    throws CertPathValidatorException
  {
    if (paramsPKIX.isRevocationEnabled())
    {

      if (attrCert.getExtensionValue(NO_REV_AVAIL) == null)
      {
        CRLDistPoint crldp = null;
        try
        {
          crldp = CRLDistPoint.getInstance(
            CertPathValidatorUtilities.getExtensionValue(attrCert, CRL_DISTRIBUTION_POINTS));
        }
        catch (AnnotatedException e)
        {
          throw new CertPathValidatorException("CRL distribution point extension could not be read.", e);
        }
        


        List crlStores = new ArrayList();
        
        try
        {
          crlStores.addAll(CertPathValidatorUtilities.getAdditionalStoresFromCRLDistributionPoint(crldp, paramsPKIX.getNamedCRLStoreMap()));
        }
        catch (AnnotatedException e)
        {
          throw new CertPathValidatorException("No additional CRL locations could be decoded from CRL distribution point extension.", e);
        }
        


        PKIXExtendedParameters.Builder bldr = new PKIXExtendedParameters.Builder(paramsPKIX);
        
        for (Iterator it = crlStores.iterator(); it.hasNext();)
        {
          bldr.addCRLStore((PKIXCRLStore)crlStores);
        }
        
        paramsPKIX = bldr.build();
        
        CertStatus certStatus = new CertStatus();
        ReasonsMask reasonsMask = new ReasonsMask();
        
        AnnotatedException lastException = null;
        boolean validCrlFound = false;
        
        if (crldp != null)
        {
          DistributionPoint[] dps = null;
          try
          {
            dps = crldp.getDistributionPoints();
          }
          catch (Exception e)
          {
            throw new ExtCertPathValidatorException("Distribution points could not be read.", e);
          }
          
          try
          {
            for (int i = 0; 
                
                (i < dps.length) && (certStatus.getCertStatus() == 11) && (!reasonsMask.isAllReasons()); i++)
            {

              PKIXExtendedParameters paramsPKIXClone = (PKIXExtendedParameters)paramsPKIX.clone();
              
              checkCRL(dps[i], attrCert, paramsPKIXClone, validDate, issuerCert, certStatus, reasonsMask, certPathCerts, helper);
              

              validCrlFound = true;
            }
          }
          catch (AnnotatedException e)
          {
            lastException = new AnnotatedException("No valid CRL for distribution point found.", e);
          }
        }
        







        if ((certStatus.getCertStatus() == 11) && 
          (!reasonsMask.isAllReasons()))
        {


          try
          {



            ASN1Primitive issuer = null;
            



            try
            {
              issuer = new ASN1InputStream(((X500Principal)attrCert.getIssuer().getPrincipals()[0]).getEncoded()).readObject();
            }
            catch (Exception e)
            {
              throw new AnnotatedException("Issuer from certificate for CRL could not be reencoded.", e);
            }
            

            DistributionPoint dp = new DistributionPoint(new DistributionPointName(0, new GeneralNames(new GeneralName(4, issuer))), null, null);
            



            PKIXExtendedParameters paramsPKIXClone = (PKIXExtendedParameters)paramsPKIX.clone();
            checkCRL(dp, attrCert, paramsPKIXClone, validDate, issuerCert, certStatus, reasonsMask, certPathCerts, helper);
            
            validCrlFound = true;
          }
          catch (AnnotatedException e)
          {
            lastException = new AnnotatedException("No valid CRL for distribution point found.", e);
          }
        }
        

        if (!validCrlFound)
        {
          throw new ExtCertPathValidatorException("No valid CRL found.", lastException);
        }
        
        if (certStatus.getCertStatus() != 11)
        {

          String message = "Attribute certificate revocation after " + certStatus.getRevocationDate();
          

          message = message + ", reason: " + RFC3280CertPathUtilities.crlReasons[certStatus.getCertStatus()];
          throw new CertPathValidatorException(message);
        }
        if ((!reasonsMask.isAllReasons()) && 
          (certStatus.getCertStatus() == 11))
        {
          certStatus.setCertStatus(12);
        }
        if (certStatus.getCertStatus() == 12)
        {
          throw new CertPathValidatorException("Attribute certificate status could not be determined.");

        }
        


      }
      else if ((attrCert.getExtensionValue(CRL_DISTRIBUTION_POINTS) != null) || 
        (attrCert.getExtensionValue(AUTHORITY_INFO_ACCESS) != null))
      {
        throw new CertPathValidatorException("No rev avail extension is set, but also an AC revocation pointer.");
      }
    }
  }
  



  protected static void additionalChecks(X509AttributeCertificate attrCert, Set prohibitedACAttributes, Set necessaryACAttributes)
    throws CertPathValidatorException
  {
    Iterator it = prohibitedACAttributes.iterator();
    while (it.hasNext())
    {
      String oid = (String)it.next();
      if (attrCert.getAttributes(oid) != null)
      {
        throw new CertPathValidatorException("Attribute certificate contains prohibited attribute: " + oid + ".");
      }
    }
    

    Iterator it = necessaryACAttributes.iterator();
    while (it.hasNext())
    {
      String oid = (String)it.next();
      if (attrCert.getAttributes(oid) == null)
      {
        throw new CertPathValidatorException("Attribute certificate does not contain necessary attribute: " + oid + ".");
      }
    }
  }
  


  protected static void processAttrCert5(X509AttributeCertificate attrCert, PKIXExtendedParameters pkixParams)
    throws CertPathValidatorException
  {
    try
    {
      attrCert.checkValidity(
        CertPathValidatorUtilities.getValidDate(pkixParams));
    }
    catch (CertificateExpiredException e)
    {
      throw new ExtCertPathValidatorException("Attribute certificate is not valid.", e);

    }
    catch (CertificateNotYetValidException e)
    {
      throw new ExtCertPathValidatorException("Attribute certificate is not valid.", e);
    }
  }
  

  protected static void processAttrCert4(X509Certificate acIssuerCert, Set trustedACIssuers)
    throws CertPathValidatorException
  {
    Set set = trustedACIssuers;
    boolean trusted = false;
    for (Iterator it = set.iterator(); it.hasNext();)
    {
      TrustAnchor anchor = (TrustAnchor)it.next();
      
      if ((acIssuerCert.getSubjectX500Principal().getName("RFC2253").equals(anchor.getCAName())) || 
        (acIssuerCert.equals(anchor.getTrustedCert())))
      {
        trusted = true;
      }
    }
    if (!trusted)
    {
      throw new CertPathValidatorException("Attribute certificate issuer is not directly trusted.");
    }
  }
  

  protected static void processAttrCert3(X509Certificate acIssuerCert, PKIXExtendedParameters pkixParams)
    throws CertPathValidatorException
  {
    if ((acIssuerCert.getKeyUsage() != null) && 
      (acIssuerCert.getKeyUsage()[0] == 0) && (acIssuerCert.getKeyUsage()[1] == 0))
    {
      throw new CertPathValidatorException("Attribute certificate issuer public key cannot be used to validate digital signatures.");
    }
    
    if (acIssuerCert.getBasicConstraints() != -1)
    {
      throw new CertPathValidatorException("Attribute certificate issuer is also a public key certificate issuer.");
    }
  }
  


  protected static CertPathValidatorResult processAttrCert2(CertPath certPath, PKIXExtendedParameters pkixParams)
    throws CertPathValidatorException
  {
    CertPathValidator validator = null;
    try
    {
      validator = CertPathValidator.getInstance("PKIX", "SC");
    }
    catch (NoSuchProviderException e)
    {
      throw new ExtCertPathValidatorException("Support class could not be created.", e);

    }
    catch (NoSuchAlgorithmException e)
    {
      throw new ExtCertPathValidatorException("Support class could not be created.", e);
    }
    
    try
    {
      return validator.validate(certPath, pkixParams);
    }
    catch (CertPathValidatorException e)
    {
      throw new ExtCertPathValidatorException("Certification path for issuer certificate of attribute certificate could not be validated.", e);


    }
    catch (InvalidAlgorithmParameterException e)
    {

      throw new RuntimeException(e.getMessage());
    }
  }
  


















  protected static CertPath processAttrCert1(X509AttributeCertificate attrCert, PKIXExtendedParameters pkixParams)
    throws CertPathValidatorException
  {
    CertPathBuilderResult result = null;
    
    Set holderPKCs = new HashSet();
    if (attrCert.getHolder().getIssuer() != null)
    {
      X509CertSelector selector = new X509CertSelector();
      selector.setSerialNumber(attrCert.getHolder().getSerialNumber());
      Principal[] principals = attrCert.getHolder().getIssuer();
      for (int i = 0; i < principals.length; i++)
      {
        try
        {
          if ((principals[i] instanceof X500Principal))
          {
            selector.setIssuer(((X500Principal)principals[i])
              .getEncoded());
          }
          holderPKCs.addAll(
            CertPathValidatorUtilities.findCertificates(new PKIXCertStoreSelector.Builder(selector).build(), pkixParams.getCertStores()));
        }
        catch (AnnotatedException e)
        {
          throw new ExtCertPathValidatorException("Public key certificate for attribute certificate cannot be searched.", e);

        }
        catch (IOException e)
        {

          throw new ExtCertPathValidatorException("Unable to encode X500 principal.", e);
        }
      }
      
      if (holderPKCs.isEmpty())
      {
        throw new CertPathValidatorException("Public key certificate specified in base certificate ID for attribute certificate cannot be found.");
      }
    }
    
    if (attrCert.getHolder().getEntityNames() != null)
    {
      X509CertStoreSelector selector = new X509CertStoreSelector();
      Principal[] principals = attrCert.getHolder().getEntityNames();
      for (int i = 0; i < principals.length; i++)
      {
        try
        {
          if ((principals[i] instanceof X500Principal))
          {
            selector.setIssuer(((X500Principal)principals[i])
              .getEncoded());
          }
          holderPKCs.addAll(
            CertPathValidatorUtilities.findCertificates(new PKIXCertStoreSelector.Builder(selector).build(), pkixParams.getCertStores()));
        }
        catch (AnnotatedException e)
        {
          throw new ExtCertPathValidatorException("Public key certificate for attribute certificate cannot be searched.", e);

        }
        catch (IOException e)
        {

          throw new ExtCertPathValidatorException("Unable to encode X500 principal.", e);
        }
      }
      
      if (holderPKCs.isEmpty())
      {
        throw new CertPathValidatorException("Public key certificate specified in entity name for attribute certificate cannot be found.");
      }
    }
    

    PKIXExtendedParameters.Builder paramsBldr = new PKIXExtendedParameters.Builder(pkixParams);
    
    CertPathValidatorException lastException = null;
    for (Iterator it = holderPKCs.iterator(); it.hasNext();)
    {
      X509CertStoreSelector selector = new X509CertStoreSelector();
      selector.setCertificate((X509Certificate)it.next());
      paramsBldr.setTargetConstraints(new PKIXCertStoreSelector.Builder(selector).build());
      CertPathBuilder builder = null;
      try
      {
        builder = CertPathBuilder.getInstance("PKIX", "SC");
      }
      catch (NoSuchProviderException e)
      {
        throw new ExtCertPathValidatorException("Support class could not be created.", e);

      }
      catch (NoSuchAlgorithmException e)
      {
        throw new ExtCertPathValidatorException("Support class could not be created.", e);
      }
      
      try
      {
        result = builder.build(new PKIXExtendedBuilderParameters.Builder(paramsBldr.build()).build());
      }
      catch (CertPathBuilderException e)
      {
        lastException = new ExtCertPathValidatorException("Certification path for public key certificate of attribute certificate could not be build.", e);


      }
      catch (InvalidAlgorithmParameterException e)
      {

        throw new RuntimeException(e.getMessage());
      }
    }
    if (lastException != null)
    {
      throw lastException;
    }
    return result.getCertPath();
  }
  



























  private static void checkCRL(DistributionPoint dp, X509AttributeCertificate attrCert, PKIXExtendedParameters paramsPKIX, Date validDate, X509Certificate issuerCert, CertStatus certStatus, ReasonsMask reasonMask, List certPathCerts, JcaJceHelper helper)
    throws AnnotatedException
  {
    if (attrCert.getExtensionValue(X509Extensions.NoRevAvail.getId()) != null)
    {
      return;
    }
    Date currentDate = new Date(System.currentTimeMillis());
    if (validDate.getTime() > currentDate.getTime())
    {
      throw new AnnotatedException("Validation time is in future.");
    }
    








    Set crls = CertPathValidatorUtilities.getCompleteCRLs(dp, attrCert, currentDate, paramsPKIX);
    
    boolean validCrlFound = false;
    AnnotatedException lastException = null;
    Iterator crl_iter = crls.iterator();
    
    while ((crl_iter.hasNext()) && 
      (certStatus.getCertStatus() == 11) && 
      (!reasonMask.isAllReasons()))
    {
      try
      {
        X509CRL crl = (X509CRL)crl_iter.next();
        


        ReasonsMask interimReasonsMask = RFC3280CertPathUtilities.processCRLD(crl, dp);
        






        if (interimReasonsMask.hasNewReasons(reasonMask))
        {




          Set keys = RFC3280CertPathUtilities.processCRLF(crl, attrCert, null, null, paramsPKIX, certPathCerts, helper);
          
          PublicKey key = RFC3280CertPathUtilities.processCRLG(crl, keys);
          
          X509CRL deltaCRL = null;
          
          if (paramsPKIX.isUseDeltasEnabled())
          {

            Set deltaCRLs = CertPathValidatorUtilities.getDeltaCRLs(currentDate, crl, paramsPKIX.getCertStores(), paramsPKIX.getCRLStores());
            

            deltaCRL = RFC3280CertPathUtilities.processCRLH(deltaCRLs, key);
          }
          














          if (paramsPKIX.getValidityModel() != 1)
          {






            if (attrCert.getNotAfter().getTime() < crl.getThisUpdate().getTime())
            {
              throw new AnnotatedException("No valid CRL for current time found.");
            }
          }
          

          RFC3280CertPathUtilities.processCRLB1(dp, attrCert, crl);
          

          RFC3280CertPathUtilities.processCRLB2(dp, attrCert, crl);
          

          RFC3280CertPathUtilities.processCRLC(deltaCRL, crl, paramsPKIX);
          

          RFC3280CertPathUtilities.processCRLI(validDate, deltaCRL, attrCert, certStatus, paramsPKIX);
          


          RFC3280CertPathUtilities.processCRLJ(validDate, crl, attrCert, certStatus);
          


          if (certStatus.getCertStatus() == 8)
          {
            certStatus.setCertStatus(11);
          }
          

          reasonMask.addReasons(interimReasonsMask);
          validCrlFound = true;
        }
      }
      catch (AnnotatedException e) {
        lastException = e;
      }
    }
    if (!validCrlFound)
    {
      throw lastException;
    }
  }
}
