package com.example.springdataintro.repositories;

import com.example.springdataintro.models.entities.Author;
import com.example.springdataintro.models.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
    @Query("SELECT a FROM Author AS a ORDER BY a.books.size desc")
    List<Author> findAllByBooksSizeDESC();

    List<Author> findAuthorsByFirstNameEndingWith(String criteria);
}
