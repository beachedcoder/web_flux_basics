package com.roitraining.demos.web_flux_basics.entity;

import java.time.Instant;
import java.util.Objects;
import java.util.StringJoiner;

public class Course {
    private long id;
    private String title;
    private String summary;
    private int catalogNumber;

    public Course() {
        this.id = Instant.now().getNano();
        this.catalogNumber = Instant.EPOCH.getNano();
    }

    public Course(String title) {
        this();
        this.title = title;
    }

    public Course(String title, String summary){
        this(title);
        this.summary = summary;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return getId() == course.getId() && getCatalogNumber() == course.getCatalogNumber() && getTitle().equals(course.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getCatalogNumber());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Course.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("title='" + title + "'")
                .add("summary='" + summary + "'")
                .add("catalogNumber=" + catalogNumber)
                .toString();
    }
}
