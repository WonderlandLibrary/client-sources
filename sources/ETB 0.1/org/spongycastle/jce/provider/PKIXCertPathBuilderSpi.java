package org.spongycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathBuilderSpi;
import java.security.cert.CertPathParameters;
import java.security.cert.CertificateParsingException;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.jcajce.PKIXCertStore;
import org.spongycastle.jcajce.PKIXCertStoreSelector;
import org.spongycastle.jcajce.PKIXExtendedBuilderParameters;
import org.spongycastle.jcajce.PKIXExtendedBuilderParameters.Builder;
import org.spongycastle.jcajce.PKIXExtendedParameters;
import org.spongycastle.jcajce.PKIXExtendedParameters.Builder;
import org.spongycastle.jcajce.provider.asymmetric.x509.CertificateFactory;
import org.spongycastle.jce.exception.ExtCertPathBuilderException;
import org.spongycastle.x509.ExtendedPKIXBuilderParameters;
import org.spongycastle.x509.ExtendedPKIXParameters;





public class PKIXCertPathBuilderSpi
  extends CertPathBuilderSpi
{
  private Exception certPathException;
  
  public PKIXCertPathBuilderSpi() {}
  
  public CertPathBuilderResult engineBuild(CertPathParameters params)
    throws CertPathBuilderException, InvalidAlgorithmParameterException
  {
    PKIXExtendedBuilderParameters paramsPKIX;
    if ((params instanceof PKIXBuilderParameters))
    {
      PKIXExtendedParameters.Builder paramsPKIXBldr = new PKIXExtendedParameters.Builder((PKIXBuilderParameters)params);
      
      PKIXExtendedBuilderParameters.Builder paramsBldrPKIXBldr;
      if ((params instanceof ExtendedPKIXParameters))
      {
        ExtendedPKIXBuilderParameters extPKIX = (ExtendedPKIXBuilderParameters)params;
        

        for (Iterator it = extPKIX.getAdditionalStores().iterator(); it.hasNext();)
        {
          paramsPKIXBldr.addCertificateStore((PKIXCertStore)it.next());
        }
        PKIXExtendedBuilderParameters.Builder paramsBldrPKIXBldr = new PKIXExtendedBuilderParameters.Builder(paramsPKIXBldr.build());
        
        paramsBldrPKIXBldr.addExcludedCerts(extPKIX.getExcludedCerts());
        paramsBldrPKIXBldr.setMaxPathLength(extPKIX.getMaxPathLength());
      }
      else
      {
        paramsBldrPKIXBldr = new PKIXExtendedBuilderParameters.Builder((PKIXBuilderParameters)params);
      }
      
      paramsPKIX = paramsBldrPKIXBldr.build();
    } else { PKIXExtendedBuilderParameters paramsPKIX;
      if ((params instanceof PKIXExtendedBuilderParameters))
      {
        paramsPKIX = (PKIXExtendedBuilderParameters)params;


      }
      else
      {

        throw new InvalidAlgorithmParameterException("Parameters must be an instance of " + PKIXBuilderParameters.class.getName() + " or " + PKIXExtendedBuilderParameters.class.getName() + ".");
      }
    }
    
    PKIXExtendedBuilderParameters paramsPKIX;
    List certPathList = new ArrayList();
    



    PKIXCertStoreSelector certSelect = paramsPKIX.getBaseParameters().getTargetConstraints();
    
    try
    {
      Collection targets = CertPathValidatorUtilities.findCertificates(certSelect, paramsPKIX.getBaseParameters().getCertificateStores());
      targets.addAll(CertPathValidatorUtilities.findCertificates(certSelect, paramsPKIX.getBaseParameters().getCertStores()));
    }
    catch (AnnotatedException e)
    {
      throw new ExtCertPathBuilderException("Error finding target certificate.", e);
    }
    
    Collection targets;
    if (targets.isEmpty())
    {

      throw new CertPathBuilderException("No certificate found matching targetContraints.");
    }
    

    CertPathBuilderResult result = null;
    

    Iterator targetIter = targets.iterator();
    while ((targetIter.hasNext()) && (result == null))
    {
      X509Certificate cert = (X509Certificate)targetIter.next();
      result = build(cert, paramsPKIX, certPathList);
    }
    
    if ((result == null) && (certPathException != null))
    {
      if ((certPathException instanceof AnnotatedException))
      {
        throw new CertPathBuilderException(certPathException.getMessage(), certPathException.getCause());
      }
      throw new CertPathBuilderException("Possible certificate chain could not be validated.", certPathException);
    }
    


    if ((result == null) && (certPathException == null))
    {
      throw new CertPathBuilderException("Unable to find certificate chain.");
    }
    

    return result;
  }
  






  protected CertPathBuilderResult build(X509Certificate tbvCert, PKIXExtendedBuilderParameters pkixParams, List tbvPath)
  {
    if (tbvPath.contains(tbvCert))
    {
      return null;
    }
    

    if (pkixParams.getExcludedCerts().contains(tbvCert))
    {
      return null;
    }
    
    if (pkixParams.getMaxPathLength() != -1)
    {
      if (tbvPath.size() - 1 > pkixParams.getMaxPathLength())
      {
        return null;
      }
    }
    
    tbvPath.add(tbvCert);
    


    CertPathBuilderResult builderResult = null;
    
    try
    {
      CertificateFactory cFact = new CertificateFactory();
      validator = new PKIXCertPathValidatorSpi();
    }
    catch (Exception e)
    {
      PKIXCertPathValidatorSpi validator;
      throw new RuntimeException("Exception creating support classes.");
    }
    try
    {
      PKIXCertPathValidatorSpi validator;
      CertificateFactory cFact;
      if (CertPathValidatorUtilities.isIssuerTrustAnchor(tbvCert, pkixParams.getBaseParameters().getTrustAnchors(), pkixParams
        .getBaseParameters().getSigProvider()))
      {


        CertPath certPath = null;
        PKIXCertPathValidatorResult result = null;
        try
        {
          certPath = cFact.engineGenerateCertPath(tbvPath);
        }
        catch (Exception e)
        {
          throw new AnnotatedException("Certification path could not be constructed from certificate list.", e);
        }
        


        try
        {
          result = (PKIXCertPathValidatorResult)validator.engineValidate(certPath, pkixParams);

        }
        catch (Exception e)
        {
          throw new AnnotatedException("Certification path could not be validated.", e);
        }
        

        return new PKIXCertPathBuilderResult(certPath, result
          .getTrustAnchor(), result.getPolicyTree(), result
          .getPublicKey());
      }
      


      List stores = new ArrayList();
      

      stores.addAll(pkixParams.getBaseParameters().getCertificateStores());
      

      try
      {
        stores.addAll(CertPathValidatorUtilities.getAdditionalStoresFromAltNames(tbvCert
          .getExtensionValue(Extension.issuerAlternativeName.getId()), pkixParams.getBaseParameters().getNamedCertificateStoreMap()));
      }
      catch (CertificateParsingException e)
      {
        throw new AnnotatedException("No additional X.509 stores can be added from certificate locations.", e);
      }
      

      Collection issuers = new HashSet();
      

      try
      {
        issuers.addAll(CertPathValidatorUtilities.findIssuerCerts(tbvCert, pkixParams.getBaseParameters().getCertStores(), stores));
      }
      catch (AnnotatedException e)
      {
        throw new AnnotatedException("Cannot find issuer certificate for certificate in certification path.", e);
      }
      

      if (issuers.isEmpty())
      {
        throw new AnnotatedException("No issuer certificate for certificate in certification path found.");
      }
      
      Iterator it = issuers.iterator();
      
      while ((it.hasNext()) && (builderResult == null))
      {
        X509Certificate issuer = (X509Certificate)it.next();
        builderResult = build(issuer, pkixParams, tbvPath);
      }
      
    }
    catch (AnnotatedException e)
    {
      certPathException = e;
    }
    if (builderResult == null)
    {
      tbvPath.remove(tbvCert);
    }
    return builderResult;
  }
}
