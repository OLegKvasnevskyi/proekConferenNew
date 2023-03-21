package conferenthymeleaf.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organization")
public class Organization {

    //@OneToMany(mappedBy = "identifier", cascade = CascadeType.ALL, orphanRemoval = true)
    //public List<ConferenceRoomEntity> conferenceRooms;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", nullable = false)
    //@Size(min = 2, max = 20)
    //@NotBlank
    private String name;

    public Organization(String name) {
        this.name = name;
    }


}
