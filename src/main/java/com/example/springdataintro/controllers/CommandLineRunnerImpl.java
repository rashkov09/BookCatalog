package com.example.springdataintro.controllers;

import com.example.springdataintro.models.entities.AgeRestriction;
import com.example.springdataintro.models.entities.Book;
import com.example.springdataintro.models.entities.EditionType;
import com.example.springdataintro.services.AuthorService;
import com.example.springdataintro.services.BookService;
import com.example.springdataintro.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.swing.text.DateFormatter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("ALL")
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

        while (true) {
            try {
                System.out.println();
                System.out.print("1. Books Titles by Age Restriction\n" +
                        "2. Golden Books\n" +
                        "3. Books by Price\n" +
                        "4. Not Released Books\n" +
                        "5. Books Released Before Date\n" +
                        "6. Authors Search\n" +
                        "7. Books Search\n" +
                        "8. Book Titles Search\n" +
                        "9. Count Books\n" +
                        "10. Total Book Copies\n" +
                        "11. Reduced Book\n" +
                        "12. Increase Book Copies\n" +
                        "13. Remove Books\n" +
                        "14. Stored Procedure\n");
                System.out.println("Enter exercise number or 0 to EXIT: ");
                int exNumber = Integer.parseInt(reader.readLine());
                if (exNumber == 0) {
                    break;
                }

                switch (exNumber) {
                    case 1 -> exOne();
                    case 2 -> exTwo();
                    case 3 -> exThree();
                    case 4 -> exFour();
                    case 5 -> exFive();
                    case 6 -> exSix();
                    case 7 -> exSeven();
                    case 8 -> exEight();
                    case 9 -> exNine();
                    case 10 -> exTen();
                    case 11 -> exEleven();
                    case 12 -> exTwelve();
                    case 13 -> exThirteen();
                    case 14 -> exFourteen();
                    default -> System.out.println("Wrong exercise number. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Something went wrong, please try again!");
            }
        }

    }

    private void exFourteen() throws IOException {
        /**
         * The stored procedure
         *
         * BEGIN
         * SELECT COUNT(b.id)
         * FROM books AS b
         * JOIN	authors AS a
         * ON a.id =b.author_id
         * WHERE a.first_name = f_name AND a.last_name = l_name;
         * END
         */

        System.out.print("Enter author first name: ");
        String firstName = reader.readLine();
        System.out.print("Enter author last name: ");
        String lastName = reader.readLine();
        System.out.printf("%s %s has written %d books", firstName, lastName, bookService.getCountOfBooksByAuthor(firstName, lastName));
    }

    private void exThirteen() throws IOException {
        System.out.print("Enter number of copies: ");
        int copies = Integer.parseInt(reader.readLine());
        System.out.println(bookService.removeBooksWithCopiesLowerThanInput(copies));
    }

    private void exTwelve() throws IOException {
        System.out.print("Enter date in format (dd MMM yyyy): ");
        Locale.setDefault(Locale.US);
        String date = reader.readLine();
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd MMM yyyy"));
        System.out.print("Enter number of copies to add: ");
        Integer bookCopiesIncrease = Integer.parseInt(reader.readLine());
        System.out.println(bookService.increaseBookCopiesForBooksAfterYearAndGetTotal(localDate, bookCopiesIncrease));


    }

    private void exEleven() throws IOException {
        System.out.print("Enter book title: ");
        String title = reader.readLine();
        System.out.println(bookService.findBookByTitle(title));
    }

    private void exTen() {
        authorService.getTotalBookCopiesByAuthor()
                .stream()
                .sorted(Comparator.reverseOrder())
                .map(author -> String.format("%s %s - %d", author.getFirstName(), author.getLastName(),
                        author.getBooks().stream().map(Book::getCopies).mapToInt(Integer::new).sum())).forEach(System.out::println);
    }

    private void exNine() throws IOException {
        System.out.print("Enter book title name length: ");
        int length = Integer.parseInt(reader.readLine());
        System.out.printf("There are %d books with longer title than %d symbols\n",
                bookService.findBooksWithTitleLengthGreaterThan(length), length);

    }

    private void exEight() throws IOException {
        System.out.print("Enter author last name search criteria: ");
        String criteria = reader.readLine();
        bookService.findBooksByAuthorLastNameStartsWithCriteria(criteria)
                .stream()
                .map(book -> String.format("%s (%s %s)", book.getTitle(), book.getAuthor().getFirstName(), book.getAuthor().getLastName()))
                .forEach(System.out::println);
    }

    private void exSeven() throws IOException {
        System.out.print("Enter book search criteria: ");
        String criteria = reader.readLine();
        bookService.getBookTitlesWhichContainCriteria(criteria)
                .stream()
                .map(book -> String.format("%s", book.getTitle()))
                .forEach(System.out::println);
    }

    private void exSix() throws IOException {
        System.out.print("Enter author name search criteria: ");
        String criteria = reader.readLine();
        authorService.getAuthorNamesEndingWithCriteria(criteria)
                .stream()
                .map(author -> String.format("%s %s", author.getFirstName(), author.getLastName()))
                .forEach(System.out::println);
    }

    private void exFive() throws IOException {
        System.out.print("Enter date: ");
        int[] dateData = Arrays.stream(reader.readLine().split("-")).mapToInt(Integer::parseInt).toArray();
        LocalDate localDate = LocalDate.of(dateData[2], dateData[1], dateData[0]);
        bookService.findBooksReleasedBeforeDate(localDate)
                .stream()
                .map(book -> String.format("%s %s %.2f", book.getTitle(), book.getEditionType(), book.getPrice()))
                .forEach(System.out::println);
    }

    private void exFour() throws IOException {
        System.out.print("Enter release year: ");
        int year = Integer.parseInt(reader.readLine());
        bookService.findBooksNotReleasedInYear(LocalDate.of(year, 1, 1), LocalDate.of(year, 12, 31))
                .stream()
                .map(book -> String.format("%s", book.getTitle()))
                .forEach(System.out::println);
    }

    private void exThree() {
        bookService.findBookByPrice(BigDecimal.valueOf(5), BigDecimal.valueOf(40))
                .stream()
                .map(book -> String.format("%s - $%.2f", book.getTitle(), book.getPrice()))
                .forEach(System.out::println);
    }

    private void exTwo() {
        bookService.findBooksWithGoldEditionTypeAndNumberOfCopies(EditionType.valueOf("GOLD"), 5000)
                .stream()
                .map(book -> String.format("%s", book.getTitle()))
                .forEach(System.out::println);
    }

    private void exOne() throws IOException {

        System.out.print("Enter age restriction: ");
        String ageRestriction = reader.readLine().toUpperCase();
        bookService.findBooksByAgeRestriction(AgeRestriction.valueOf(ageRestriction))
                .stream()
                .map(book -> String.format("%s", book.getTitle()))
                .forEach(System.out::println);


    }


    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
