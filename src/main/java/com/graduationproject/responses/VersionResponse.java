package com.graduationproject.responses;

public class VersionResponse {

    private int success;
    private String ver;

    public VersionResponse(int success, String ver) {
        this.success = success;
        this.ver = ver;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getVer() {
        return ver;
    }

    public void setVersion(String ver) {
        this.ver = ver;
    }
}
