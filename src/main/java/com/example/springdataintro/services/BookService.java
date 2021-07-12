package com.example.springdataintro.services;

import com.example.springdataintro.models.entities.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {

    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);
    List<String> findAllBooksBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName,String lastName);
}
