package com.fges.dao;

import com.fges.data_structures.GroceryList;
import java.io.IOException;

public interface GroceryDAO {
    GroceryList load() throws IOException;
    void save(GroceryList list) throws IOException;
}
