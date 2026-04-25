package com.rsassistant.v99.security;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import com.rsassistant.v99.Constants;
import net.openid.appauth.*;

public class OAuthManager {
    private Context context;
    private AuthorizationService authService;

    public OAuthManager(Context context) {
        this.context = context;
        this.authService = new AuthorizationService(context);
    }

    public void startLogin(Activity activity) {
        AuthorizationServiceConfiguration config = new AuthorizationServiceConfiguration(
            Uri.parse(Constants.OAUTH_AUTH_URL),
            Uri.parse(Constants.OAUTH_TOKEN_URL)
        );
        
        AuthorizationRequest request = new AuthorizationRequest.Builder(
            config, Constants.OAUTH_CLIENT_ID, ResponseTypeValues.CODE,
            Uri.parse(Constants.OAUTH_REDIRECT_URI)
        ).setScope(Constants.OAUTH_SCOPE).build();
        
        activity.startActivityForResult(authService.getAuthorizationRequestIntent(request), Constants.REQUEST_CODE_OAUTH);
    }

    public interface OAuthCallback {
        void onSuccess(String token);
        void onError(String error);
    }
}
