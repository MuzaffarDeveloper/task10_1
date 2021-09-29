package uz.pdp.task10_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task10_1.entity.Hotel;
import uz.pdp.task10_1.entity.Room;
import uz.pdp.task10_1.payload.RoomDto;
import uz.pdp.task10_1.repository.HotelRepository;
import uz.pdp.task10_1.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    HotelRepository hotelRepository;

    @GetMapping("/forMinistry")
    public Page<Room> getRooms(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Room> roomPage = roomRepository.findAll(pageable);
        return roomPage;
    }

    @GetMapping("/byHotelId/{hotelId}")
    public List<Room> getRoomsByHotelId(@PathVariable Integer hotelId) {
        List<Room> allByHotelId = roomRepository.findAllByHotelId(hotelId);
        return allByHotelId;
    }

    @PostMapping
    public String addRoom(@RequestBody RoomDto roomDto) {
        Room room = new Room();
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(room.getSize());
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent())
            return "Hotel not found";
        Hotel hotel = optionalHotel.get();
        room.setHotel(hotel);
        roomRepository.save(room);
        return "Room added";
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Integer id) {
        try {
            roomRepository.deleteById(id);
            return "Room deleted";
        } catch (Exception e) {
            return "Error in deleting";
        }
    }

    @PutMapping("/{id}")
    public String editRoom(@PathVariable Integer id, @RequestBody RoomDto roomDto){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (!optionalRoom.isPresent())
            return "Room not found";
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent())
            return "Hotel not found";
        Room room = optionalRoom.get();
        room.setFloor(roomDto.getFloor());
        room.setNumber(roomDto.getNumber());
        room.setSize(roomDto.getSize());
        room.setHotel(optionalHotel.get());
        roomRepository.save(room);
        return "Room edited";
    }
}
