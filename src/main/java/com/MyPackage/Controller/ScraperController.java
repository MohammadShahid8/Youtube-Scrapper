package com.MyPackage.Controller;
import com.MyPackage.dto.VideoDetails;
import com.MyPackage.exception.ScraperException;
import com.MyPackage.Services.YouTubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ScraperController {

    private final YouTubeService youTubeService;

    @Autowired
    public ScraperController(YouTubeService youTubeService) {
        this.youTubeService = youTubeService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/scrape")
    public String scrapeVideo(@RequestParam String url, Model model) {
        try {
            String videoId = youTubeService.extractVideoId(url);
            if (videoId == null) {
                throw new ScraperException("Invalid YouTube URL");
            }
            
            VideoDetails details = youTubeService.getVideoDetails(videoId);
            
            // Preserve the original description with HTML line breaks
            if (details.getDescription() != null) {
                // Replace newlines with HTML breaks if needed
                String processedDesc = details.getDescription()
                    .replace("\n", "<br />")
                    .replace("\r\n", "<br />");
                details.setDescription(processedDesc);
            }
            
            model.addAttribute("video", details);
            return "details";
        } catch (ScraperException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}