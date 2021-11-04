package com.telegram.bot.chatbot.service.command;

import com.telegram.bot.chatbot.service.analyze.MessageParser;
import com.telegram.bot.chatbot.utils.NameBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Service
public class MessagesStateCommand extends Command {
    private static final String identifier = "info";
    private static final String description = "Получить информацию о количестве стикеров которых оставил пользователь";

    private final MessageParser messageParser;

    MessagesStateCommand(MessageParser messageParser) {
        super(identifier, description);
        this.messageParser = messageParser;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chat.getId().toString());
        message.setText(messageParser.getInfo(NameBuilder.getUserName(user), chat.getId(), user.getId()));
        sendAnswer(absSender, message);
    }
}
