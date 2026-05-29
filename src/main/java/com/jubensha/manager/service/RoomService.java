package com.jubensha.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jubensha.manager.model.entity.Room;

import java.util.List;

public interface RoomService extends IService<Room> {
    List<Room> getAvailableRooms();
    void updateRoomStatus(Long id, String status);
}
