package com.govtech.usermanagement.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author xdebugger
 */
@Builder
@Setter
@Getter
@JsonSerialize
public class UsersList {

    private List<UserInfo> results;
}
