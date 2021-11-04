package com.telegram.bot.chatbot.service.analyze;

import com.telegram.bot.chatbot.data.UserMessageState;
import com.telegram.bot.chatbot.enums.MessageType;
import com.telegram.bot.chatbot.utils.NameBuilder;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MessageParser {
    private final Map<Long, List<UserMessageState>> infoStorage = new HashMap<>();

    public void analyze(Message msg) {
        MessageType messageType = Objects.nonNull(msg.getSticker()) ? MessageType.STICKER :
                (Objects.nonNull(msg.getDocument()) || Objects.nonNull(msg.getPhoto())) ? MessageType.MEDIA : MessageType.TEXT;
        List<UserMessageState> userMessageState = List.of(UserMessageState.builder()
                .userId(msg.getFrom().getId())
                .type(messageType)
                .build());
        infoStorage.merge(msg.getChatId(), userMessageState, (oldValue, addValue) ->
                Stream.concat(oldValue.stream(), addValue.stream())
                        .collect(Collectors.toList()));
    }

    public String getInfo(String userName, Long chatId, Integer userId) {
        return Optional.ofNullable(infoStorage.get(chatId))
                .map(list -> String.format("Пользователь '%s' выдавил из себя: \n%s\n %s, Может хватит, а?", userName,
                        list.stream()
                        .filter(userMessageState -> Objects.equals(userId, userMessageState.getUserId()))
                        .collect(Collectors.groupingBy(el -> el.getType().getValue(), Collectors.counting())).entrySet()
                        .stream()
                        .map(e -> e.getKey() + " :  " + e.getValue())
                        .collect(Collectors.joining(";\n")), userName))
                .orElse("Информации о пользователе нет");
    }
}
