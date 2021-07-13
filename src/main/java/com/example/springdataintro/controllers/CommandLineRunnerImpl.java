package com.example.springdataintro.controllers;

import com.example.springdataintro.models.entities.AgeRestriction;
import com.example.springdataintro.models.entities.Book;
import com.example.springdataintro.models.entities.EditionType;
import com.example.springdataintro.services.AuthorService;
import com.example.springdataintro.services.BookService;
import com.example.springdataintro.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        while(true){
            System.out.println();
            System.out.println("Enter exercise number or 0 to EXIT: ");
            int exNumber = Integer.parseInt(reader.readLine());
            if (exNumber == 0){
                break;
            }
            switch (exNumber){
                case 1 -> exOne();
                case 2 -> exTwo();
                case 3 -> exThree();
                case 4-> exFour();
                case 5 -> exFive();
                case 6 -> exSix();
                case 7 -> exSeven();
                case 8 -> exEight();
                case 9 -> exNine();
                case 10 -> exTen();
                default -> System.out.println("Wrong exercise number. Please try again.");
            }
        }

    }

    private void exTen() {

    }

    private void exNine() throws IOException {
        System.out.print("Enter book title name length: ");
        int length = Integer.parseInt(reader.readLine());
        System.out.printf("There are %d books with longer title than %d symbols\n",
                bookService.findBooksWithTitleLengthGreaterThan(length),length);

    }

    private void exEight() throws IOException {
        System.out.print("Enter author last name search criteria: ");
        String criteria = reader.readLine();
        bookService.findBooksByAuthorLastNameStartsWithCriteria(criteria)
        .stream()
        .map(book -> String.format("%s (%s %s)",book.getTitle(),book.getAuthor().getFirstName(),book.getAuthor().getLastName()))
        .forEach(System.out::println);
    }

    private void exSeven() throws IOException {
        System.out.print("Enter book search criteria: ");
        String criteria = reader.readLine();
        bookService.getBookTitlesWhichContainCriteria(criteria)
        .stream()
        .map(book -> String.format("%s",book.getTitle()))
        .forEach(System.out::println);
    }

    private void exSix() throws IOException {
        System.out.print("Enter author name search criteria: ");
        String criteria = reader.readLine();
        authorService.getAuthorNamesEndingWithCriteria(criteria)
        .stream()
        .map(author -> String.format("%s %s",author.getFirstName(),author.getLastName()))
        .forEach(System.out::println);
    }

    private void exFive() throws IOException {
        System.out.print("Enter date: ");
        int[] dateData = Arrays.stream(reader.readLine().split("-")).mapToInt(Integer::parseInt).toArray();
        LocalDate localDate = LocalDate.of(dateData[2],dateData[1],dateData[0]);
        bookService.findBooksReleasedBeforeDate(localDate)
                .stream()
                .map(book -> String.format("%s %s %.2f",book.getTitle(),book.getEditionType(),book.getPrice()))
                .forEach(System.out::println);
    }

    private void exFour() throws IOException {
        System.out.print("Enter release year: ");
        int year = Integer.parseInt(reader.readLine());
        bookService.findBooksNotReleasedInYear(LocalDate.of(year,1,1),LocalDate.of(year,12,31))
                .stream()
                .map(book -> String.format("%s",book.getTitle()))
                .forEach(System.out::println);
    }

    private void exThree() {
        bookService.findBookByPrice(BigDecimal.valueOf(5),BigDecimal.valueOf(40))
        .stream()
        .map(book -> String.format("%s - $%.2f",book.getTitle(),book.getPrice()))
        .forEach(System.out::println);
    }

    private void exTwo() {
        bookService.findBooksWithGoldEditionTypeAndNumberOfCopies(EditionType.valueOf("GOLD"),5000)
        .stream()
        .map(book -> String.format("%s",book.getTitle()))
        .forEach(System.out::println);
    }

    private void exOne() throws IOException {

        System.out.print("Enter age restriction: ");
        String ageRestriction = reader.readLine().toUpperCase();
        bookService.findBooksByAgeRestriction(AgeRestriction.valueOf(ageRestriction))
                .stream()
                .map(book -> String.format("%s",book.getTitle()))
        .forEach(System.out::println);


    }


    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
