package com.jubensha.manager.controller.admin;

import com.jubensha.manager.common.Result;
import com.jubensha.manager.model.entity.Room;
import com.jubensha.manager.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/rooms")
@RequiredArgsConstructor
public class AdminRoomController {

    private final RoomService roomService;

    @GetMapping
    public Result<List<Room>> list() {
        return Result.success(roomService.list());
    }

    @PostMapping
    public Result<Room> create(@RequestBody Room room) {
        roomService.save(room);
        return Result.success(room);
    }

    @PutMapping("/{id}")
    public Result<Room> update(@PathVariable Long id, @RequestBody Room room) {
        room.setId(id);
        roomService.updateById(room);
        return Result.success(room);
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        roomService.removeById(id);
        return Result.success();
    }

    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam String status) {
        roomService.updateRoomStatus(id, status);
        return Result.success();
    }
}
