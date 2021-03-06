package model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String username;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "profileImgSrc")
    private String profileImgSrc;

    @OneToMany(mappedBy = "user")
    private Set<Artist> artists;


    @OneToMany(targetEntity = FollowArtist.class,mappedBy = "user",fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<FollowArtist> artistFollows;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private Set<FollowEvent> followEvents;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private Set<ArtistReview> artistReviews;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private Set<EventReview> eventReviews;

    @Column(name = "recoverPassowrdCode")
    private String recoverPassowrdCode;

    public User(String username, String password, Set<Artist> artists) {
        this.username = username;
        this.password = password;
        this.artists = artists;
    }

    public User() {
    }
}
