package com.disney.utilities.managers;

/**
 * Created by Disney on 3/31/17.
 */
public class ExternalPropertiesManager
{
    public String getExternalEnvironmentProperty()
    {
        return System.getProperty("working_environment", "qa").toLowerCase();
    }

    public String getExternalBrowserProperty()
    {
        return System.getProperty("browser", "chrome");
    }

    public String getExternalUseSauceLabsProperty()
    {
        return System.getProperty("use_sauce_labs", "false");
    }

    public String getExternalSauceLabsUsernameProperty()
    {
       // return System.getProperty("sl_username", "disney_qe");cfd698b3-d5b6-4e11-9d6b-1c6bf4ef3484
        return System.getProperty("sl_username", "");
    }

    public String getExternalSauceLabsTokenProperty()
    {
       // return System.getProperty("sl_token", "aa169f66-83e2-4458-8efb-9b6f9455979f");
        return System.getProperty("sl_token", "");
    }

    public String getExternalBrowserVersionProperty()
    {
        return System.getProperty("browser_version", "latest");
    }

    public String getExternalVaultProperty()
    {
        return System.getProperty("use_vault", "false");
    }

    public String getExternalPlatformProperty()
    {
        return System.getProperty("platform", "win");
    }

    public String getExternalAccountNumberProperty()
    {
        return System.getProperty("account_number", "");
    }

    public String getExternalSauceLabsTunnelId()
    {
        return System.getProperty("sauce_tunnel_id", "");
    }

    public String getExternalSauceLabsBuildProperty()
    {
        return System.getProperty("sauce_labs_build", "FE_Analytics_" + getExternalEnvironmentProperty() + ":" + System.getProperty("timestamp"));
    }

    public boolean getExternalUseTestJSProperty()
    {
        return Boolean.valueOf(System.getProperty("use_test_js", "false"));
    }
}
