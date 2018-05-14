package by.com.lifetech.model;

import by.com.lifetech.model.security.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "LOCATION")
public class Location extends BaseEntity {
    @Column(name = "VALUE", nullable = false, unique = true)
    private String value;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "CREATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date addTime;
}
