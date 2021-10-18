package nl.group9.xmlanalyser.repository;

import nl.group9.xmlanalyser.entity.Xml;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XmlRepository extends JpaRepository<Xml,Long> {


}
