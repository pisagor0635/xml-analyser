package nl.group9.xmlanalyser.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import nl.group9.xmlanalyser.entity.Row;
import nl.group9.xmlanalyser.entity.Xml;
import nl.group9.xmlanalyser.repository.RowRepository;
import nl.group9.xmlanalyser.repository.XmlRepository;
import nl.group9.xmlanalyser.service.UtilService;
import nl.group9.xmlanalyser.service.XmlService;
import nl.group9.xmlanalyser.view.*;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class XmlServiceImpl implements XmlService {
    private final XmlRepository xmlRepository;
    private final RowRepository rowRepository;
    private final UtilService utilService;

    private Row row;

    @Override
    public Xml saveXml(Xml xml) {
        return xmlRepository.save(xml);
    }

    @Override
    public void parseXml(Xml xml, File file) {

        try {
            Instant start = Instant.now();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("row");

            for (int temp = 0; temp < nodeList.getLength(); temp++) {

                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    row = new Row();

                    Element element = (Element) node;

                    prepareRow(row, element);

                    row.setXml(xml);
                    rowRepository.save(row);
                }
            }
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).getSeconds();

            xml.setAnalyseTimeInSeconds(timeElapsed);

            xml.setState(State.FINISHED.getValue());
            xmlRepository.save(xml);

        } catch (FileNotFoundException e) {
            throw new RuntimeException("No such file or directory");
        } catch (Exception e) {
            xml.setState(State.FAILED.getValue());
            xml.setFailedSummary(e.getMessage());
            xmlRepository.save(xml);
        }
    }

    private void prepareRow(Row row, Element element) {

        try {
            if (utilService.checkNullOrEmpty(element.getAttribute("Id"))) {
                row.setRowId(Long.parseLong(element.getAttribute("Id")));
            }
            if (utilService.checkNullOrEmpty(element.getAttribute("OwnerUserId"))) {
                row.setOwnerUserId(Long.parseLong(element.getAttribute("OwnerUserId")));
            }
            if (utilService.checkNullOrEmpty(element.getAttribute("AcceptedAnswerId"))) {
                row.setAcceptedAnswerId(Long.parseLong(element.getAttribute("AcceptedAnswerId")));
            }

            row.setBody(element.getAttribute("Body"));
            row.setCreationDate(utilService.stringToDate(element.getAttribute("CreationDate")));
            row.setLastActivityDate(utilService.stringToDate(element.getAttribute("LastActivityDate")));

            if (utilService.checkNullOrEmpty(element.getAttribute("ParentId"))) {
                row.setParentId(Long.parseLong(element.getAttribute("ParentId")));
            }
            if (utilService.checkNullOrEmpty(element.getAttribute("PostTypeId"))) {
                row.setPostTypeId(Long.parseLong(element.getAttribute("PostTypeId")));
            }

            if (utilService.checkNullOrEmpty(element.getAttribute("Score"))) {
                row.setScore(Integer.parseInt(element.getAttribute("Score")));
            }
            row.setTitle(element.getAttribute("Title"));

            if (utilService.checkNullOrEmpty(element.getAttribute("Id"))) {
                row.setRowId(Integer.parseInt(element.getAttribute("Id")));
            }

            if (utilService.checkNullOrEmpty(element.getAttribute("ViewCount"))) {
                row.setViewCount(Integer.parseInt(element.getAttribute("ViewCount")));
            }

        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    @Override
    public AnalyseResponse analyseResult(Long id) {

        Xml xml = xmlRepository.getById(id);

        AnalyseResponse analyseResponse = new AnalyseResponse();
        analyseResponse.setId(xml.getId());
        analyseResponse.setAnalyseDate(xml.getAnalyseDate());
        analyseResponse.setState(xml.getState());
        analyseResponse.setFailedSummary(xml.getFailedSummary());
        analyseResponse.setAnalyseTimeInSeconds(xml.getAnalyseTimeInSeconds());

        List<Row> rows = rowRepository.findAllByXmlOrderByCreationDateAsc(xml);
        Details details = new Details();
        details.setFirstPost(rows.get(0).getCreationDate());
        details.setLastPost((rows.get(rows.size() - 1)).getCreationDate());
        details.setTotalPosts(new HashSet<>(rows).size());
        details.setTotalAcceptedPosts((int) rows.stream().filter(f -> f.getAcceptedAnswerId() != null).count());
        details.setAvgScore(rows.stream().mapToDouble(Row::getScore).average().orElse(0));

        analyseResponse.setDetails(details);

        return analyseResponse;
    }

    @Override
    public AnalysisWithAllRowResponse analysisWithAllRowResponse(Long id) {

        Xml xml = xmlRepository.getById(id);

        AnalysisWithAllRowResponse analysisWithAllRowResponse = new AnalysisWithAllRowResponse();

        analysisWithAllRowResponse.setId(xml.getId());
        analysisWithAllRowResponse.setState(xml.getState());
        analysisWithAllRowResponse.setFailedSummary(xml.getFailedSummary());

        List<Row> rows = rowRepository.findAllByXmlOrderByCreationDateAsc(xml);

        List<RowView> rowViews = new ArrayList<>();

        rows.forEach(e -> {
            RowView rowView = new RowView();
            rowView.setId(e.getId());
            rowView.setScore(e.getScore());
            rowView.setBody(e.getBody());
            rowView.setChildBodyList(rowRepository.findAllByParentId(e.getId()).stream().map(b -> b.getBody()).collect(Collectors.toList()));
            rowViews.add(rowView);
        });

        analysisWithAllRowResponse.setPosts(rowViews);

        return analysisWithAllRowResponse;
    }

    @Override
    public void deleteXml(Long id) {
        Xml xml = xmlRepository.getById(id);
        xml.setState(State.DELETED.getValue());
        xmlRepository.save(xml);
    }
}
