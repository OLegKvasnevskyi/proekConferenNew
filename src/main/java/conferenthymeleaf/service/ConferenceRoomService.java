package conferenthymeleaf.service;


import conferenthymeleaf.entity.ConferenceRoom;
import conferenthymeleaf.repository.ConferenceRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service

public class ConferenceRoomService {
    private final ConferenceRoomRepository conferenceRoomRepository;

    @Autowired
    ConferenceRoomService(ConferenceRoomRepository conferenceRoomRepository) {
        this.conferenceRoomRepository = conferenceRoomRepository;
    }

    public Page<ConferenceRoom> getConferenceRooms(final int pageNumber, final int size) {
        return conferenceRoomRepository.findAll(PageRequest.of(pageNumber, size));
    }

    public Optional<ConferenceRoom> getConferenceRoom(final Integer id) {
        return conferenceRoomRepository.findById(id);
    }

    public ConferenceRoom save(final ConferenceRoom conferenceRoomn) {
        return conferenceRoomRepository.save(conferenceRoomn);
    }

    public void delete(final Integer id) {
        conferenceRoomRepository.deleteById(id);
    }







    /*
        public List<ConferenceRoomEntity> getConferenceRoomsAll(String name) {
        if (name != null) {
            return conferenceRoomRepository.findByName(name)
                    .map(Collections::singletonList)
                    .orElse(Collections.emptyList());
        }
        List<ConferenceRoomEntity> listCustomer = conferenceRoomRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return conferenceRoomRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    //--------------------------------------------------------------
    public ConferenceRoomEntity getConferenceRoomByName(@NonNull String name) {
        return conferenceRoomRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException("Organization Not Found:  " + name));
    }

    //--------------------------------------------------------------
    public void addConferenceRoom(@NonNull ConferenceRoomEntity conferenceRoomEntity) {
        conferenceRoomRepository.findByName(conferenceRoomEntity.getName()).ifPresent(org -> {
            throw new RecordAlreadyExistsException("Organization already exists: " + conferenceRoomEntity.getName());
        });
        conferenceRoomRepository.save(conferenceRoomEntity);
    }

    //--------------------------------------------------------------
    public void removeConferenceRoomByName(@NonNull String name) {
        ConferenceRoomEntity conferenceRoomEntity = conferenceRoomRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException("Can't find organization with name: " + name));
        conferenceRoomRepository.delete(conferenceRoomEntity);
    }

    //--------------------------------------------------------------
    public void updateConferenceRoomn(@NonNull String oldName, @NonNull String newName) {
        conferenceRoomRepository.findByName(oldName).ifPresentOrElse(conf -> {
            conf.setName(newName);
            conferenceRoomRepository.save(conf);
        }, () -> {
            throw new RecordNotFoundException("Organization Not Found");
        });
    }

    //--------------------------------------------------------------
    //--------------------------------------------------------------
    public List<ConferenceRoomEntity> getConferenceRoomByNumberOfSeats(Integer numberOfSeats) {
        if (numberOfSeats != null) {
            return conferenceRoomRepository.findByNumberOfSeats(numberOfSeats)
                    .map(Collections::singletonList)
                    .orElse(Collections.emptyList());
        }
        return conferenceRoomRepository.findAll();
    }
//--------------------------------------------------------------
//--------------------------------------------------------------
*/

    public List<ConferenceRoom> getNewConferenceRoomsAll() {
        return conferenceRoomRepository.findAll();
    }
//--------------------------------------------------------------
/*
    public List<ConferenceRoomEntity> getConferenceRoomByAvailability(@NonNull Boolean availability) {
        if (name != null) {
            return conferenceRoomRepository.findByAvailability(availability)
                    .map(Collections::singletonList)
                    .orElse(Collections.emptyList());
        }
        return conferenceRoomRepository.findAll(Sort.by(Sort., "availability"));

    }

*/


}
