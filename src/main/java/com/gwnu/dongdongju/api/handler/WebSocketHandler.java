package com.gwnu.dongdongju.api.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gwnu.dongdongju.api.dto.chat.ChatMessage;
import com.gwnu.dongdongju.api.dto.chat.ChatRoom;
import com.gwnu.dongdongju.api.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
/**
 * 스프링은 Text 타입과 Binary타입의 핸들러를 지원하는데,
 * 채팅 서비스를 만들 것 이므로 TextWebSocketHandler를 상속하여 WebSocketHandler를 만들기
 */
/**
 * 메세지를 json형식을 통해서 웹소켓을 통해 서버로 보내면, Handler는 이를받아 ObjectMapper를 통해서
 * 해당 json 데이터를 chatMessage.class에 맞게 파싱하여 ChatMessage 객체로 변환하고,
 * 이 json 데이터에 들어있는 roomId를 이용해서 해당 채팅방을 찾아 handlerAction() 이라는 메서드를 실행시킬 것이다.
 * 그러면 handlerAction() 메서드는 이 참여자가 현재 이미 채팅방에 접속된 상태인지, 아니면 이미 채팅에 참여해있는 상태인지를 판별하여,
 * 만약 채팅방에 처음 참여하는거라면 session을 연결해줌과 동시에 메시지를 보낼것이고 아니라면 메시지를 해당 채팅방으로 보내게 될 것이다.
 */
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("{}", payload);
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);

        ChatRoom chatRoom = chatService.findRoomById(chatMessage.getRoomId());
        chatRoom.handlerActions(session, chatMessage, chatService);
    }
}