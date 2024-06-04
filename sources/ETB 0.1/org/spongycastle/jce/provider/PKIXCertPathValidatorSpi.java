package org.spongycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;
import java.security.cert.CertificateEncodingException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.TBSCertificate;
import org.spongycastle.jcajce.PKIXCertStoreSelector;
import org.spongycastle.jcajce.PKIXExtendedBuilderParameters;
import org.spongycastle.jcajce.PKIXExtendedParameters;
import org.spongycastle.jcajce.PKIXExtendedParameters.Builder;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.jce.exception.ExtCertPathValidatorException;
import org.spongycastle.x509.ExtendedPKIXParameters;





public class PKIXCertPathValidatorSpi
  extends CertPathValidatorSpi
{
  private final JcaJceHelper helper = new BCJcaJceHelper();
  


  public PKIXCertPathValidatorSpi() {}
  


  public CertPathValidatorResult engineValidate(CertPath certPath, CertPathParameters params)
    throws CertPathValidatorException, InvalidAlgorithmParameterException
  {
    PKIXExtendedParameters paramsPKIX;
    
    if ((params instanceof PKIXParameters))
    {
      PKIXExtendedParameters.Builder paramsPKIXBldr = new PKIXExtendedParameters.Builder((PKIXParameters)params);
      
      if ((params instanceof ExtendedPKIXParameters))
      {
        ExtendedPKIXParameters extPKIX = (ExtendedPKIXParameters)params;
        
        paramsPKIXBldr.setUseDeltasEnabled(extPKIX.isUseDeltasEnabled());
        paramsPKIXBldr.setValidityModel(extPKIX.getValidityModel());
      }
      
      paramsPKIX = paramsPKIXBldr.build();
    } else { PKIXExtendedParameters paramsPKIX;
      if ((params instanceof PKIXExtendedBuilderParameters))
      {
        paramsPKIX = ((PKIXExtendedBuilderParameters)params).getBaseParameters();
      } else { PKIXExtendedParameters paramsPKIX;
        if ((params instanceof PKIXExtendedParameters))
        {
          paramsPKIX = (PKIXExtendedParameters)params;
        }
        else
        {
          throw new InvalidAlgorithmParameterException("Parameters must be a " + PKIXParameters.class.getName() + " instance."); }
      }
    }
    if (paramsPKIX.getTrustAnchors() == null)
    {
      throw new InvalidAlgorithmParameterException("trustAnchors is null, this is not allowed for certification path validation.");
    }
    








    List certs = certPath.getCertificates();
    int n = certs.size();
    
    if (certs.isEmpty())
    {
      throw new CertPathValidatorException("Certification path is empty.", null, certPath, -1);
    }
    








    Set userInitialPolicySet = paramsPKIX.getInitialPolicies();
    




    try
    {
      TrustAnchor trust = CertPathValidatorUtilities.findTrustAnchor((X509Certificate)certs.get(certs.size() - 1), paramsPKIX
        .getTrustAnchors(), paramsPKIX.getSigProvider());
      
      if (trust == null)
      {
        throw new CertPathValidatorException("Trust anchor for certification path not found.", null, certPath, -1);
      }
      
      checkCertificate(trust.getTrustedCert());
    }
    catch (AnnotatedException e)
    {
      throw new CertPathValidatorException(e.getMessage(), e.getUnderlyingException(), certPath, certs.size() - 1);
    }
    
    TrustAnchor trust;
    PKIXExtendedParameters paramsPKIX = new PKIXExtendedParameters.Builder(paramsPKIX).setTrustAnchor(trust).build();
    




    int index = 0;
    









    List[] policyNodes = new ArrayList[n + 1];
    for (int j = 0; j < policyNodes.length; j++)
    {
      policyNodes[j] = new ArrayList();
    }
    
    Set policySet = new HashSet();
    
    policySet.add("2.5.29.32.0");
    
    PKIXPolicyNode validPolicyTree = new PKIXPolicyNode(new ArrayList(), 0, policySet, null, new HashSet(), "2.5.29.32.0", false);
    

    policyNodes[0].add(validPolicyTree);
    



    PKIXNameConstraintValidator nameConstraintValidator = new PKIXNameConstraintValidator();
    



    Set acceptablePolicies = new HashSet();
    int explicitPolicy;
    if (paramsPKIX.isExplicitPolicyRequired())
    {
      explicitPolicy = 0;
    }
    else
    {
      explicitPolicy = n + 1;
    }
    

    int inhibitAnyPolicy;
    
    int inhibitAnyPolicy;
    
    if (paramsPKIX.isAnyPolicyInhibited())
    {
      inhibitAnyPolicy = 0;
    }
    else
    {
      inhibitAnyPolicy = n + 1;
    }
    

    int policyMapping;
    
    int policyMapping;
    
    if (paramsPKIX.isPolicyMappingInhibited())
    {
      policyMapping = 0;
    }
    else
    {
      policyMapping = n + 1;
    }
    






    X509Certificate sign = trust.getTrustedCert();
    try {
      PublicKey workingPublicKey;
      if (sign != null)
      {
        X500Name workingIssuerName = PrincipalUtils.getSubjectPrincipal(sign);
        workingPublicKey = sign.getPublicKey();
      }
      else
      {
        X500Name workingIssuerName = PrincipalUtils.getCA(trust);
        workingPublicKey = trust.getCAPublicKey();
      }
    }
    catch (IllegalArgumentException ex) {
      PublicKey workingPublicKey;
      throw new ExtCertPathValidatorException("Subject of trust anchor could not be (re)encoded.", ex, certPath, -1);
    }
    X500Name workingIssuerName;
    PublicKey workingPublicKey;
    AlgorithmIdentifier workingAlgId = null;
    try
    {
      workingAlgId = CertPathValidatorUtilities.getAlgorithmIdentifier(workingPublicKey);
    }
    catch (CertPathValidatorException e)
    {
      throw new ExtCertPathValidatorException("Algorithm identifier of public key of trust anchor could not be read.", e, certPath, -1);
    }
    
    ASN1ObjectIdentifier workingPublicKeyAlgorithm = workingAlgId.getAlgorithm();
    ASN1Encodable workingPublicKeyParameters = workingAlgId.getParameters();
    



    int maxPathLength = n;
    




    if ((paramsPKIX.getTargetConstraints() != null) && 
      (!paramsPKIX.getTargetConstraints().match((X509Certificate)certs.get(0))))
    {
      throw new ExtCertPathValidatorException("Target certificate in certification path does not match targetConstraints.", null, certPath, 0);
    }
    




    List pathCheckers = paramsPKIX.getCertPathCheckers();
    Iterator certIter = pathCheckers.iterator();
    while (certIter.hasNext())
    {
      ((PKIXCertPathChecker)certIter.next()).init(false);
    }
    
    X509Certificate cert = null;
    
    for (index = certs.size() - 1; index >= 0; index--)
    {





      int i = n - index;
      






      cert = (X509Certificate)certs.get(index);
      boolean verificationAlreadyPerformed = index == certs.size() - 1;
      
      try
      {
        checkCertificate(cert);
      }
      catch (AnnotatedException e)
      {
        throw new CertPathValidatorException(e.getMessage(), e.getUnderlyingException(), certPath, index);
      }
      




      RFC3280CertPathUtilities.processCertA(certPath, paramsPKIX, index, workingPublicKey, verificationAlreadyPerformed, workingIssuerName, sign, helper);
      

      RFC3280CertPathUtilities.processCertBC(certPath, index, nameConstraintValidator);
      
      validPolicyTree = RFC3280CertPathUtilities.processCertD(certPath, index, acceptablePolicies, validPolicyTree, policyNodes, inhibitAnyPolicy);
      

      validPolicyTree = RFC3280CertPathUtilities.processCertE(certPath, index, validPolicyTree);
      
      RFC3280CertPathUtilities.processCertF(certPath, index, validPolicyTree, explicitPolicy);
      



      if (i != n)
      {
        if ((cert != null) && (cert.getVersion() == 1))
        {

          if ((i != 1) || (!cert.equals(trust.getTrustedCert())))
          {


            throw new CertPathValidatorException("Version 1 certificates can't be used as CA ones.", null, certPath, index);
          }
        }
        else {
          RFC3280CertPathUtilities.prepareNextCertA(certPath, index);
          
          validPolicyTree = RFC3280CertPathUtilities.prepareCertB(certPath, index, policyNodes, validPolicyTree, policyMapping);
          

          RFC3280CertPathUtilities.prepareNextCertG(certPath, index, nameConstraintValidator);
          

          explicitPolicy = RFC3280CertPathUtilities.prepareNextCertH1(certPath, index, explicitPolicy);
          policyMapping = RFC3280CertPathUtilities.prepareNextCertH2(certPath, index, policyMapping);
          inhibitAnyPolicy = RFC3280CertPathUtilities.prepareNextCertH3(certPath, index, inhibitAnyPolicy);
          



          explicitPolicy = RFC3280CertPathUtilities.prepareNextCertI1(certPath, index, explicitPolicy);
          policyMapping = RFC3280CertPathUtilities.prepareNextCertI2(certPath, index, policyMapping);
          

          inhibitAnyPolicy = RFC3280CertPathUtilities.prepareNextCertJ(certPath, index, inhibitAnyPolicy);
          

          RFC3280CertPathUtilities.prepareNextCertK(certPath, index);
          

          maxPathLength = RFC3280CertPathUtilities.prepareNextCertL(certPath, index, maxPathLength);
          

          maxPathLength = RFC3280CertPathUtilities.prepareNextCertM(certPath, index, maxPathLength);
          

          RFC3280CertPathUtilities.prepareNextCertN(certPath, index);
          
          Set criticalExtensions = cert.getCriticalExtensionOIDs();
          if (criticalExtensions != null)
          {
            criticalExtensions = new HashSet(criticalExtensions);
            

            criticalExtensions.remove(RFC3280CertPathUtilities.KEY_USAGE);
            criticalExtensions.remove(RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
            criticalExtensions.remove(RFC3280CertPathUtilities.POLICY_MAPPINGS);
            criticalExtensions.remove(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY);
            criticalExtensions.remove(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
            criticalExtensions.remove(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
            criticalExtensions.remove(RFC3280CertPathUtilities.POLICY_CONSTRAINTS);
            criticalExtensions.remove(RFC3280CertPathUtilities.BASIC_CONSTRAINTS);
            criticalExtensions.remove(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME);
            criticalExtensions.remove(RFC3280CertPathUtilities.NAME_CONSTRAINTS);
          }
          else
          {
            criticalExtensions = new HashSet();
          }
          

          RFC3280CertPathUtilities.prepareNextCertO(certPath, index, criticalExtensions, pathCheckers);
          

          sign = cert;
          

          workingIssuerName = PrincipalUtils.getSubjectPrincipal(sign);
          

          try
          {
            workingPublicKey = CertPathValidatorUtilities.getNextWorkingKey(certPath.getCertificates(), index, helper);
          }
          catch (CertPathValidatorException e)
          {
            throw new CertPathValidatorException("Next working key could not be retrieved.", e, certPath, index);
          }
          
          workingAlgId = CertPathValidatorUtilities.getAlgorithmIdentifier(workingPublicKey);
          
          workingPublicKeyAlgorithm = workingAlgId.getAlgorithm();
          
          workingPublicKeyParameters = workingAlgId.getParameters();
        }
      }
    }
    



    int explicitPolicy = RFC3280CertPathUtilities.wrapupCertA(explicitPolicy, cert);
    
    explicitPolicy = RFC3280CertPathUtilities.wrapupCertB(certPath, index + 1, explicitPolicy);
    







    Set criticalExtensions = cert.getCriticalExtensionOIDs();
    
    if (criticalExtensions != null)
    {
      criticalExtensions = new HashSet(criticalExtensions);
      
      criticalExtensions.remove(RFC3280CertPathUtilities.KEY_USAGE);
      criticalExtensions.remove(RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
      criticalExtensions.remove(RFC3280CertPathUtilities.POLICY_MAPPINGS);
      criticalExtensions.remove(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY);
      criticalExtensions.remove(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
      criticalExtensions.remove(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
      criticalExtensions.remove(RFC3280CertPathUtilities.POLICY_CONSTRAINTS);
      criticalExtensions.remove(RFC3280CertPathUtilities.BASIC_CONSTRAINTS);
      criticalExtensions.remove(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME);
      criticalExtensions.remove(RFC3280CertPathUtilities.NAME_CONSTRAINTS);
      criticalExtensions.remove(RFC3280CertPathUtilities.CRL_DISTRIBUTION_POINTS);
      criticalExtensions.remove(Extension.extendedKeyUsage.getId());
    }
    else
    {
      criticalExtensions = new HashSet();
    }
    
    RFC3280CertPathUtilities.wrapupCertF(certPath, index + 1, pathCheckers, criticalExtensions);
    
    PKIXPolicyNode intersection = RFC3280CertPathUtilities.wrapupCertG(certPath, paramsPKIX, userInitialPolicySet, index + 1, policyNodes, validPolicyTree, acceptablePolicies);
    

    if ((explicitPolicy > 0) || (intersection != null))
    {
      return new PKIXCertPathValidatorResult(trust, intersection, cert.getPublicKey());
    }
    
    throw new CertPathValidatorException("Path processing failed on policy.", null, certPath, index);
  }
  
  static void checkCertificate(X509Certificate cert)
    throws AnnotatedException
  {
    try
    {
      TBSCertificate.getInstance(cert.getTBSCertificate());
    }
    catch (CertificateEncodingException e)
    {
      throw new AnnotatedException("unable to process TBSCertificate");
    }
    catch (IllegalArgumentException e)
    {
      throw new AnnotatedException(e.getMessage());
    }
  }
}
