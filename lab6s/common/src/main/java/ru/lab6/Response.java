package ru.lab6;

import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID = -7424055826225839709L;
    private String responseInfo;

    public Response(String responseInfo) {
        this.responseInfo = responseInfo;
    }

    public String getResponseInfo() {
        return responseInfo;
    }

    public boolean isEmpty() {
        return responseInfo == null;
    }
}
