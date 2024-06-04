package org.spongycastle.math.field;

public abstract interface PolynomialExtensionField
  extends ExtensionField
{
  public abstract Polynomial getMinimalPolynomial();
}
