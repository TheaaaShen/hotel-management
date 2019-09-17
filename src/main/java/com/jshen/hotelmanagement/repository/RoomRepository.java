package com.jshen.hotelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jshen.hotelmanagement.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
