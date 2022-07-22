package com.tech.usermanagement.advise;

import com.tech.usermanagement.constants.CommonConstants;
import com.tech.usermanagement.model.ErrorResponseVO;
import com.tech.usermanagement.model.FileUploadFailureResponse;
import com.tech.usermanagement.util.CommonRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xdebugger
 */
@ControllerAdvice
@Slf4j
public class CommonControllerAdvise {

    @Value("${error.message.file.validation}")
    private String fileErrorMessage;

    @Value("${error.message.min.max.validation}")
    private String minMaxMessage;

    @Value("${error.message.sort.by.validation}")
    private String sortByMessage;

    @Value("${error.message.limit.validation}")
    private String limitMessage;

    @ExceptionHandler(CommonRuntimeException.class)
    @ResponseBody
    public ResponseEntity<Object> commonExceptionHandler(Exception exception, HttpServletRequest request) {

        log.info("[CommonControllerAdvise] commonExceptionHandler ==> ", exception);

        String path = "PATH :" + request.getRequestURI();
        String errorMessage = "ERROR: " + ExceptionUtils.getMessage(exception);

        if (ExceptionUtils.getMessage(exception).contains(CommonConstants.EXCEPTION_CSVPROCESSING))
            return new ResponseEntity<>(FileUploadFailureResponse.builder().failure(0).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);

        if (ExceptionUtils.getMessage(exception).contains(CommonConstants.EXCEPTION_FILEVALIDATION))
            return new ResponseEntity<>(ErrorResponseVO.builder().error(fileErrorMessage).build(),
                    HttpStatus.BAD_GATEWAY);

        if (ExceptionUtils.getMessage(exception).contains(CommonConstants.EXCEPTION_MIN_MAX))
            return new ResponseEntity<>(ErrorResponseVO.builder().error(minMaxMessage).build(), HttpStatus.BAD_GATEWAY);

        if (ExceptionUtils.getMessage(exception).contains(CommonConstants.EXCEPTION_SORT_BY))
            return new ResponseEntity<>(ErrorResponseVO.builder().error(sortByMessage).build(), HttpStatus.BAD_GATEWAY);

        if (ExceptionUtils.getMessage(exception).contains(CommonConstants.EXCEPTION_LIMIT))
            return new ResponseEntity<>(ErrorResponseVO.builder().error(limitMessage).build(), HttpStatus.BAD_GATEWAY);

        ErrorResponseVO error = ErrorResponseVO.builder().error(path + ", " + errorMessage).build();

        return new ResponseEntity<>(error, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<Object> httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception) {
        return new ResponseEntity<>(ErrorResponseVO.builder().error(exception.getLocalizedMessage()).build(),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

}
