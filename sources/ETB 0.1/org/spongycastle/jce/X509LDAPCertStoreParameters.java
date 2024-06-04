package org.spongycastle.jce;

import java.security.cert.CertStoreParameters;
import java.security.cert.LDAPCertStoreParameters;
import org.spongycastle.x509.X509StoreParameters;


























































































public class X509LDAPCertStoreParameters
  implements X509StoreParameters, CertStoreParameters
{
  private String ldapURL;
  private String baseDN;
  private String userCertificateAttribute;
  private String cACertificateAttribute;
  private String crossCertificateAttribute;
  private String certificateRevocationListAttribute;
  private String deltaRevocationListAttribute;
  private String authorityRevocationListAttribute;
  private String attributeCertificateAttributeAttribute;
  private String aACertificateAttribute;
  private String attributeDescriptorCertificateAttribute;
  private String attributeCertificateRevocationListAttribute;
  private String attributeAuthorityRevocationListAttribute;
  private String ldapUserCertificateAttributeName;
  private String ldapCACertificateAttributeName;
  private String ldapCrossCertificateAttributeName;
  private String ldapCertificateRevocationListAttributeName;
  private String ldapDeltaRevocationListAttributeName;
  private String ldapAuthorityRevocationListAttributeName;
  private String ldapAttributeCertificateAttributeAttributeName;
  private String ldapAACertificateAttributeName;
  private String ldapAttributeDescriptorCertificateAttributeName;
  private String ldapAttributeCertificateRevocationListAttributeName;
  private String ldapAttributeAuthorityRevocationListAttributeName;
  private String userCertificateSubjectAttributeName;
  private String cACertificateSubjectAttributeName;
  private String crossCertificateSubjectAttributeName;
  private String certificateRevocationListIssuerAttributeName;
  private String deltaRevocationListIssuerAttributeName;
  private String authorityRevocationListIssuerAttributeName;
  private String attributeCertificateAttributeSubjectAttributeName;
  private String aACertificateSubjectAttributeName;
  private String attributeDescriptorCertificateSubjectAttributeName;
  private String attributeCertificateRevocationListIssuerAttributeName;
  private String attributeAuthorityRevocationListIssuerAttributeName;
  private String searchForSerialNumberIn;
  
  public static class Builder
  {
    private String ldapURL;
    private String baseDN;
    private String userCertificateAttribute;
    private String cACertificateAttribute;
    private String crossCertificateAttribute;
    private String certificateRevocationListAttribute;
    private String deltaRevocationListAttribute;
    private String authorityRevocationListAttribute;
    private String attributeCertificateAttributeAttribute;
    private String aACertificateAttribute;
    private String attributeDescriptorCertificateAttribute;
    private String attributeCertificateRevocationListAttribute;
    private String attributeAuthorityRevocationListAttribute;
    private String ldapUserCertificateAttributeName;
    private String ldapCACertificateAttributeName;
    private String ldapCrossCertificateAttributeName;
    private String ldapCertificateRevocationListAttributeName;
    private String ldapDeltaRevocationListAttributeName;
    private String ldapAuthorityRevocationListAttributeName;
    private String ldapAttributeCertificateAttributeAttributeName;
    private String ldapAACertificateAttributeName;
    private String ldapAttributeDescriptorCertificateAttributeName;
    private String ldapAttributeCertificateRevocationListAttributeName;
    private String ldapAttributeAuthorityRevocationListAttributeName;
    private String userCertificateSubjectAttributeName;
    private String cACertificateSubjectAttributeName;
    private String crossCertificateSubjectAttributeName;
    private String certificateRevocationListIssuerAttributeName;
    private String deltaRevocationListIssuerAttributeName;
    private String authorityRevocationListIssuerAttributeName;
    private String attributeCertificateAttributeSubjectAttributeName;
    private String aACertificateSubjectAttributeName;
    private String attributeDescriptorCertificateSubjectAttributeName;
    private String attributeCertificateRevocationListIssuerAttributeName;
    private String attributeAuthorityRevocationListIssuerAttributeName;
    private String searchForSerialNumberIn;
    
    public Builder()
    {
      this("ldap://localhost:389", "");
    }
    
    public Builder(String ldapURL, String baseDN)
    {
      this.ldapURL = ldapURL;
      if (baseDN == null)
      {
        this.baseDN = "";
      }
      else
      {
        this.baseDN = baseDN;
      }
      
      userCertificateAttribute = "userCertificate";
      cACertificateAttribute = "cACertificate";
      crossCertificateAttribute = "crossCertificatePair";
      certificateRevocationListAttribute = "certificateRevocationList";
      deltaRevocationListAttribute = "deltaRevocationList";
      authorityRevocationListAttribute = "authorityRevocationList";
      attributeCertificateAttributeAttribute = "attributeCertificateAttribute";
      aACertificateAttribute = "aACertificate";
      attributeDescriptorCertificateAttribute = "attributeDescriptorCertificate";
      attributeCertificateRevocationListAttribute = "attributeCertificateRevocationList";
      attributeAuthorityRevocationListAttribute = "attributeAuthorityRevocationList";
      ldapUserCertificateAttributeName = "cn";
      ldapCACertificateAttributeName = "cn ou o";
      ldapCrossCertificateAttributeName = "cn ou o";
      ldapCertificateRevocationListAttributeName = "cn ou o";
      ldapDeltaRevocationListAttributeName = "cn ou o";
      ldapAuthorityRevocationListAttributeName = "cn ou o";
      ldapAttributeCertificateAttributeAttributeName = "cn";
      ldapAACertificateAttributeName = "cn o ou";
      ldapAttributeDescriptorCertificateAttributeName = "cn o ou";
      ldapAttributeCertificateRevocationListAttributeName = "cn o ou";
      ldapAttributeAuthorityRevocationListAttributeName = "cn o ou";
      userCertificateSubjectAttributeName = "cn";
      cACertificateSubjectAttributeName = "o ou";
      crossCertificateSubjectAttributeName = "o ou";
      certificateRevocationListIssuerAttributeName = "o ou";
      deltaRevocationListIssuerAttributeName = "o ou";
      authorityRevocationListIssuerAttributeName = "o ou";
      attributeCertificateAttributeSubjectAttributeName = "cn";
      aACertificateSubjectAttributeName = "o ou";
      attributeDescriptorCertificateSubjectAttributeName = "o ou";
      attributeCertificateRevocationListIssuerAttributeName = "o ou";
      attributeAuthorityRevocationListIssuerAttributeName = "o ou";
      searchForSerialNumberIn = "uid serialNumber cn";
    }
    







    public Builder setUserCertificateAttribute(String userCertificateAttribute)
    {
      this.userCertificateAttribute = userCertificateAttribute;
      
      return this;
    }
    







    public Builder setCACertificateAttribute(String cACertificateAttribute)
    {
      this.cACertificateAttribute = cACertificateAttribute;
      
      return this;
    }
    







    public Builder setCrossCertificateAttribute(String crossCertificateAttribute)
    {
      this.crossCertificateAttribute = crossCertificateAttribute;
      
      return this;
    }
    








    public Builder setCertificateRevocationListAttribute(String certificateRevocationListAttribute)
    {
      this.certificateRevocationListAttribute = certificateRevocationListAttribute;
      
      return this;
    }
    







    public Builder setDeltaRevocationListAttribute(String deltaRevocationListAttribute)
    {
      this.deltaRevocationListAttribute = deltaRevocationListAttribute;
      
      return this;
    }
    








    public Builder setAuthorityRevocationListAttribute(String authorityRevocationListAttribute)
    {
      this.authorityRevocationListAttribute = authorityRevocationListAttribute;
      
      return this;
    }
    








    public Builder setAttributeCertificateAttributeAttribute(String attributeCertificateAttributeAttribute)
    {
      this.attributeCertificateAttributeAttribute = attributeCertificateAttributeAttribute;
      
      return this;
    }
    







    public Builder setAACertificateAttribute(String aACertificateAttribute)
    {
      this.aACertificateAttribute = aACertificateAttribute;
      
      return this;
    }
    









    public Builder setAttributeDescriptorCertificateAttribute(String attributeDescriptorCertificateAttribute)
    {
      this.attributeDescriptorCertificateAttribute = attributeDescriptorCertificateAttribute;
      
      return this;
    }
    









    public Builder setAttributeCertificateRevocationListAttribute(String attributeCertificateRevocationListAttribute)
    {
      this.attributeCertificateRevocationListAttribute = attributeCertificateRevocationListAttribute;
      
      return this;
    }
    









    public Builder setAttributeAuthorityRevocationListAttribute(String attributeAuthorityRevocationListAttribute)
    {
      this.attributeAuthorityRevocationListAttribute = attributeAuthorityRevocationListAttribute;
      
      return this;
    }
    










    public Builder setLdapUserCertificateAttributeName(String ldapUserCertificateAttributeName)
    {
      this.ldapUserCertificateAttributeName = ldapUserCertificateAttributeName;
      
      return this;
    }
    









    public Builder setLdapCACertificateAttributeName(String ldapCACertificateAttributeName)
    {
      this.ldapCACertificateAttributeName = ldapCACertificateAttributeName;
      
      return this;
    }
    










    public Builder setLdapCrossCertificateAttributeName(String ldapCrossCertificateAttributeName)
    {
      this.ldapCrossCertificateAttributeName = ldapCrossCertificateAttributeName;
      
      return this;
    }
    










    public Builder setLdapCertificateRevocationListAttributeName(String ldapCertificateRevocationListAttributeName)
    {
      this.ldapCertificateRevocationListAttributeName = ldapCertificateRevocationListAttributeName;
      
      return this;
    }
    










    public Builder setLdapDeltaRevocationListAttributeName(String ldapDeltaRevocationListAttributeName)
    {
      this.ldapDeltaRevocationListAttributeName = ldapDeltaRevocationListAttributeName;
      
      return this;
    }
    










    public Builder setLdapAuthorityRevocationListAttributeName(String ldapAuthorityRevocationListAttributeName)
    {
      this.ldapAuthorityRevocationListAttributeName = ldapAuthorityRevocationListAttributeName;
      
      return this;
    }
    










    public Builder setLdapAttributeCertificateAttributeAttributeName(String ldapAttributeCertificateAttributeAttributeName)
    {
      this.ldapAttributeCertificateAttributeAttributeName = ldapAttributeCertificateAttributeAttributeName;
      
      return this;
    }
    









    public Builder setLdapAACertificateAttributeName(String ldapAACertificateAttributeName)
    {
      this.ldapAACertificateAttributeName = ldapAACertificateAttributeName;
      
      return this;
    }
    











    public Builder setLdapAttributeDescriptorCertificateAttributeName(String ldapAttributeDescriptorCertificateAttributeName)
    {
      this.ldapAttributeDescriptorCertificateAttributeName = ldapAttributeDescriptorCertificateAttributeName;
      
      return this;
    }
    










    public Builder setLdapAttributeCertificateRevocationListAttributeName(String ldapAttributeCertificateRevocationListAttributeName)
    {
      this.ldapAttributeCertificateRevocationListAttributeName = ldapAttributeCertificateRevocationListAttributeName;
      
      return this;
    }
    










    public Builder setLdapAttributeAuthorityRevocationListAttributeName(String ldapAttributeAuthorityRevocationListAttributeName)
    {
      this.ldapAttributeAuthorityRevocationListAttributeName = ldapAttributeAuthorityRevocationListAttributeName;
      
      return this;
    }
    









    public Builder setUserCertificateSubjectAttributeName(String userCertificateSubjectAttributeName)
    {
      this.userCertificateSubjectAttributeName = userCertificateSubjectAttributeName;
      
      return this;
    }
    









    public Builder setCACertificateSubjectAttributeName(String cACertificateSubjectAttributeName)
    {
      this.cACertificateSubjectAttributeName = cACertificateSubjectAttributeName;
      
      return this;
    }
    









    public Builder setCrossCertificateSubjectAttributeName(String crossCertificateSubjectAttributeName)
    {
      this.crossCertificateSubjectAttributeName = crossCertificateSubjectAttributeName;
      
      return this;
    }
    









    public Builder setCertificateRevocationListIssuerAttributeName(String certificateRevocationListIssuerAttributeName)
    {
      this.certificateRevocationListIssuerAttributeName = certificateRevocationListIssuerAttributeName;
      
      return this;
    }
    









    public Builder setDeltaRevocationListIssuerAttributeName(String deltaRevocationListIssuerAttributeName)
    {
      this.deltaRevocationListIssuerAttributeName = deltaRevocationListIssuerAttributeName;
      
      return this;
    }
    









    public Builder setAuthorityRevocationListIssuerAttributeName(String authorityRevocationListIssuerAttributeName)
    {
      this.authorityRevocationListIssuerAttributeName = authorityRevocationListIssuerAttributeName;
      
      return this;
    }
    









    public Builder setAttributeCertificateAttributeSubjectAttributeName(String attributeCertificateAttributeSubjectAttributeName)
    {
      this.attributeCertificateAttributeSubjectAttributeName = attributeCertificateAttributeSubjectAttributeName;
      
      return this;
    }
    









    public Builder setAACertificateSubjectAttributeName(String aACertificateSubjectAttributeName)
    {
      this.aACertificateSubjectAttributeName = aACertificateSubjectAttributeName;
      
      return this;
    }
    









    public Builder setAttributeDescriptorCertificateSubjectAttributeName(String attributeDescriptorCertificateSubjectAttributeName)
    {
      this.attributeDescriptorCertificateSubjectAttributeName = attributeDescriptorCertificateSubjectAttributeName;
      
      return this;
    }
    










    public Builder setAttributeCertificateRevocationListIssuerAttributeName(String attributeCertificateRevocationListIssuerAttributeName)
    {
      this.attributeCertificateRevocationListIssuerAttributeName = attributeCertificateRevocationListIssuerAttributeName;
      
      return this;
    }
    









    public Builder setAttributeAuthorityRevocationListIssuerAttributeName(String attributeAuthorityRevocationListIssuerAttributeName)
    {
      this.attributeAuthorityRevocationListIssuerAttributeName = attributeAuthorityRevocationListIssuerAttributeName;
      
      return this;
    }
    







    public Builder setSearchForSerialNumberIn(String searchForSerialNumberIn)
    {
      this.searchForSerialNumberIn = searchForSerialNumberIn;
      
      return this;
    }
    
    public X509LDAPCertStoreParameters build()
    {
      if ((ldapUserCertificateAttributeName == null) || (ldapCACertificateAttributeName == null) || (ldapCrossCertificateAttributeName == null) || (ldapCertificateRevocationListAttributeName == null) || (ldapDeltaRevocationListAttributeName == null) || (ldapAuthorityRevocationListAttributeName == null) || (ldapAttributeCertificateAttributeAttributeName == null) || (ldapAACertificateAttributeName == null) || (ldapAttributeDescriptorCertificateAttributeName == null) || (ldapAttributeCertificateRevocationListAttributeName == null) || (ldapAttributeAuthorityRevocationListAttributeName == null) || (userCertificateSubjectAttributeName == null) || (cACertificateSubjectAttributeName == null) || (crossCertificateSubjectAttributeName == null) || (certificateRevocationListIssuerAttributeName == null) || (deltaRevocationListIssuerAttributeName == null) || (authorityRevocationListIssuerAttributeName == null) || (attributeCertificateAttributeSubjectAttributeName == null) || (aACertificateSubjectAttributeName == null) || (attributeDescriptorCertificateSubjectAttributeName == null) || (attributeCertificateRevocationListIssuerAttributeName == null) || (attributeAuthorityRevocationListIssuerAttributeName == null))
      {





















        throw new IllegalArgumentException("Necessary parameters not specified.");
      }
      
      return new X509LDAPCertStoreParameters(this, null);
    }
  }
  

  private X509LDAPCertStoreParameters(Builder builder)
  {
    ldapURL = ldapURL;
    baseDN = baseDN;
    
    userCertificateAttribute = userCertificateAttribute;
    cACertificateAttribute = cACertificateAttribute;
    crossCertificateAttribute = crossCertificateAttribute;
    certificateRevocationListAttribute = certificateRevocationListAttribute;
    deltaRevocationListAttribute = deltaRevocationListAttribute;
    authorityRevocationListAttribute = authorityRevocationListAttribute;
    attributeCertificateAttributeAttribute = attributeCertificateAttributeAttribute;
    aACertificateAttribute = aACertificateAttribute;
    attributeDescriptorCertificateAttribute = attributeDescriptorCertificateAttribute;
    attributeCertificateRevocationListAttribute = attributeCertificateRevocationListAttribute;
    attributeAuthorityRevocationListAttribute = attributeAuthorityRevocationListAttribute;
    ldapUserCertificateAttributeName = ldapUserCertificateAttributeName;
    ldapCACertificateAttributeName = ldapCACertificateAttributeName;
    ldapCrossCertificateAttributeName = ldapCrossCertificateAttributeName;
    ldapCertificateRevocationListAttributeName = ldapCertificateRevocationListAttributeName;
    ldapDeltaRevocationListAttributeName = ldapDeltaRevocationListAttributeName;
    ldapAuthorityRevocationListAttributeName = ldapAuthorityRevocationListAttributeName;
    ldapAttributeCertificateAttributeAttributeName = ldapAttributeCertificateAttributeAttributeName;
    ldapAACertificateAttributeName = ldapAACertificateAttributeName;
    ldapAttributeDescriptorCertificateAttributeName = ldapAttributeDescriptorCertificateAttributeName;
    ldapAttributeCertificateRevocationListAttributeName = ldapAttributeCertificateRevocationListAttributeName;
    ldapAttributeAuthorityRevocationListAttributeName = ldapAttributeAuthorityRevocationListAttributeName;
    userCertificateSubjectAttributeName = userCertificateSubjectAttributeName;
    cACertificateSubjectAttributeName = cACertificateSubjectAttributeName;
    crossCertificateSubjectAttributeName = crossCertificateSubjectAttributeName;
    certificateRevocationListIssuerAttributeName = certificateRevocationListIssuerAttributeName;
    deltaRevocationListIssuerAttributeName = deltaRevocationListIssuerAttributeName;
    authorityRevocationListIssuerAttributeName = authorityRevocationListIssuerAttributeName;
    attributeCertificateAttributeSubjectAttributeName = attributeCertificateAttributeSubjectAttributeName;
    aACertificateSubjectAttributeName = aACertificateSubjectAttributeName;
    attributeDescriptorCertificateSubjectAttributeName = attributeDescriptorCertificateSubjectAttributeName;
    attributeCertificateRevocationListIssuerAttributeName = attributeCertificateRevocationListIssuerAttributeName;
    attributeAuthorityRevocationListIssuerAttributeName = attributeAuthorityRevocationListIssuerAttributeName;
    searchForSerialNumberIn = searchForSerialNumberIn;
  }
  



  public Object clone()
  {
    return this;
  }
  
  public boolean equal(Object o)
  {
    if (o == this)
    {
      return true;
    }
    
    if (!(o instanceof X509LDAPCertStoreParameters))
    {
      return false;
    }
    
    X509LDAPCertStoreParameters params = (X509LDAPCertStoreParameters)o;
    return (checkField(ldapURL, ldapURL)) && 
      (checkField(baseDN, baseDN)) && 
      (checkField(userCertificateAttribute, userCertificateAttribute)) && 
      (checkField(cACertificateAttribute, cACertificateAttribute)) && 
      (checkField(crossCertificateAttribute, crossCertificateAttribute)) && 
      (checkField(certificateRevocationListAttribute, certificateRevocationListAttribute)) && 
      (checkField(deltaRevocationListAttribute, deltaRevocationListAttribute)) && 
      (checkField(authorityRevocationListAttribute, authorityRevocationListAttribute)) && 
      (checkField(attributeCertificateAttributeAttribute, attributeCertificateAttributeAttribute)) && 
      (checkField(aACertificateAttribute, aACertificateAttribute)) && 
      (checkField(attributeDescriptorCertificateAttribute, attributeDescriptorCertificateAttribute)) && 
      (checkField(attributeCertificateRevocationListAttribute, attributeCertificateRevocationListAttribute)) && 
      (checkField(attributeAuthorityRevocationListAttribute, attributeAuthorityRevocationListAttribute)) && 
      (checkField(ldapUserCertificateAttributeName, ldapUserCertificateAttributeName)) && 
      (checkField(ldapCACertificateAttributeName, ldapCACertificateAttributeName)) && 
      (checkField(ldapCrossCertificateAttributeName, ldapCrossCertificateAttributeName)) && 
      (checkField(ldapCertificateRevocationListAttributeName, ldapCertificateRevocationListAttributeName)) && 
      (checkField(ldapDeltaRevocationListAttributeName, ldapDeltaRevocationListAttributeName)) && 
      (checkField(ldapAuthorityRevocationListAttributeName, ldapAuthorityRevocationListAttributeName)) && 
      (checkField(ldapAttributeCertificateAttributeAttributeName, ldapAttributeCertificateAttributeAttributeName)) && 
      (checkField(ldapAACertificateAttributeName, ldapAACertificateAttributeName)) && 
      (checkField(ldapAttributeDescriptorCertificateAttributeName, ldapAttributeDescriptorCertificateAttributeName)) && 
      (checkField(ldapAttributeCertificateRevocationListAttributeName, ldapAttributeCertificateRevocationListAttributeName)) && 
      (checkField(ldapAttributeAuthorityRevocationListAttributeName, ldapAttributeAuthorityRevocationListAttributeName)) && 
      (checkField(userCertificateSubjectAttributeName, userCertificateSubjectAttributeName)) && 
      (checkField(cACertificateSubjectAttributeName, cACertificateSubjectAttributeName)) && 
      (checkField(crossCertificateSubjectAttributeName, crossCertificateSubjectAttributeName)) && 
      (checkField(certificateRevocationListIssuerAttributeName, certificateRevocationListIssuerAttributeName)) && 
      (checkField(deltaRevocationListIssuerAttributeName, deltaRevocationListIssuerAttributeName)) && 
      (checkField(authorityRevocationListIssuerAttributeName, authorityRevocationListIssuerAttributeName)) && 
      (checkField(attributeCertificateAttributeSubjectAttributeName, attributeCertificateAttributeSubjectAttributeName)) && 
      (checkField(aACertificateSubjectAttributeName, aACertificateSubjectAttributeName)) && 
      (checkField(attributeDescriptorCertificateSubjectAttributeName, attributeDescriptorCertificateSubjectAttributeName)) && 
      (checkField(attributeCertificateRevocationListIssuerAttributeName, attributeCertificateRevocationListIssuerAttributeName)) && 
      (checkField(attributeAuthorityRevocationListIssuerAttributeName, attributeAuthorityRevocationListIssuerAttributeName)) && 
      (checkField(searchForSerialNumberIn, searchForSerialNumberIn));
  }
  
  private boolean checkField(Object o1, Object o2)
  {
    if (o1 == o2)
    {
      return true;
    }
    
    if (o1 == null)
    {
      return false;
    }
    
    return o1.equals(o2);
  }
  
  public int hashCode()
  {
    int hash = 0;
    
    hash = addHashCode(hash, userCertificateAttribute);
    hash = addHashCode(hash, cACertificateAttribute);
    hash = addHashCode(hash, crossCertificateAttribute);
    hash = addHashCode(hash, certificateRevocationListAttribute);
    hash = addHashCode(hash, deltaRevocationListAttribute);
    hash = addHashCode(hash, authorityRevocationListAttribute);
    hash = addHashCode(hash, attributeCertificateAttributeAttribute);
    hash = addHashCode(hash, aACertificateAttribute);
    hash = addHashCode(hash, attributeDescriptorCertificateAttribute);
    hash = addHashCode(hash, attributeCertificateRevocationListAttribute);
    hash = addHashCode(hash, attributeAuthorityRevocationListAttribute);
    hash = addHashCode(hash, ldapUserCertificateAttributeName);
    hash = addHashCode(hash, ldapCACertificateAttributeName);
    hash = addHashCode(hash, ldapCrossCertificateAttributeName);
    hash = addHashCode(hash, ldapCertificateRevocationListAttributeName);
    hash = addHashCode(hash, ldapDeltaRevocationListAttributeName);
    hash = addHashCode(hash, ldapAuthorityRevocationListAttributeName);
    hash = addHashCode(hash, ldapAttributeCertificateAttributeAttributeName);
    hash = addHashCode(hash, ldapAACertificateAttributeName);
    hash = addHashCode(hash, ldapAttributeDescriptorCertificateAttributeName);
    hash = addHashCode(hash, ldapAttributeCertificateRevocationListAttributeName);
    hash = addHashCode(hash, ldapAttributeAuthorityRevocationListAttributeName);
    hash = addHashCode(hash, userCertificateSubjectAttributeName);
    hash = addHashCode(hash, cACertificateSubjectAttributeName);
    hash = addHashCode(hash, crossCertificateSubjectAttributeName);
    hash = addHashCode(hash, certificateRevocationListIssuerAttributeName);
    hash = addHashCode(hash, deltaRevocationListIssuerAttributeName);
    hash = addHashCode(hash, authorityRevocationListIssuerAttributeName);
    hash = addHashCode(hash, attributeCertificateAttributeSubjectAttributeName);
    hash = addHashCode(hash, aACertificateSubjectAttributeName);
    hash = addHashCode(hash, attributeDescriptorCertificateSubjectAttributeName);
    hash = addHashCode(hash, attributeCertificateRevocationListIssuerAttributeName);
    hash = addHashCode(hash, attributeAuthorityRevocationListIssuerAttributeName);
    hash = addHashCode(hash, searchForSerialNumberIn);
    
    return hash;
  }
  
  private int addHashCode(int hashCode, Object o)
  {
    return hashCode * 29 + (o == null ? 0 : o.hashCode());
  }
  



  public String getAACertificateAttribute()
  {
    return aACertificateAttribute;
  }
  



  public String getAACertificateSubjectAttributeName()
  {
    return aACertificateSubjectAttributeName;
  }
  



  public String getAttributeAuthorityRevocationListAttribute()
  {
    return attributeAuthorityRevocationListAttribute;
  }
  



  public String getAttributeAuthorityRevocationListIssuerAttributeName()
  {
    return attributeAuthorityRevocationListIssuerAttributeName;
  }
  



  public String getAttributeCertificateAttributeAttribute()
  {
    return attributeCertificateAttributeAttribute;
  }
  



  public String getAttributeCertificateAttributeSubjectAttributeName()
  {
    return attributeCertificateAttributeSubjectAttributeName;
  }
  



  public String getAttributeCertificateRevocationListAttribute()
  {
    return attributeCertificateRevocationListAttribute;
  }
  




  public String getAttributeCertificateRevocationListIssuerAttributeName()
  {
    return attributeCertificateRevocationListIssuerAttributeName;
  }
  



  public String getAttributeDescriptorCertificateAttribute()
  {
    return attributeDescriptorCertificateAttribute;
  }
  



  public String getAttributeDescriptorCertificateSubjectAttributeName()
  {
    return attributeDescriptorCertificateSubjectAttributeName;
  }
  



  public String getAuthorityRevocationListAttribute()
  {
    return authorityRevocationListAttribute;
  }
  



  public String getAuthorityRevocationListIssuerAttributeName()
  {
    return authorityRevocationListIssuerAttributeName;
  }
  



  public String getBaseDN()
  {
    return baseDN;
  }
  



  public String getCACertificateAttribute()
  {
    return cACertificateAttribute;
  }
  



  public String getCACertificateSubjectAttributeName()
  {
    return cACertificateSubjectAttributeName;
  }
  



  public String getCertificateRevocationListAttribute()
  {
    return certificateRevocationListAttribute;
  }
  



  public String getCertificateRevocationListIssuerAttributeName()
  {
    return certificateRevocationListIssuerAttributeName;
  }
  



  public String getCrossCertificateAttribute()
  {
    return crossCertificateAttribute;
  }
  



  public String getCrossCertificateSubjectAttributeName()
  {
    return crossCertificateSubjectAttributeName;
  }
  



  public String getDeltaRevocationListAttribute()
  {
    return deltaRevocationListAttribute;
  }
  



  public String getDeltaRevocationListIssuerAttributeName()
  {
    return deltaRevocationListIssuerAttributeName;
  }
  



  public String getLdapAACertificateAttributeName()
  {
    return ldapAACertificateAttributeName;
  }
  



  public String getLdapAttributeAuthorityRevocationListAttributeName()
  {
    return ldapAttributeAuthorityRevocationListAttributeName;
  }
  



  public String getLdapAttributeCertificateAttributeAttributeName()
  {
    return ldapAttributeCertificateAttributeAttributeName;
  }
  



  public String getLdapAttributeCertificateRevocationListAttributeName()
  {
    return ldapAttributeCertificateRevocationListAttributeName;
  }
  



  public String getLdapAttributeDescriptorCertificateAttributeName()
  {
    return ldapAttributeDescriptorCertificateAttributeName;
  }
  



  public String getLdapAuthorityRevocationListAttributeName()
  {
    return ldapAuthorityRevocationListAttributeName;
  }
  



  public String getLdapCACertificateAttributeName()
  {
    return ldapCACertificateAttributeName;
  }
  



  public String getLdapCertificateRevocationListAttributeName()
  {
    return ldapCertificateRevocationListAttributeName;
  }
  



  public String getLdapCrossCertificateAttributeName()
  {
    return ldapCrossCertificateAttributeName;
  }
  



  public String getLdapDeltaRevocationListAttributeName()
  {
    return ldapDeltaRevocationListAttributeName;
  }
  



  public String getLdapURL()
  {
    return ldapURL;
  }
  



  public String getLdapUserCertificateAttributeName()
  {
    return ldapUserCertificateAttributeName;
  }
  



  public String getSearchForSerialNumberIn()
  {
    return searchForSerialNumberIn;
  }
  



  public String getUserCertificateAttribute()
  {
    return userCertificateAttribute;
  }
  



  public String getUserCertificateSubjectAttributeName()
  {
    return userCertificateSubjectAttributeName;
  }
  
  public static X509LDAPCertStoreParameters getInstance(LDAPCertStoreParameters params)
  {
    String server = "ldap://" + params.getServerName() + ":" + params.getPort();
    X509LDAPCertStoreParameters _params = new Builder(server, "").build();
    return _params;
  }
}
