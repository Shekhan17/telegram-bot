package com.telegram.bot.chatbot.data;

import com.telegram.bot.chatbot.enums.MessageType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserMessageState {
    private Integer userId;
    private MessageType type;
}
