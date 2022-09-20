package com.bmc.mii.domain;

public class ConfigurationValue {

    private String remedyServer;
    private String remedyUsername, remedyPassword;
    private String remedyPort;
    private String remedyMiddleFormRequest;
    private String remedyMiddleFormCompleted;

    public ConfigurationValue(String remedyServer, String remedyUsername, String remedyPassword, String remedyPort, String remedyMiddleFormRequest, String remedyMiddleFormCompleted) {
        this.remedyServer = remedyServer;
        this.remedyUsername = remedyUsername;
        this.remedyPassword = remedyPassword;
        this.remedyPort = remedyPort;
        this.remedyMiddleFormRequest = remedyMiddleFormRequest;
        this.remedyMiddleFormCompleted = remedyMiddleFormCompleted;
    }

    public String getRemedyServer() {
        return remedyServer;
    }

    public void setRemedyServer(String remedyServer) {
        this.remedyServer = remedyServer;
    }

    public String getRemedyUsername() {
        return remedyUsername;
    }

    public void setRemedyUsername(String remedyUsername) {
        this.remedyUsername = remedyUsername;
    }

    public String getRemedyPassword() {
        return remedyPassword;
    }

    public void setRemedyPassword(String remedyPassword) {
        this.remedyPassword = remedyPassword;
    }

    public String getRemedyPort() {
        return remedyPort;
    }

    public void setRemedyPort(String remedyPort) {
        this.remedyPort = remedyPort;
    }

    public String getRemedyMiddleFormRequest() {
        return remedyMiddleFormRequest;
    }

    public void setRemedyMiddleFormRequest(String remedyMiddleFormRequest) {
        this.remedyMiddleFormRequest = remedyMiddleFormRequest;
    }

    public String getRemedyMiddleFormCompleted() {
        return remedyMiddleFormCompleted;
    }

    public void setRemedyMiddleFormCompleted(String remedyMiddleFormCompleted) {
        this.remedyMiddleFormCompleted = remedyMiddleFormCompleted;
    }

  

}
