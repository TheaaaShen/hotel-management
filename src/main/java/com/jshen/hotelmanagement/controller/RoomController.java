package com.jshen.hotelmanagement.controller;

import com.jshen.hotelmanagement.exception.ResourceNotFoundException;
import com.jshen.hotelmanagement.exception.ResourceNotAvailableException;
import com.jshen.hotelmanagement.model.Room;
import com.jshen.hotelmanagement.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomController {

    @Autowired
    RoomRepository roomRepository;

    //Get All Rooms
    @GetMapping("/rooms")
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    //Create a new Room
    @PostMapping("/rooms")
    public Room createRoom (
            @Valid
            @RequestBody Room room){
        return roomRepository.save(room);
    }

    //Get a Single Room
    @GetMapping("/rooms/{id}")
    public Room getRoomById (
            @PathVariable (value = "id") Long roomId) {
                return roomRepository.findById(roomId)
                        .orElseThrow(() -> new ResourceNotFoundException("Room", "id", roomId));
    }

    //Check in a Room
    @PutMapping("/rooms/checkIn/{id}")
    public Room checkInRoom(
            @PathVariable(value = "id") Long roomId,
            //TODO: input for check in should be a booking/reservation object
            @Valid
            @RequestBody Room roomDetails) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", roomId));

        if (room.isOccupied()) throw new ResourceNotAvailableException("Room", "id", roomId);

        if (room.getMaxCapacity() < roomDetails.getActualCapacity()) throw new ResourceNotAvailableException("Room", "id", roomId);

        room.setActualCapacity(roomDetails.getActualCapacity());
        room.setOccupied(true);
        //TODO: update update time

        Room updatedRoom = roomRepository.save(room);
        return updatedRoom;
    }

    //Check out a Room
    @PutMapping("/rooms/checkOut/{id}")
    public Room checkOutRoom(
            @PathVariable(value = "id") Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", roomId));

        //TODO: maybe other exception invalidOperationException
        if (!room.isOccupied()) throw new ResourceNotAvailableException("Room", "id", roomId);

        room.setActualCapacity(0);
        room.setOccupied(false);
        //TODO: update update time

        Room updatedRoom = roomRepository.save(room);
        return updatedRoom;
    }

    //Delete a Room
    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<?> deleteRoom(
            @PathVariable(value = "id") Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", roomId));

        roomRepository.delete(room);

        return ResponseEntity.ok().build();
    }
}
