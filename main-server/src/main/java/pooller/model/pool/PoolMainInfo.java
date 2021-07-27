package pooller.model.pool;

import pooller.model.actors.Author;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class PoolMainInfo {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Author author;

    @OneToMany
    private List<PoolQuestion> questions;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;
    private Date created;
    private Date updated;
    public PoolMainInfo() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<PoolQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<PoolQuestion> questions) {
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
