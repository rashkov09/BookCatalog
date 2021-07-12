package com.example.springdataintro.controllers;

import com.example.springdataintro.models.entities.Book;
import com.example.springdataintro.services.AuthorService;
import com.example.springdataintro.services.BookService;
import com.example.springdataintro.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
//        printAllBooksAfterYear(2000);
//        printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
//        printAllAuthorsAndNumberOfTheirBooks();
        printAllBooksByAuthorNameOrderByReleaseDate("George","Powell");

    }

    private void printAllBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService.findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName,lastName).forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService.getAllAuthorsOrderByCountOfTheirBooks().forEach(System.out::println);
    }


    private void printAllBooksAfter2000(int year) {
        bookService.findAllBooksAfterYear(year)
        .stream().map(Book::getTitle)
        .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService.findAllBooksBeforeYear(year)
                .forEach(System.out::println);
    }


    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
