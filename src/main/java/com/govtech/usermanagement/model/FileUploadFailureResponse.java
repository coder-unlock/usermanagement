package com.govtech.usermanagement.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xdebugger
 */
@Builder
@Getter
@Setter
@JsonSerialize
public class FileUploadFailureResponse {

    private int failure;
}
