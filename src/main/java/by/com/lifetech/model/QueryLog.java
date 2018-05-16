package by.com.lifetech.model;

import by.com.lifetech.dto.weather.QueryStatus;
import by.com.lifetech.model.security.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "query_log")
public class QueryLog extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "CREATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date queryTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID", nullable = false)
    private Location location;

    @JoinColumn(name = "IP")
    private String ip;

    @JoinColumn(name = "RESPONSE")
    private String response;

    @JoinColumn(name = "DURATION")
    private Long duration;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "STATUS", columnDefinition = "smallint")
    private QueryStatus queryStatus;
}
