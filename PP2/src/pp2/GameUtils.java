package pp2;

import java.util.ArrayList;

/**
 * 
 * 
 */
public class GameUtils {

   
    public static void mergeSortPlayersByPoints(ArrayList<Player> list) {
        int n = list.size();
        if (n < 2) {
            return; 
        }
        
       
        int mid = n / 2;
        ArrayList<Player> left = new ArrayList<>(list.subList(0, mid));
        ArrayList<Player> right = new ArrayList<>(list.subList(mid, n));

       
        mergeSortPlayersByPoints(left);
        mergeSortPlayersByPoints(right);

       
        merge(list, left, right);
    }

    
    private static void merge(ArrayList<Player> list, ArrayList<Player> left, ArrayList<Player> right) {
        int i = 0, j = 0, k = 0;
        
       
        while (i < left.size() && j < right.size()) {
            if (left.get(i).getPuntos() >= right.get(j).getPuntos()) { // >= para orden descendente
                list.set(k++, left.get(i++));
            } else {
                list.set(k++, right.get(j++));
            }
        }
        
      
        while (i < left.size()) {
            list.set(k++, left.get(i++));
        }
        
       
        while (j < right.size()) {
            list.set(k++, right.get(j++));
        }
    }
}