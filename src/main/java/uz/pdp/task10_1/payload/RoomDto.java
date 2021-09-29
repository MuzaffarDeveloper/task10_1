package uz.pdp.task10_1.payload;

import lombok.Data;

@Data
public class RoomDto {
    private String number;

    private String  floor;

    private long size;

    private Integer hotelId;
}
