package com.authorization.server.authorization.server.dto.common;

import java.io.Serializable;

public class EmailOtpDetails implements Serializable {

    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;

    private EmailOtpDetails() {
        // Private constructor to enforce the use of the builder
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
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

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
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
            emailOtpDetails.recipient = recipient;
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

        public Builder attachment(String attachment) {
            emailOtpDetails.attachment = attachment;
            return this;
        }

        public EmailOtpDetails build() {
            return emailOtpDetails;
        }
    }
}
