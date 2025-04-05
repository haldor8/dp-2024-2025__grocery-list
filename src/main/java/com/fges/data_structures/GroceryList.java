package com.fges.data_structures;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GroceryList {
    @JsonProperty("liste_objets")
    private List<Item> liste_objets;

    public GroceryList() {
        this.liste_objets = new ArrayList<>();
    }

    public List<Item> getListeObjets() {
        return liste_objets;
    }

    public void setListeObjets(List<Item> liste_objets) {
        this.liste_objets = liste_objets;
    }

    public void addItem(Item item) {
        this.liste_objets.add(item);
    }

    public boolean removeItemByName(String name) {
        Iterator<Item> iterator = liste_objets.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getName().equalsIgnoreCase(name)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}
