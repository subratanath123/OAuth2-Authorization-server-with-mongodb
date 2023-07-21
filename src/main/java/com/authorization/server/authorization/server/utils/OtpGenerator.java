package com.authorization.server.authorization.server.utils;

import java.security.SecureRandom;

public class OtpGenerator {

    public static String generateOtp(int length) {
        String digits = "0123456789";
        StringBuilder otp = new StringBuilder(length);

        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(digits.length());
            otp.append(digits.charAt(index));
        }

        return otp.toString();
    }

}