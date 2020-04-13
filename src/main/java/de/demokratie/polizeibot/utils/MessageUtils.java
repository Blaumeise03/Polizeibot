package de.demokratie.polizeibot.utils;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;

public class MessageUtils {

    /**
     * Deletes the amount of lastest messages in a TextChannel
     *
     * @param channel
     * @param amount
     */
    public static void deleteMessages(TextChannel channel, int amount) {
        channel.purgeMessages(getMessages(channel, amount));
    }

    /**
     * Deletes the latest message in a TextChannel
     *
     * @param channel
     */
    public static void deleteLastMessage(TextChannel channel) {
        deleteMessages(channel, 1);
    }

    /**
     * Getting a List of Messages, pinned messages excluded
     *
     * @param channel
     * @param messageAmount
     * @return
     */
    public static List<Message> getMessages(TextChannel channel, int messageAmount) {
        return getMessages(channel, messageAmount, false);
    }

    /**
     * Getting a list of Messages
     *
     * @param channel
     * @param messageAmount
     * @param includePinned supposes whether pinned messages are included or not, default=false
     * @return
     */
    public static List<Message> getMessages(TextChannel channel, int messageAmount, boolean includePinned) {
        List<Message> messages = new ArrayList<>();
        int i = messageAmount;
        for (Message message : channel.getIterableHistory().cache(false)) {

            if (!message.isPinned()) messages.add(message);
            else if (message.isPinned() && includePinned) messages.add(message);

            if (--i <= 0) break;
        }
        return messages;
    }

}
