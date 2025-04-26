package com.fges.data_structures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GroceryList {
    private List<Item> listeObjets;

    public GroceryList() {
        this.listeObjets = new ArrayList<>();
    }

    public List<Item> getListeObjets() {
        return listeObjets;
    }

    public void setListeObjets(List<Item> liste_objets) {
        this.listeObjets = liste_objets;
    }

    public void addItem(Item item) {
        this.listeObjets.add(item);
    }

    public boolean removeItemByName(String name) {
        // Supprime un élément selon un nom donné
        Iterator<Item> iterator = listeObjets.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getName().equalsIgnoreCase(name)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public int findElementIndex(String name, String category){
        // Trouve l'index de l'objet correspondant au nom et à la catégorie en paramètre
        int listSize = this.listeObjets.size();

        for(int index = 0; index < listSize; index++){
            if(listeObjets.get(index).getName().equals(name) && listeObjets.get(index).getCategory().equals(category)){
                return index;
            }
        }

        return -1;
    }
}