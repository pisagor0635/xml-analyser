package nl.group9.xmlanalyser.view;

import lombok.Data;

import java.util.Date;

@Data
public class Details {
    private Date firstPost;
    private Date lastPost;
    private int totalPosts;
    private int totalAcceptedPosts;
    private double avgScore;
}
