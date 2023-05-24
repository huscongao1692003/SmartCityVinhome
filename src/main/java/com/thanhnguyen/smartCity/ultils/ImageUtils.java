package com.thanhnguyen.smartCity.ultils;

import java.util.Base64;

public class ImageUtils {
    public static String getBase64UrlSafe(String base64Data) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(decodedBytes);
    }
}
