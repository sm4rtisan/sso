package com.j.sso.config;

import java.util.ResourceBundle;

/**
 * @author yzsn
 */
public class SSOConfiguration {

    public static final String LOGO;
    public static final String SECRET_KEY;
    public static final String HEADER_AUTH_KEY;

    private static final String DEFAULT_LOGO = "L";
    private static final String DEFAULT_SECRET_KEY = "sso_default_key";

    static {

        HEADER_AUTH_KEY = "Authorization";

        ResourceBundle application = readProp("application");
        String applicationSsoLogo = application.getString("sso.logo");
        String applicationSsoSecretKey = application.getString("sso.secret.key");
        LOGO = (applicationSsoLogo == null || applicationSsoLogo.trim().equals("")) ? DEFAULT_LOGO : applicationSsoLogo;
        SECRET_KEY = (applicationSsoSecretKey == null || applicationSsoSecretKey.trim().equals("")) ? DEFAULT_SECRET_KEY : applicationSsoSecretKey;
    }

    private SSOConfiguration() {
    }

    private static ResourceBundle readProp(String propName) {

        try {
            return ResourceBundle.getBundle(propName);
        } catch (Exception e) {
            throw new RuntimeException("application.properties配置文件加载失败", e);
        }
    }
}
