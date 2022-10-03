package com.gwnu.dongdongju.api.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * 채팅중인 상태인지를 파악하기 위해 ENUM 타입으로 Message 타입을 선언해주고,
 * 이를 type으로 갖게 하도록한다. 그 이외에는 간단하게, 이 메시지를 보낼 채팅방 id인 roomId와,
 * 보내는 사람의 닉네임인 sender, 메시지를 담는 변수 message를 만들어, json 데이터로 채팅 데이터가 들어오면
 * 이를 ChatMessage로 변환해 줄 수 있도록하자.
 */
public class ChatMessage {
    public enum MessageType{
        ENTER, TALK
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}