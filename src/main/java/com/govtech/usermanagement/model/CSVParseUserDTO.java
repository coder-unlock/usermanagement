package com.govtech.usermanagement.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xdebugger
 */
@Getter
@Setter
public class CSVParseUserDTO {

    @CsvBindByName(column = "Name", required = true)
    private String name;

    @CsvBindByName(column = "Salary", required = true)
    private Double salary;
}
