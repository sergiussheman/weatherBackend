package by.com.lifetech.model.security;

import by.com.lifetech.model.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "USER")
public class User extends BaseEntity {


    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ROLE", nullable = false)
    private String role;

    @Column(name = "IS_ROOT", columnDefinition = "TINYINT(1)")
    private boolean isRoot;

    @Column(name = "EMAIL")
    private String email;

}
