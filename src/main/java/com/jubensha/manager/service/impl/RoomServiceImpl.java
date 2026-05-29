package com.jubensha.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jubensha.manager.dao.RoomMapper;
import com.jubensha.manager.model.entity.Room;
import com.jubensha.manager.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

    @Override
    public List<Room> getAvailableRooms() {
        return list(new LambdaQueryWrapper<Room>().eq(Room::getStatus, "IDLE"));
    }

    @Override
    public void updateRoomStatus(Long id, String status) {
        Room room = getById(id);
        if (room != null) {
            room.setStatus(status);
            updateById(room);
        }
    }
}
