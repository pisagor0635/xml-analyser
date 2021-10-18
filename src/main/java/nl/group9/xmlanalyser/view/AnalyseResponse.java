package nl.group9.xmlanalyser.view;

import lombok.Data;

import java.util.Date;

@Data
public class AnalyseResponse {
    private long id;
    private Date analyseDate;
    private String state;
    private String failedSummary;
    private long analyseTimeInSeconds;
    private Details details;
}