package com.govtech.usermanagement.controller;

import com.govtech.usermanagement.constants.CommonConstants;
import com.govtech.usermanagement.model.FileUploadSuccessResponse;
import com.govtech.usermanagement.model.UsersList;
import com.govtech.usermanagement.service.UsersService;
import com.govtech.usermanagement.util.CommonRuntimeException;
import com.govtech.usermanagement.validator.FileUploadValidator;
import com.govtech.usermanagement.validator.UserCriteriaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xdebugger
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/usersInfo")
public class UserController {

    private final UsersService usersService;

    /**
     * HTTP endpoint to upload user info.
     * Return success or failure. 1 if successful and 0 if failure.
     * If failure, HTTP status code should not be HTTP_OK.
     * File upload is an all-or-nothing operation.
     * The entire file's changes are only applied after the whole file passes validation.
     * If the file has an error, none of its rows should be updated in the database.
     *
     * @param file MultipartFile
     * @return FileUploadResponseVO
     */
    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileUploadSuccessResponse> uploadUsersInfo(@RequestPart("file") MultipartFile file)
            throws CommonRuntimeException {
        boolean isValid = FileUploadValidator.validateFile(file);
        if (!isValid)
            throw new CommonRuntimeException(CommonConstants.EXCEPTION_FILEVALIDATION);
        return new ResponseEntity<>(usersService.processUploadFile(file), HttpStatus.OK);
    }

    /**
     * Return list of users that match specified criteria and ordering in JSON form:
     * {
     *     "results" : [
     *          {
     *              "name": "John",
     *              "salary": 3000.0
     *          },
     *          {
     *              "name": "John 2",
     *              "salary": 3500.0
     *          }
     *     ]
     * }
     *
     * @param min Double minimum salary. Optional, defaults to 0.0
     * @param max Double maximum salary. Optional, defaults to 4000.0
     * @param offset Integer first result among set to be returned. Optional, defaults to 0
     * @param limit Integer number of results to include. Optional, default to no limit
     * @param sort String Name or Salary, non-case sensitive. Optional, defaults to no sorting. Sort only in ascending sequence.
     * @return UsersList
     */
    @PostMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsersList> getUsersInfo(@RequestParam(defaultValue = "0.0") Double min,
                                                  @RequestParam(defaultValue = "4000.0") Double max,
                                                  @RequestParam(defaultValue = "0") Integer offset,
                                                  @RequestParam(required = false) Integer limit,
                                                  @RequestParam(required = false) String sort) throws CommonRuntimeException {
        boolean isValidMinMax = UserCriteriaValidator.validateMinMaxCriteria(min, max);
        if (!isValidMinMax)
            throw new CommonRuntimeException(CommonConstants.EXCEPTION_MIN_MAX);
        boolean isValidSortBy = UserCriteriaValidator.validateSortByCriteria(sort);
        if (!isValidSortBy)
            throw new CommonRuntimeException(CommonConstants.EXCEPTION_SORT_BY);
        boolean isValidLimit = UserCriteriaValidator.validateLimitCriteria(limit);
        if (!isValidLimit)
            throw new CommonRuntimeException(CommonConstants.EXCEPTION_LIMIT);
        return new ResponseEntity<>(UsersList.builder().results(usersService.getAllUsers(min, max,offset, limit, sort))
                .build(), HttpStatus.OK);
    }
}
