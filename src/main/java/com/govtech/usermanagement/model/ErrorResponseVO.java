package com.govtech.usermanagement.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xdebugger
 */
@Builder
@Setter
@Getter
public class ErrorResponseVO {

    private String error;
}
