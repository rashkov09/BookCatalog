package com.example.springdataintro.repositories;

import com.example.springdataintro.models.entities.AgeRestriction;
import com.example.springdataintro.models.entities.Book;
import com.example.springdataintro.models.entities.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
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

 }
