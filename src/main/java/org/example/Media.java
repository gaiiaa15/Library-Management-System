package org.example;

import java.util.ArrayList;
import java.util.List;

public class Media extends LibraryResources {

    // Static storage list
    public static List<Media> mediaList = new ArrayList<>();

    // Child-specific fields
    private String mediaID;
    private String topic;
    private double fileSize;
    private String format;
    private int duration;

    // Empty constructor
    public Media() {
        super();
    }

    // constructor matching Media.csv (6 columns)
    public Media(String itemID, String mediaID, String topic,
                 double fileSize, String format, int duration) {

        super(itemID);  // only ItemID known before linking

        this.mediaID = mediaID;
        this.topic = topic;
        this.fileSize = fileSize;
        this.format = format;
        this.duration = duration;
    }

    // Getters & Setters
    public String getMediaID() { return mediaID; }
    public void setMediaID(String mediaID) { this.mediaID = mediaID; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public double getFileSize() { return fileSize; }
    public void setFileSize(double fileSize) { this.fileSize = fileSize; }

    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    @Override
    public String toString() {
        return "Media{" +
                "ItemID='" + getItemID() + '\'' +
                ", Name='" + getName() + '\'' +
                ", Publisher='" + getPublisher() + '\'' +
                ", PublishDate='" + getPublishDate() + '\'' +
                ", Availability='" + getAvailability() + '\'' +
                ", AmountAvailable=" + getAmountAvailable() +
                ", Type='" + getType() + '\'' +
                ", MediaID='" + mediaID + '\'' +
                ", Topic='" + topic + '\'' +
                ", FileSize=" + fileSize +
                ", Format='" + format + '\'' +
                ", Duration=" + duration +
                '}';
    }
}