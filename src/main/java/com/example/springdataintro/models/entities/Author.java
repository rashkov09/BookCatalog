package com.example.springdataintro.models.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "authors")
public class Author extends BaseEntity implements Comparable<Author>{
    private String firstName;
    private String lastName;
    private Set<Book> books;

    public Author() {
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }




    @Column(name = "first_name")

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name",nullable = false)

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }



    @Override
    public int compareTo(Author o) {
        Integer bookCount = this.getBooks().stream().map(Book::getCopies).mapToInt(Integer::new).sum();
        Integer otherCount = o.getBooks().stream().map(Book::getCopies).mapToInt(Integer::new).sum();

        return bookCount.compareTo(otherCount);
    }
}
