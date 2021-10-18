package nl.group9.xmlanalyser.service;

import nl.group9.xmlanalyser.service.serviceimpl.RabbitMQServiceImpl;
import nl.group9.xmlanalyser.view.XmlRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RabbitMQServiceTests")
 class RabbitMQServiceTests {

    @DisplayName("processXml")
    @Test
    void processXml() {

        RabbitMQService rabbitMQService = new RabbitMQServiceImpl(null, null);

        XmlRequest xmlRequest = new XmlRequest();

        xmlRequest.setUrl(null);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> rabbitMQService.processXml(xmlRequest));

        Assertions.assertEquals("Url can not be null", exception.getMessage());

    }


}
