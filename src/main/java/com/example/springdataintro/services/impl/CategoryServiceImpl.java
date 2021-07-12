package com.example.springdataintro.services.impl;

import com.example.springdataintro.models.entities.Category;
import com.example.springdataintro.repositories.CategoryRepository;
import com.example.springdataintro.services.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private static final String CATEGORIES_FILE_PATH = "src/main/resources/files/categories.txt";
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() > 0){
            return;
        }
        Files.readAllLines(Path.of(CATEGORIES_FILE_PATH))
                .stream()
                .filter(row->!row.isEmpty())
                .forEach(categoryName ->{
                    Category category = new Category(categoryName);
                    categoryRepository.save(category);
                });

    }

    @Override
    public Set<Category> getRandomCategories() {

      long categoryCount = ThreadLocalRandom.current().nextLong(1,3);
        Set<Category> categories = new HashSet<>();

        long catRepoCount = categoryRepository.count();
        for (int i = 0; i < categoryCount; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1,categoryCount+1);
            categories.add(categoryRepository.findById(randomId).orElse(null));
        }
        return categories;
    }
}
