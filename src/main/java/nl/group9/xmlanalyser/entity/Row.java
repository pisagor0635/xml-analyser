package nl.group9.xmlanalyser.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "xml_row")
@Data
public class Row implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "row_Id")
    private long rowId;

    @Column(name = "post_type_id")
    private long postTypeId;

    @Column(name = "parent_id")
    private long parentId;

    @Column(name = "accepted_answer_id")
    private Long acceptedAnswerId;

    @Column(name = "score")
    private int score;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "owner_user_id")
    private long ownerUserId;

    @Column(name = "body",columnDefinition="TEXT")
    private String body;

    @Column(name = "title")
    private String title;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "last_activity_date")
    private Date lastActivityDate;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date")
    private Date modifyDate;

    @ManyToOne
    @JoinColumn(name = "xml_id", nullable = false)
    private Xml xml;

}
