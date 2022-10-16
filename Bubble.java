import java.util.Arrays;
 
public class Bubble {
    public static void main(String[] args) {
        int [] m = {1, 2, 6, 10, 3, 7, 4, 8, 5, 3, 9};
 
        boolean Sorting = false;
        int buffer;
        while(!Sorting) {
            Sorting = true;
            for (int i = 0; i < m.length-1; i++) {
                if(m[i] > m[i+1]){
                    Sorting = false;
 
                    buffer = m[i];
                    m[i] = m[i+1];
                    m[i+1] = buffer;
                }
            }
        }
        System.out.println(Arrays.toString(m));
    }
}
