package nl.group9.xmlanalyser.view;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AnalysisWithAllRowResponse {
    private long id;
    private String state;
    private String failedSummary;
    List<RowView> posts;
}
