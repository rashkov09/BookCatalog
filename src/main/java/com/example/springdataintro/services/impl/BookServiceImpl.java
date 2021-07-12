package com.example.springdataintro.services.impl;

import com.example.springdataintro.models.entities.*;
import com.example.springdataintro.repositories.BookRepository;
import com.example.springdataintro.services.AuthorService;
import com.example.springdataintro.services.BookService;
import com.example.springdataintro.services.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final static String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;

        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0){
            return;
        }
        Files.readAllLines(Path.of(BOOKS_FILE_PATH)).stream().filter(s->!s.isEmpty())
                .forEach(row -> {
            String[] data = row.split("\\s+");

            Book book = createBookFromData(data);
            bookRepository.save(book);
        });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year,12,31));
    }

    @Override
    public List<String> findAllBooksBeforeYear(int year) {
        return bookRepository.findAllByReleaseDateBefore(LocalDate.of(year,1,1))
                .stream().map(book -> String.format("%s %s",book.getAuthor().getFirstName(),book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName,String lastName) {
        return bookRepository.findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName,lastName)
                .stream()
                .map(book -> String.format("%s %s %d",book.getTitle(),book.getReleaseDate(),book.getCopies()))
                .collect(Collectors.toList());
    }


    private Book createBookFromData(String[] data) {
        EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];
        LocalDate releaseDate = LocalDate.parse(data[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(data[2]);
        BigDecimal price = new BigDecimal(data[3]);
        AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(data[4])];
        String title= Arrays.stream(data)
                .skip(5)
                .collect(Collectors.joining(" "));
        Author author =  authorService.getRandomAuthor();
        Set<Category> categories = categoryService.getRandomCategories();
        return new Book(title,editionType,price,releaseDate,ageRestriction,author,categories,copies);
    }
}
