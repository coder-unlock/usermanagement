package com.tech.usermanagement.util;

import com.tech.usermanagement.entities.UserEntity;
import com.tech.usermanagement.model.CSVParseUserDTO;
import com.tech.usermanagement.model.UserInfo;
import lombok.experimental.UtilityClass;

/**
 * @author xdebugger
 */
@UtilityClass
public class CommonHelper {

    public static UserInfo fromUserEntityToUserInfo(UserEntity userEntity) {
        return UserInfo.builder().name(userEntity.getName()).salary(userEntity.getSalary()).build();
    }

    public static UserEntity mapToEntity(CSVParseUserDTO user) {
        return UserEntity.builder().name(user.getName()).salary(user.getSalary()).build();
    }
}
