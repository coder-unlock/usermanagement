package com.govtech.usermanagement.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author xdebugger
 */
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS_INFO")
public class UserEntity {

    @Id
    @Column(name = "NAME")
    private String name;

    @Column(name = "SALARY")
    private Double salary;

}
