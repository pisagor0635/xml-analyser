package nl.group9.xmlanalyser.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import nl.group9.xmlanalyser.entity.Xml;
import nl.group9.xmlanalyser.service.RabbitMQService;
import nl.group9.xmlanalyser.service.XmlService;
import nl.group9.xmlanalyser.view.State;
import nl.group9.xmlanalyser.view.XmlRequest;
import nl.group9.xmlanalyser.view.XmlResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RabbitMQServiceImpl implements RabbitMQService {

    private final RabbitTemplate rabbitTemplate;
    private final XmlService xmlService;

    @Override
    public XmlResponse processXml(XmlRequest xmlRequest) {

        Optional.ofNullable(xmlRequest.getUrl()).orElseThrow(() -> new RuntimeException("Url can not be null"));

        Xml xml = new Xml();
        xml.setAnalyseDate(new Date());
        xml.setUrl(xmlRequest.getUrl());
        xml.setState(State.ANALYZING.getValue());

        xml = xmlService.saveXml(xml);

        rabbitTemplate.convertAndSend("myQueue", xml);

        XmlResponse xmlResponse = new XmlResponse();
        xmlResponse.setId(xml.getId());

        return xmlResponse;
    }

    @RabbitListener(queues = "myQueue")
    @Override
    public void receiveXml(Xml xml) {

        File file = null;
        try {
            file = new File(xml.getId() + ".xml");
            FileUtils.copyURLToFile(new URL(xml.getUrl()), file);
            xmlService.parseXml(xml, file);
        } catch (MalformedURLException me) {
            me.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (file != null) {
                file.delete();
            }
        }
    }
}
