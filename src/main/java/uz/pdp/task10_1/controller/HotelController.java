package uz.pdp.task10_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task10_1.entity.Hotel;
import uz.pdp.task10_1.repository.HotelRepository;

import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;

    @GetMapping("/forMinistry")
    public Page<Hotel> getHotels(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<Hotel> hotelPage = hotelRepository.findAll(pageable);
        return hotelPage;
    }

    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable Integer id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (!optionalHotel.isPresent())
            return new Hotel();
        return optionalHotel.get();
    }

    @PostMapping
    public String addHotel(@RequestBody Hotel hotel) {
        boolean exists = hotelRepository.existsByName(hotel.getName());
        if (exists)
            return "The hotel with this name already exists";
        hotelRepository.save(hotel);
        return "Hotel added";
    }

    @DeleteMapping("/{id}")
    public String deleteHotel(@PathVariable Integer id) {
        try {
            hotelRepository.deleteById(id);
            return "Hotel deleted";
        } catch (Exception e) {
            return "Error in deleting";
        }
    }

    @PutMapping("/{id}")
    public String editHotel(@PathVariable Integer id, @RequestBody Hotel hotel){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (!optionalHotel.isPresent())
            return "Hotel not found";
        Hotel hotelLast = optionalHotel.get();
        hotelLast.setName(hotel.getName());
        hotelRepository.save(hotelLast);
        return "Hotel edited";
    }
}
