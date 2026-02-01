package com.mketsyrof.budget_tracker.web;

import com.mketsyrof.budget_tracker.business.CategoryService;
import com.mketsyrof.budget_tracker.model.Category;
import com.mketsyrof.budget_tracker.model.CategoryType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(path = "/api/categories")
public class CategoryController {
    private static final String API_PATH_FOR_LOGS = "/api/categories";

    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategoriesOfGivenType(@RequestParam(name = "type", required = false) CategoryType type) {
        if (type == null) {
            log.info("GET " + API_PATH_FOR_LOGS);
            return categoryService.getAllSorted();
        }

        log.info("GET " + API_PATH_FOR_LOGS + "?type={}", type);
        return categoryService.getAllByTypeSorted(type);
    }
}
