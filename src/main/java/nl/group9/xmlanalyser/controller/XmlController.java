package nl.group9.xmlanalyser.controller;

import lombok.RequiredArgsConstructor;
import nl.group9.xmlanalyser.service.RabbitMQService;
import nl.group9.xmlanalyser.service.XmlService;
import nl.group9.xmlanalyser.view.AnalyseResponse;
import nl.group9.xmlanalyser.view.AnalysisWithAllRowResponse;
import nl.group9.xmlanalyser.view.XmlRequest;
import nl.group9.xmlanalyser.view.XmlResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/xml")
@RequiredArgsConstructor
public class XmlController {

    private final RabbitMQService rabbitMQService;

    private final XmlService xmlService;

    @PostMapping
    public ResponseEntity<XmlResponse> createXml(@RequestBody XmlRequest xmlRequest) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(rabbitMQService.processXml(xmlRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnalyseResponse> analyse(@PathVariable Long id) {
        return ResponseEntity.ok(xmlService.analyseResult(id));
    }

    @GetMapping("/{id}/rows")
    public ResponseEntity<AnalysisWithAllRowResponse> analysisWithAllRowResponse(@PathVariable Long id) {
        return ResponseEntity.ok(xmlService.analysisWithAllRowResponse(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        xmlService.deleteXml(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
