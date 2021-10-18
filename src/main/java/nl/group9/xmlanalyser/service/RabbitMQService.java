package nl.group9.xmlanalyser.service;

import nl.group9.xmlanalyser.entity.Xml;
import nl.group9.xmlanalyser.view.XmlRequest;
import nl.group9.xmlanalyser.view.XmlResponse;

public interface RabbitMQService {

    XmlResponse processXml(XmlRequest xmlRequest);

    void receiveXml(Xml xml);
}
