package nl.group9.xmlanalyser.repository;

import nl.group9.xmlanalyser.entity.Row;
import nl.group9.xmlanalyser.entity.Xml;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RowRepository extends JpaRepository<Row,Long> {

    List<Row> findAllByXmlOrderByCreationDateAsc(Xml xml);

    List<Row> findAllByParentId(long id);
}
