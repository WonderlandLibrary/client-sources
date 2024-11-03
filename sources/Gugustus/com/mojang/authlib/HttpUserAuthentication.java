package com.mojang.authlib;

public abstract class HttpUserAuthentication extends BaseUserAuthentication {
   protected HttpUserAuthentication(HttpAuthenticationService authenticationService) {
      super(authenticationService);
   }

   public HttpAuthenticationService getAuthenticationService() {
      return (HttpAuthenticationService)super.getAuthenticationService();
   }
}
