package com.rsassistant.v99;

public final class Constants {
    public static final String APP_NAME = "RS Assistant";
    public static final String APP_VERSION = "99.0.0";
    public static final int VERSION_CODE = 99;
    
    public static final String OAUTH_AUTH_URL = "https://chat.z.ai/oauth/authorize";
    public static final String OAUTH_TOKEN_URL = "https://chat.z.ai/oauth/token";
    public static final String OAUTH_CLIENT_ID = "t3rmuxk1ng-assistant-v99";
    public static final String OAUTH_REDIRECT_URI = "rsassistant://oauth2redirect";
    public static final String OAUTH_SCOPE = "openid profile email assistant";
    
    public static final String API_BASE_URL = "https://api.z.ai/v1/";
    public static final String WS_URL = "wss://api.z.ai/ws";
    
    public static final String NOTIFICATION_CHANNEL_SERVICE = "rs_assistant_service";
    public static final int NOTIFICATION_ID_SERVICE = 1001;
    
    public static final String LANG_ENGLISH = "en";
    public static final String LANG_HINDI = "hi";
    public static final String LANG_HINGLISH = "hinglish";
    
    public static final int REQUEST_CODE_PERMISSIONS = 100;
    public static final int REQUEST_CODE_OAUTH = 103;
}
