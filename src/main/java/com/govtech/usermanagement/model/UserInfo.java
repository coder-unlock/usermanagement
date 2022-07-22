package com.govtech.usermanagement.model;

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
public class UserInfo {

    private String name;
    private Double salary;
}
