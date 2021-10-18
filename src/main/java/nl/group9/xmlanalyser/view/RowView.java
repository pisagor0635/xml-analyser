package nl.group9.xmlanalyser.view;

import lombok.Data;

import java.util.List;

@Data
public class RowView {
    private long id;
    private int score;
    private String body;
    List<String> childBodyList;

}
