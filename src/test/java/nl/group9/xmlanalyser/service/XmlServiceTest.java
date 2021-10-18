package nl.group9.xmlanalyser.service;

import nl.group9.xmlanalyser.entity.Xml;
import nl.group9.xmlanalyser.service.serviceimpl.XmlServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;


@DisplayName("XmlServiceTest")
class XmlServiceTest {

    @DisplayName("parseXml")
    @Test
    void parseXml() {

        XmlService xmlService = new XmlServiceImpl(null, null, null);

        File file = new File("nonfile.xml");

        Xml xml = new Xml();

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> xmlService.parseXml(xml, file));

        Assertions.assertEquals("No such file or directory", exception.getMessage());

    }


}
