package com.roitraining.demos.web_flux_basics.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;


public class Course implements Persistable<UUID> {
    @Id
    private UUID id;
    private String title;
    private String summary;
    private int catalogNumber;
//      optional formatting for exchange
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime created;
    // optional formatting for exchange
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime updated;
    private boolean isNew;

    public Course() {
        this.isNew = true;
        this.id = UUID.randomUUID();
        this.catalogNumber = (int) UUID.randomUUID().getMostSignificantBits();
    }

    public Course(String title) {
        this();
        this.title = title;
    }

    public Course(String title, String summary){
        this(title);
        this.summary = summary;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(int catalogNumber) {
        this.catalogNumber = catalogNumber;
    }


    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
        this.isNew = false;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    @Override
    public boolean isNew() {
        if (created!=null && isNew) return true;
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return getCatalogNumber() == course.getCatalogNumber() && getId().equals(course.getId()) && getTitle().equals(course.getTitle()) && getSummary().equals(course.getSummary()) && getCreated().equals(course.getCreated());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getSummary(), getCatalogNumber(), getCreated());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Course.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("title='" + title + "'")
                .add("summary='" + summary + "'")
                .add("catalogNumber=" + catalogNumber)
                .add("created=" + created)
                .add("updated=" + updated)
                .add("isNew=" + isNew)
                .toString();
    }
}
