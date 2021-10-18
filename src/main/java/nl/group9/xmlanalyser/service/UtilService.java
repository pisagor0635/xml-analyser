package nl.group9.xmlanalyser.service;

import java.text.ParseException;
import java.util.Date;

public interface UtilService {

    Date stringToDate(String date) throws ParseException;

    boolean checkNullOrEmpty(String input);

}
