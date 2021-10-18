package nl.group9.xmlanalyser.service.serviceimpl;

import lombok.extern.slf4j.Slf4j;
import nl.group9.xmlanalyser.service.UtilService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class UtilServiceImpl implements UtilService {
    @Override
    public Date stringToDate(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(date);
    }

    @Override
    public boolean checkNullOrEmpty(String input) {
        return (input != null && !input.trim().isEmpty());
    }
}
