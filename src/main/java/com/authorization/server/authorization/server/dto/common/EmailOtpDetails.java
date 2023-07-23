package com.authorization.server.authorization.server.dto.common;

import java.io.Serializable;

public class EmailOtpDetails implements Serializable {

    private String email;
    private String msgBody;
    private String subject;
    private String verificationCode;

    private EmailOtpDetails() {
        // Private constructor to enforce the use of the builder
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final EmailOtpDetails emailOtpDetails;

        public Builder() {
            emailOtpDetails = new EmailOtpDetails();
        }

        public Builder recipient(String recipient) {
            emailOtpDetails.email = recipient;
            return this;
        }

        public Builder msgBody(String msgBody) {
            emailOtpDetails.msgBody = msgBody;
            return this;
        }

        public Builder subject(String subject) {
            emailOtpDetails.subject = subject;
            return this;
        }

        public Builder verificationCode(String verificationCode) {
            emailOtpDetails.verificationCode = verificationCode;
            return this;
        }

        public EmailOtpDetails build() {
            return emailOtpDetails;
        }
    }
}
