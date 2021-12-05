package com.roitraining.demos.web_flux_basics.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;


public class Author implements Persistable<UUID> {
    @Id
    private UUID id;
    private UUID authorCode;
    private String firstName;
    private String lastName;
    private LocalDateTime created;
    private LocalDateTime updated;
    private boolean isNew;


    public Author() {
        this.isNew = true;
        this.authorCode = UUID.randomUUID();
    }

    public Author(String firstName, String lastName) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAuthorCode() {
        return authorCode;
    }

    public void setAuthorCode(UUID authorCode) {
        this.authorCode = authorCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setNew(boolean isNew){
        this.isNew = isNew;
    }

    @Override
    public boolean isNew() {
        if (created!=null && isNew){
            return true;
        }
        return updated==null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return isNew() == author.isNew() && getId().equals(author.getId()) && getAuthorCode().equals(author.getAuthorCode()) && getFirstName().equals(author.getFirstName()) && getLastName().equals(author.getLastName()) && Objects.equals(created, author.created) && Objects.equals(updated, author.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAuthorCode(), getFirstName(), getLastName(), created, updated, isNew());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Author.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("authorCode=" + authorCode)
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("created=" + created)
                .add("updated=" + updated)
                .add("isNew=" + isNew)
                .toString();
    }

}
