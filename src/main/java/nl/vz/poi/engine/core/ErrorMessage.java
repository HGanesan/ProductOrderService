package nl.vz.poi.engine.core;

import org.springframework.http.HttpStatus;

public class ErrorMessage {

    private int errorCode;
    private HttpStatus status;
    private String msg;

    public ErrorMessage(int errorCode, HttpStatus status, String msg) {
        this.errorCode = errorCode;
        this.status = status;
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
