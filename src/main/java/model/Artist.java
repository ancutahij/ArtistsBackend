package model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "artists")
@Getter @Setter
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private Long id;

//    @NotEmpty(message = "Name field in mandatory")
    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "longtext")
    private String description;

    @Column(name = "avatar_url", columnDefinition = "longtext")
    private String avatarUrl;

    @Column(name = "cover_url", columnDefinition = "longtext")
    private String coverUrl;

    @Column(name = "stars")
    private Integer stars;

    @OneToOne
    @JoinColumn(name="contact_info_id", nullable = false)
    private ContactInfo contactInfo;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name="id_artist_subcategory", nullable = false)
    @JsonManagedReference
    private ArtistSubcategory artistSubcategory;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private Set<FollowArtist> followArtists;

    @Column(name = "educations", columnDefinition = "longtext")
    private String education;

    @Column(name = "awards", columnDefinition = "longtext")
    private String awards;

    @Column(name = "past_events", columnDefinition = "longtext")
    private String pastEvents;

    @Column(name = "highlighted_work", columnDefinition = "longtext")
    private String highlightedWork;

    @OneToMany(mappedBy = "reviewedArtist")
    @JsonBackReference(value="reviews")
    private Set<ArtistReview> reviews;

    @OneToMany(mappedBy = "byArtist")
    @JsonBackReference(value="posts")
    private Set<ArtistPost> posts;

}
