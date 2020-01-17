package model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "events")
@Getter @Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "url_poster")
    private String urlPoster;

    @ManyToOne
    @JoinColumn(name="address_id", nullable = false)
    private Address address;

    @Column(name="artists")
    private String artists;

    @Column(name="organizers")
    private String organizers;

    @Column(name="tickets_links")
    private String linksToTickets;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "reviewedEvent")
    @JsonBackReference
    private Set<EventReview> reviews;

    @OneToMany(mappedBy = "byEvent")
    @JsonBackReference
    private Set<EventPost> posts;

    @OneToMany(mappedBy = "event")
    @JsonBackReference
    private Set<FollowEvent> followEvents;

}
