package com.MyPackage.Services;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.MyPackage.Config.YouTubeConfig;
import com.MyPackage.dto.VideoDetails;
import com.MyPackage.exception.ScraperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class YouTubeService {

    private final YouTubeConfig config;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public YouTubeService(YouTubeConfig config, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.config = config;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Cacheable(value = "videoDetails", key = "#videoId")
    public VideoDetails getVideoDetails(String videoId) throws ScraperException {
        try {
            String url = String.format("%s?part=snippet,contentDetails,statistics&id=%s&key=%s",
                    config.getApiUrl(), videoId, config.getApiKey());
            
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode items = root.path("items");
            
            if (!items.isArray() || items.size() == 0) {
                throw new ScraperException("Video not found or API response is empty");
            }
            
            JsonNode snippet = items.get(0).path("snippet");
            JsonNode stats = items.get(0).path("statistics");
            JsonNode contentDetails = items.get(0).path("contentDetails");
            
            VideoDetails details = new VideoDetails();
            details.setId(videoId);
            details.setTitle(snippet.path("title").asText());
            details.setChannel(snippet.path("channelTitle").asText());
            details.setViews(stats.path("viewCount").asLong());
            details.setLikes(stats.path("likeCount").asLong());
            details.setComments(stats.path("commentCount").asLong());
            details.setDescription(snippet.path("description").asText());
            details.setThumbnailUrl(snippet.path("thumbnails").path("maxres").path("url").asText());
            
            // Format published date
            String publishedAt = snippet.path("publishedAt").asText();
            LocalDateTime dateTime = LocalDateTime.ofInstant(
                Instant.parse(publishedAt), ZoneId.systemDefault());
            details.setPublishedAt(dateTime);
            
            // Parse tags
            List<String> tags = new ArrayList<>();
            JsonNode tagsNode = snippet.path("tags");
            if (tagsNode.isArray()) {
                tagsNode.forEach(tag -> tags.add(tag.asText()));
            }
            details.setTags(tags);
            
            // Additional details
            details.setDuration(contentDetails.path("duration").asText());
            details.setCategory(snippet.path("categoryId").asText());
            
            return details;
            
        } catch (Exception e) {
            throw new ScraperException("Error fetching video details: " + e.getMessage(), e);
        }
    }

    public String extractVideoId(String url) {
        // Improved regex for all YouTube URL formats
        String regex = "(?:https?:\\/\\/)?(?:www\\.)?(?:youtube\\.com\\/watch\\?v=|youtu\\.be\\/|youtube\\.com\\/embed\\/|youtube\\.com\\/v\\/|youtube\\.com\\/shorts\\/)([\\w-]{11})";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(url);
        
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}