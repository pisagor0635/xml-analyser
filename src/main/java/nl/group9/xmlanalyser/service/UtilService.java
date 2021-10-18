package nl.group9.xmlanalyser.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class UtilService {

    public Date stringToDate(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(date);
    }

    public boolean checkNullOrEmpty(String input) {
        return (input != null && !input.trim().isEmpty());
    }

}
