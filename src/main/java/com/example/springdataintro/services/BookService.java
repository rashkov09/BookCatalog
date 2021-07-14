package com.example.springdataintro.services;

import com.example.springdataintro.models.entities.AgeRestriction;
import com.example.springdataintro.models.entities.Book;
import com.example.springdataintro.models.entities.EditionType;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookService {

    void seedBooks() throws IOException;


    List<Book> findBooksByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findBooksWithGoldEditionTypeAndNumberOfCopies(EditionType gold, int i);

    List<Book> findBookByPrice(BigDecimal lower, BigDecimal upper);

    List<Book> findBooksNotReleasedInYear(LocalDate startOfYear, LocalDate endOfYear);

    List<Book> findBooksReleasedBeforeDate(LocalDate localDate);

    List<Book> getBookTitlesWhichContainCriteria(String criteria);

    List<Book> findBooksByAuthorLastNameStartsWithCriteria(String criteria);

    Integer findBooksWithTitleLengthGreaterThan(int length);


    String findBookByTitle(String title);

    Integer increaseBookCopiesForBooksAfterYearAndGetTotal(LocalDate localDate, Integer bookCopiesIncrease);

    Integer removeBooksWithCopiesLowerThanInput(int copies);

    Integer getCountOfBooksByAuthor(String firstName, String lastName);
}
