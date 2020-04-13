package de.demokratie.polizeibot.embed;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.temporal.TemporalAccessor;

public class EmbedCreator {

    private EmbedBuilder embed;
    private MessageBuilder messageBuilder;
    private Object content;

    /**
     * Constructors
     */
    public EmbedCreator() {
        embed = new EmbedBuilder();
        messageBuilder = new MessageBuilder();
        content = null;
    }

    public EmbedCreator(String title) {
        this();
        embed.setTitle(title);
    }

    public EmbedCreator(String title, Color color) {
        this(title);
        embed.setColor(color);
    }

    public EmbedCreator(Color color) {
        this();
        embed.setColor(color);
    }

    /**
     * Setting the Content of the message
     *
     * @param content the Content
     * @return
     */
    public EmbedCreator setMessageContent(Object content) {
        this.content = content;
        return this;
    }

    /**
     * Sets the title of the Embed
     *
     * @param title
     * @return
     */
    public EmbedCreator setTitle(String title) {
        embed.setTitle(title);
        return this;
    }

    /**
     * Sets the title with a URL
     *
     * @param title
     * @param url
     * @return
     */
    public EmbedCreator setTitle(String title, String url) {
        embed.setTitle(title, url);
        return this;
    }

    /**
     * Adding a field to the embed, which is automatically not inline
     *
     * @param title
     * @param description
     * @return
     */
    public EmbedCreator addField(String title, String description) {
        return addField(title, description, false);
    }

    /**
     * Adding a field to the embed
     *
     * @param title
     * @param description
     * @param inLine
     * @return
     */
    public EmbedCreator addField(String title, String description, boolean inLine) {
        embed.addField(title, description, inLine);
        return this;
    }

    /**
     * Adding a field to the embed as Object
     *
     * @param field
     * @return
     */
    public EmbedCreator addField(MessageEmbed.Field field) {
        embed.addField(field);
        return this;
    }

    /**
     * Setting the footer of the embed
     *
     * @param footer
     * @return
     */
    public EmbedCreator setFooter(String footer) {
        embed.setFooter(footer);
        return this;
    }

    /**
     * Setting the footer with an url
     *
     * @param footer
     * @param url
     * @return
     */
    public EmbedCreator setFooter(String footer, String url) {
        embed.setFooter(footer, url);
        return this;
    }

    /**
     * Setting the main embed image
     *
     * @param url the image's url
     * @return
     */
    public EmbedCreator setImage(String url) {
        embed.setImage(url);
        return this;
    }

    /**
     * Setting the description of the embed
     *
     * @param description
     * @return
     */
    public EmbedCreator setDescription(CharSequence description) {
        embed.setDescription(description);
        return this;
    }

    /**
     * Setting the thumbnail of the embed
     *
     * @param url
     * @return
     */
    public EmbedCreator setThumbnail(String url) {
        embed.setThumbnail(url);
        return this;
    }

    /**
     * Setting the author of the embed
     *
     * @param author
     * @return
     */
    public EmbedCreator setAuthor(String author) {
        embed.setAuthor(author);
        return this;
    }

    /**
     * Same as above but with link
     *
     * @param author
     * @param url
     * @return
     */
    public EmbedCreator setAuthor(String author, String url) {
        embed.setAuthor(author, url);
        return this;
    }

    /**
     * Save as just above but with additional icon
     *
     * @param author
     * @param url
     * @param iconUrl
     * @return
     */
    public EmbedCreator setAuthor(String author, String url, String iconUrl) {
        embed.setAuthor(author, url, iconUrl);
        return this;
    }

    /**
     * Setting the Timestamp of the embed
     *
     * @param timestamp for current timestamp: use {@link java.time.Instant}.now()
     * @return
     */
    public EmbedCreator setTimestamp(TemporalAccessor timestamp) {
        embed.setTimestamp(timestamp);
        return this;
    }

    /**
     * Adding a blank field to the embed, automatically not inline
     *
     * @return
     */
    public EmbedCreator addBlankField() {
        return addBlankField(false);
    }

    /**
     * Adding a blank field to the embed
     *
     * @param inLine
     * @return
     */
    public EmbedCreator addBlankField(boolean inLine) {
        embed.addBlankField(inLine);
        return this;
    }

    /**
     * Setting the title with url
     * Setting the image of the embed
     *
     * @param title
     * @param url
     * @param image
     * @return
     */
    public EmbedCreator setTitle(String title, String url, String image) {
        embed.setTitle(title, url);
        embed.setImage(image);
        return this;
    }

    /**
     * Building the whole embed with message content
     *
     * @return the resulting Message
     */
    public Message build() {
        if (content != null)
            messageBuilder.append(content);
        messageBuilder.setEmbed(embed.build());
        return messageBuilder.build();
    }

}
