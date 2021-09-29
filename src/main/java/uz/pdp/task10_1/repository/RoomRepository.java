package uz.pdp.task10_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import uz.pdp.task10_1.entity.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findAllByHotelId(Integer hotel_id);
}
