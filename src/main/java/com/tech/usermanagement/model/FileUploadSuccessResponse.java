package com.tech.usermanagement.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xdebugger
 */
@Builder
@Setter
@Getter
@JsonSerialize
public class FileUploadSuccessResponse {

    private int success;
}
