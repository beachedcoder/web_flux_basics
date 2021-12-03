package com.roitraining.demos.web_flux_basics.entity;

import java.util.Objects;
import java.util.StringJoiner;

public class RequestError {
    private int code;
    private String message;

    public RequestError() {
    }

    public RequestError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestError)) return false;
        RequestError that = (RequestError) o;
        return getCode() == that.getCode() && getMessage().equals(that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getMessage());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RequestError.class.getSimpleName() + "[", "]")
                .add("code=" + code)
                .add("message='" + message + "'")
                .toString();
    }
}
