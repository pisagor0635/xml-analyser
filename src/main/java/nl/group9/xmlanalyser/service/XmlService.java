package nl.group9.xmlanalyser.service;

import nl.group9.xmlanalyser.entity.Xml;
import nl.group9.xmlanalyser.view.*;

import java.io.File;

public interface XmlService {

    Xml saveXml(Xml xml);

    void parseXml(Xml xml, File file);

    AnalyseResponse analyseResult(Long id);

    AnalysisWithAllRowResponse analysisWithAllRowResponse(Long id);

    void deleteXml(Long id);

}
