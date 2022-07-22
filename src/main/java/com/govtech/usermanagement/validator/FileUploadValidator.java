package com.govtech.usermanagement.validator;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * @author xdebugger
 */
@UtilityClass
public class FileUploadValidator {

    public static boolean validateFile(MultipartFile file) {
        boolean result = true;

        if (file.getSize() == 0)
            result = false;

        if (!isSupportedContentType(Objects.requireNonNull(file.getContentType()))) {
            result = false;
        }

        return result;
    }

    private static boolean isSupportedContentType(String contentType) {
        return contentType.equals("text/csv");
    }
}
