package com.example.springdataintro.services.impl;

import com.example.springdataintro.models.entities.Author;
import com.example.springdataintro.repositories.AuthorRepository;
import com.example.springdataintro.services.AuthorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final static String AUTHORS_FILE_PATH = "src/main/resources/files/authors.txt";

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (authorRepository.count() > 0){
            return;
        }
        Files.readAllLines(Path.of(AUTHORS_FILE_PATH))
                .stream()
                .filter(a -> !a.isEmpty())
                .forEach(authorFullName -> {
                    Author author = new Author(authorFullName.split(" ")[0], authorFullName.split(" ")[1]);
                    authorRepository.save(author);
                });
    }

    @Override
    public Author getRandomAuthor() {
        long randomNum = ThreadLocalRandom.current().nextLong(1,authorRepository.count()+1);
        return authorRepository.findById(randomNum).orElse(null);
    }

    @Override
    public List<String> getAllAuthorsOrderByCountOfTheirBooks() {
        return authorRepository
                .findAllByBooksSizeDESC()
                .stream()
                .map(author -> String.format("%s %s %d",author.getFirstName(),author.getLastName(),author.getBooks().size()))
                .collect(Collectors.toList());
    }
}
