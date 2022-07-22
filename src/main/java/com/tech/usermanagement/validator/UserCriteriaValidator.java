package com.tech.usermanagement.validator;

import com.tech.usermanagement.constants.CommonConstants;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * @author xdebugger
 */
@UtilityClass
public class UserCriteriaValidator {

    public static boolean validateMinMaxCriteria(Double min, Double max) {
        return !(min > max || min < 0 || max < 0);
    }

    public static boolean validateSortByCriteria(String sortBy) {
        return !(StringUtils.isNotEmpty(sortBy) && !isSupportedSortBy(sortBy));
    }

    public static boolean validateLimitCriteria(Integer limit) {
        return !(limit != null && limit < 1);
    }

    private boolean isSupportedSortBy(String sortBy) {
        return sortBy.equalsIgnoreCase(CommonConstants.SORT_BY_NAME) ||
                sortBy.equalsIgnoreCase(CommonConstants.SORT_BY_SALARY);
    }
}
