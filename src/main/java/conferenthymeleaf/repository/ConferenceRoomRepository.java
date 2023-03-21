package conferenthymeleaf.repository;


import conferenthymeleaf.entity.ConferenceRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoom, Integer> {

    //Optional<ConferenceRoom> findByName(String name);
//НОВАЯ ВЕРСИЯ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //private Integer numerOfSeats;
    //@Modifying

    //Query("SELECT c FROM conferenceroomentity WHERE c.numberOfSeats = :numberOfSeats")
    //@Query("SELECT c FROM conferenceroomentity c WHERE c.id = :id")
    //Optional<ConferenceRoom> findByNumberOfSeats(@Param("id") Integer numberOfSeats);

    //Optional<ConferenceRoomEntity> findByNumberOfSeats(Integer numberOfSeats);


    //private String identifier;
    //private String levelStatus;
    //private Boolean availability;
    //private Integer numerOfSeats;

}
