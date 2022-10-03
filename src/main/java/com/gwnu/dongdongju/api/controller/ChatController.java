package com.gwnu.dongdongju.api.controller;

import com.gwnu.dongdongju.api.dto.chat.ChatRoom;
import com.gwnu.dongdongju.api.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
/**
 * @RequestMapping 어노테이션을 통해서 “/chat” 주소로 Post요청이 들어오면 json 데이터에서 name값을 받아 해당 이름으로 된 채팅방을 생성하고,
 * Get요청이 들어오면 현재 열려있는 모든 채팅방을 모두 조회 할 수 있게 해주었다.
 */
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom(@RequestBody String name) {
        return chatService.createRoom(name);
    }

    @GetMapping
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }
}