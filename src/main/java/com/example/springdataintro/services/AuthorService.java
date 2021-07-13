package com.example.springdataintro.services;

import com.example.springdataintro.models.entities.Author;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<String> getAllAuthorsOrderByCountOfTheirBooks();

    List<Author> getAuthorNamesEndingWithCriteria(String criteria);
}
