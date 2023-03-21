package conferenthymeleaf;


import conferenthymeleaf.entity.ConferenceRoom;
import conferenthymeleaf.entity.Organization;
import conferenthymeleaf.repository.ConferenceRoomRepository;
import conferenthymeleaf.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
class AppInitializer {

    private final OrganizationRepository organizationRepository;
    private final ConferenceRoomRepository conferenceRoomRepository;


    @Autowired
    AppInitializer(OrganizationRepository organizationRepository,
                   ConferenceRoomRepository conferenceRoomRepository) {
        this.organizationRepository = organizationRepository;
        this.conferenceRoomRepository = conferenceRoomRepository;

    }


    @PostConstruct
    void init() {
        organizationRepository.save(new Organization("Google"));
        organizationRepository.save(new Organization("Amazon"));
        organizationRepository.save(new Organization("Meta"));

        conferenceRoomRepository.save(new ConferenceRoom("BlueHall"));
        conferenceRoomRepository.save(new ConferenceRoom("WhiteHall"));
        conferenceRoomRepository.save(new ConferenceRoom("PinkHall"));

    }

}


