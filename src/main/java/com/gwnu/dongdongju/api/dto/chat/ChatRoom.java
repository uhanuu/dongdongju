package com.gwnu.dongdongju.api.dto.chat;

import com.gwnu.dongdongju.api.service.chat.ChatService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

/**
 * ChatRoom은 roomId와 name, 그리고 session을 관리할 sessions를 갖는다. json 데이터를 받아 WebSocketHandler에서
 * 해당 데이터에 담긴 roomId를 chatService를 통해서 조회 하여 해당 id의 채팅방을 찾아
 * json데이터에 담긴 메시지를 해당 채팅방으로 보내게된다. 이 기능을 담당하는 곳이 handlerActions 메서드로,
 * 해당 roomId의 채팅방을 찾아 handlerActions로 메시지와 세션을 보내면 이 메시지의 상태가 ENTER 상태인지 TALK 상태인지 판별하여
 * 만약 ENTER 상태라면 session을 연결한뒤에 해당 sender가 입장했다는 메시지를 해당 채팅방에 보내고,
 * 만약 이미 연결된 TALK 상태라면 해당 메시지를 해당 채팅방에 입장해있는 모든 클라이언트들 (Websocket session)에게 메시지를 보낸다.
 */
@Getter
public class ChatRoom {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public void handlerActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
        }
        sendMessage(chatMessage, chatService);

    }

    private <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream()
                .forEach(session -> chatService.sendMessage(session, message));
    }
}
