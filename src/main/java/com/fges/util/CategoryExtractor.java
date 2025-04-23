package com.fges.util;

import com.fges.data_structures.Item;
import java.util.ArrayList;
import java.util.List;

public class CategoryExtractor {
    private final List<Item> items;

    public CategoryExtractor(List<Item> items) {
        this.items = items;
    }

    /**
     * Extrait toutes les catégories uniques d'une liste d'articles
     */
    public List<String> extractCategories() {
        List<String> categories = new ArrayList<>();

        for (Item item : items) {
            String category = item.getCategory();
            if (!categories.contains(category)) {
                categories.add(category);
            }
        }

        return categories;
    }
}