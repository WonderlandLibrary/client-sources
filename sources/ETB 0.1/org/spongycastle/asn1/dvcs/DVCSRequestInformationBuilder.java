package org.spongycastle.asn1.dvcs;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.PolicyInformation;
import org.spongycastle.util.BigIntegers;

















public class DVCSRequestInformationBuilder
{
  private int version = 1;
  
  private final ServiceType service;
  
  private DVCSRequestInformation initialInfo;
  
  private BigInteger nonce;
  private DVCSTime requestTime;
  private GeneralNames requester;
  private PolicyInformation requestPolicy;
  private GeneralNames dvcs;
  private GeneralNames dataLocations;
  private Extensions extensions;
  private static final int DEFAULT_VERSION = 1;
  private static final int TAG_REQUESTER = 0;
  private static final int TAG_REQUEST_POLICY = 1;
  private static final int TAG_DVCS = 2;
  private static final int TAG_DATA_LOCATIONS = 3;
  private static final int TAG_EXTENSIONS = 4;
  
  public DVCSRequestInformationBuilder(ServiceType service)
  {
    this.service = service;
  }
  
  public DVCSRequestInformationBuilder(DVCSRequestInformation initialInfo)
  {
    this.initialInfo = initialInfo;
    service = initialInfo.getService();
    version = initialInfo.getVersion();
    nonce = initialInfo.getNonce();
    requestTime = initialInfo.getRequestTime();
    requestPolicy = initialInfo.getRequestPolicy();
    dvcs = initialInfo.getDVCS();
    dataLocations = initialInfo.getDataLocations();
  }
  
  public DVCSRequestInformation build()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    if (version != 1)
    {
      v.add(new ASN1Integer(version));
    }
    v.add(service);
    if (nonce != null)
    {
      v.add(new ASN1Integer(nonce));
    }
    if (requestTime != null)
    {
      v.add(requestTime);
    }
    
    int[] tags = { 0, 1, 2, 3, 4 };
    





    ASN1Encodable[] taggedObjects = { requester, requestPolicy, dvcs, dataLocations, extensions };
    





    for (int i = 0; i < tags.length; i++)
    {
      int tag = tags[i];
      ASN1Encodable taggedObject = taggedObjects[i];
      if (taggedObject != null)
      {
        v.add(new DERTaggedObject(false, tag, taggedObject));
      }
    }
    
    return DVCSRequestInformation.getInstance(new DERSequence(v));
  }
  
  public void setVersion(int version)
  {
    if (initialInfo != null)
    {
      throw new IllegalStateException("cannot change version in existing DVCSRequestInformation");
    }
    
    this.version = version;
  }
  







  public void setNonce(BigInteger nonce)
  {
    if (initialInfo != null)
    {
      if (initialInfo.getNonce() == null)
      {
        this.nonce = nonce;
      }
      else
      {
        byte[] initialBytes = initialInfo.getNonce().toByteArray();
        byte[] newBytes = BigIntegers.asUnsignedByteArray(nonce);
        byte[] nonceBytes = new byte[initialBytes.length + newBytes.length];
        
        System.arraycopy(initialBytes, 0, nonceBytes, 0, initialBytes.length);
        System.arraycopy(newBytes, 0, nonceBytes, initialBytes.length, newBytes.length);
        
        this.nonce = new BigInteger(nonceBytes);
      }
    }
    
    this.nonce = nonce;
  }
  
  public void setRequestTime(DVCSTime requestTime)
  {
    if (initialInfo != null)
    {
      throw new IllegalStateException("cannot change request time in existing DVCSRequestInformation");
    }
    
    this.requestTime = requestTime;
  }
  
  public void setRequester(GeneralName requester)
  {
    setRequester(new GeneralNames(requester));
  }
  



  public void setRequester(GeneralNames requester)
  {
    this.requester = requester;
  }
  
  public void setRequestPolicy(PolicyInformation requestPolicy)
  {
    if (initialInfo != null)
    {
      throw new IllegalStateException("cannot change request policy in existing DVCSRequestInformation");
    }
    
    this.requestPolicy = requestPolicy;
  }
  
  public void setDVCS(GeneralName dvcs)
  {
    setDVCS(new GeneralNames(dvcs));
  }
  



  public void setDVCS(GeneralNames dvcs)
  {
    this.dvcs = dvcs;
  }
  
  public void setDataLocations(GeneralName dataLocation)
  {
    setDataLocations(new GeneralNames(dataLocation));
  }
  



  public void setDataLocations(GeneralNames dataLocations)
  {
    this.dataLocations = dataLocations;
  }
  
  public void setExtensions(Extensions extensions)
  {
    if (initialInfo != null)
    {
      throw new IllegalStateException("cannot change extensions in existing DVCSRequestInformation");
    }
    
    this.extensions = extensions;
  }
}
