package com.example.springdataintro.models.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import com.example.springdataintro.models.entities.Author;

@Entity
@Table(name = "books")
public class Book extends BaseEntity{
    private String title;
    private String description;
    private EditionType editionType;
    private BigDecimal price;
    private Integer copies;
    private LocalDate releaseDate;
    private AgeRestriction ageRestriction;
    private Author author;
    private Set<Category> categories;

    public Book() {
    }

    public Book(String title, EditionType editionType, BigDecimal price, LocalDate releaseDate, AgeRestriction ageRestriction, com.example.springdataintro.models.entities.Author author, Set<Category> categories, int copies) {
        this.title = title;
        this.editionType =editionType;
        this.price= price;
        this.releaseDate = releaseDate;
        this.ageRestriction = ageRestriction;
        this.author = author;
        this.categories = categories;
        this.copies = copies;
    }

    @Column(name = "title",length = 50, nullable = false)

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "description",columnDefinition = "TEXT")

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Enumerated
    public EditionType getEditionType() {
        return editionType;
    }

    public void setEditionType(EditionType editionType) {
        this.editionType = editionType;
    }

    @Column(name="price",nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "copies",nullable = false)
    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    @Column(name = "release_date")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Enumerated
    public AgeRestriction getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(AgeRestriction ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    @ManyToOne
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {

        this.author = author;
    }

    @ManyToMany

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
