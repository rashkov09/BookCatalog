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
    public List<Book> findBooksByAgeRestriction(AgeRestriction ageRestriction) {
       return bookRepository.findBookByAgeRestrictionIs(ageRestriction);
    }

    @Override
    public List<Book> findBooksWithGoldEditionTypeAndNumberOfCopies(EditionType gold, int copies) {

        return bookRepository.findBooksByEditionTypeAndCopiesLessThan(gold,copies);
    }

    @Override
    public List<Book> findBookByPrice(BigDecimal lower, BigDecimal upper) {
        return bookRepository.findBookByPriceLessThanOrPriceGreaterThan(lower,upper);
    }

    @Override
    public List<Book> findBooksNotReleasedInYear(LocalDate startOfYear, LocalDate endOfYear) {
        return bookRepository.findBooksByReleaseDateBeforeOrReleaseDateAfter(startOfYear,endOfYear);
    }

    @Override
    public List<Book> findBooksReleasedBeforeDate(LocalDate localDate) {
        return bookRepository.findBookByReleaseDateBefore(localDate);

    }

    @Override
    public List<Book> getBookTitlesWhichContainCriteria(String criteria) {
        return bookRepository.findBooksByTitleContains(criteria);
    }

    @Override
    public List<Book> findBooksByAuthorLastNameStartsWithCriteria(String criteria) {
        return bookRepository.findBooksByAuthorLastNameContains(criteria);
    }

    @Override
    public Integer findBooksWithTitleLengthGreaterThan(int length) {
        return  bookRepository.findBooksByTitleGreaterThan(length).size();
    }

    @Override
    public String findBookByTitle(String title) {
        Book book = bookRepository.findBookByTitle(title);
        return  String.format("%s %s %s %.2f",
                        book.getTitle(),
                        book.getEditionType(),
                        book.getAgeRestriction(),
                        book.getPrice());
    }

    @Override
    public Integer increaseBookCopiesForBooksAfterYearAndGetTotal(LocalDate localDate, Integer bookCopiesIncrease) {
        List<Book> books = bookRepository.findBooksByReleaseDateAfter(localDate);
        books.forEach(book -> {
            book.setCopies(book.getCopies()+bookCopiesIncrease);
            bookRepository.save(book);
        });
        return books.size()*bookCopiesIncrease;
    }

    @Override
    public Integer removeBooksWithCopiesLowerThanInput(int copies) {
        List<Book> books = bookRepository.findBooksByCopiesLessThan(copies);
        bookRepository.deleteAll(books);
        return books.size();
    }

    @Override
    public Integer getCountOfBooksByAuthor(String firstName, String lastName) {
        return bookRepository.getBookByAuthorFirstNameAndAuthorLastName(firstName,lastName);
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
