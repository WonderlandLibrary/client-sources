package fr.litarvan.openauth.microsoft.model.response;

public class MinecraftStoreResponse {
  private final MinecraftStoreResponse$StoreProduct[] items;
  
  private final String signature;
  
  private final String keyId;
  
  public MinecraftStoreResponse(MinecraftStoreResponse$StoreProduct[] paramArrayOfMinecraftStoreResponse$StoreProduct, String paramString1, String paramString2) {
    this.items = paramArrayOfMinecraftStoreResponse$StoreProduct;
    this.signature = paramString1;
    this.keyId = paramString2;
  }
  
  public MinecraftStoreResponse$StoreProduct[] getItems() {
    return this.items;
  }
  
  public String getSignature() {
    return this.signature;
  }
  
  public String getKeyId() {
    return this.keyId;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\microsoft\model\response\MinecraftStoreResponse.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */