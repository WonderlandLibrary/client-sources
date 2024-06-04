package org.spongycastle.asn1.x509;

public abstract interface NameConstraintValidator
{
  public abstract void checkPermitted(GeneralName paramGeneralName)
    throws NameConstraintValidatorException;
  
  public abstract void checkExcluded(GeneralName paramGeneralName)
    throws NameConstraintValidatorException;
  
  public abstract void intersectPermittedSubtree(GeneralSubtree paramGeneralSubtree);
  
  public abstract void intersectPermittedSubtree(GeneralSubtree[] paramArrayOfGeneralSubtree);
  
  public abstract void intersectEmptyPermittedSubtree(int paramInt);
  
  public abstract void addExcludedSubtree(GeneralSubtree paramGeneralSubtree);
}
