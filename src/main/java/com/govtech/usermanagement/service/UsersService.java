package com.govtech.usermanagement.service;

import com.govtech.usermanagement.constants.CommonConstants;
import com.govtech.usermanagement.entities.UserEntity;
import com.govtech.usermanagement.model.CSVParseUserDTO;
import com.govtech.usermanagement.model.FileUploadSuccessResponse;
import com.govtech.usermanagement.model.UserInfo;
import com.govtech.usermanagement.repository.UsersRepository;
import com.govtech.usermanagement.util.CommonRuntimeException;
import com.govtech.usermanagement.util.CommonHelper;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xdebugger
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;

    public List<UserInfo> getAllUsers(Double min, Double max, Integer offset, Integer limit, String sortBy)
            throws CommonRuntimeException {
        try {
            PageRequest pageReq = PageRequest.of(offset, (limit == null || limit < 0) ? Integer.MAX_VALUE : limit,
                    StringUtils.isNotEmpty(sortBy) ? Sort.by(Sort.Direction.ASC, sortBy.toLowerCase()) : Sort.unsorted());

            return usersRepository.findBySalaryBetween(min, max, pageReq).stream().map(CommonHelper::fromUserEntityToUserInfo)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("Exception while fetching: " + ex);
            throw new CommonRuntimeException(ex);
        }
    }

    public FileUploadSuccessResponse processUploadFile(MultipartFile file) throws CommonRuntimeException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            List<CSVParseUserDTO> reader = new CsvToBeanBuilder<CSVParseUserDTO>(br).withType(CSVParseUserDTO.class)
                    .build().parse();

            List<UserEntity> usersList = new ArrayList<>();
            reader.forEach(user -> {
                if (user.getSalary() >= 0.0)
                    usersList.add(CommonHelper.mapToEntity(user));
            });

            usersRepository.saveAll(usersList);

            return FileUploadSuccessResponse.builder().success(1).build();
        } catch (Exception ex) {
            log.error("Exception Ocurred while processing : " + ex);
            throw new CommonRuntimeException(CommonConstants.EXCEPTION_CSVPROCESSING);
        }
    }

}
