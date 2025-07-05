package com.MyPackage.dto;

import java.time.LocalDateTime;
import java.util.List;

public class VideoDetails {
    private String id;
    private String title;
    private String channel;
    private long views;
    private long likes;
    private long comments;
    private String description;
    private String thumbnailUrl;
    private LocalDateTime publishedAt;
    private List<String> tags;
    private String duration;
    private String category;
    
    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }
    public long getViews() { return views; }
    public void setViews(long views) { this.views = views; }
    public long getLikes() { return likes; }
    public void setLikes(long likes) { this.likes = likes; }
    public long getComments() { return comments; }
    public void setComments(long comments) { this.comments = comments; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
    public LocalDateTime getPublishedAt() { return publishedAt; }
    public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}