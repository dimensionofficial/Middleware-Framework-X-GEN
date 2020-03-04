package com.xray;

import java.util.UUID;

public class XPayConfig {
    private String serverUrl =  "XXXX";
    private String serverBackUrl = "XXXX";
    private String appKey =  "XXXX";
    private String appName =  "XXXX";
    private String appIcon =  "XXXX";
    private UUID userId = UUID.randomUUID();
    private String appBackUrl =  "XXXX";

    private String payToAccount =  "XXXX";

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getServerBackUrl() {
        return serverBackUrl;
    }

    public void setServerBackUrl(String serverBackUrl) {
        this.serverBackUrl = serverBackUrl;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getAppBackUrl() {
        return appBackUrl;
    }

    public void setAppBackUrl(String appBackUrl) {
        this.appBackUrl = appBackUrl;
    }

    public String getPayToAccount() {
        return payToAccount;
    }

    public void setPayToAccount(String payToAccount) {
        this.payToAccount = payToAccount;
    }

}
