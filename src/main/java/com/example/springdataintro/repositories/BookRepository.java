package com.example.springdataintro.repositories;

import com.example.springdataintro.models.entities.AgeRestriction;
import com.example.springdataintro.models.entities.Book;
import com.example.springdataintro.models.entities.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findBookByAgeRestrictionIs(AgeRestriction ageRestriction);
    List<Book> findBooksByEditionTypeAndCopiesLessThan(EditionType editionType,int copies);
    List<Book> findBookByPriceLessThanOrPriceGreaterThan(BigDecimal lower, BigDecimal upper);
    List<Book> findBooksByReleaseDateBeforeOrReleaseDateAfter(LocalDate startOfYear,LocalDate endOfYear);
    List<Book> findBookByReleaseDateBefore(LocalDate date);
    List<Book> findBooksByTitleContains(String criteria);

    @Query("SELECT b FROM Book AS b JOIN b.author AS a WHERE a.lastName LIKE ?1%")
    List<Book> findBooksByAuthorLastNameContains(String criteria);

    @Query("SELECT b FROM Book AS b WHERE length(b.title) > ?1")
    List<Book> findBooksByTitleGreaterThan(Integer length);


 }
