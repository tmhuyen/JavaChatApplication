import java.lang.reflect.Array;
import java.util.*;

public class Test {
    public static boolean checkDiffUserList(ArrayList<String> list1, ArrayList<String> list2) {
        Set<String> set1 = new HashSet<>(list1);
        Set<String> set2 = new HashSet<>(list2);
        if (set1.size() != set2.size())
            return true;
        for (String s : set1) {
            if (!set2.contains(s))
                return true;
        }
        return false;
    }
    public static void main(String[] args) {
        ArrayList<String> list1 = new ArrayList<>(Arrays.asList("a", "b", "c"));
        ArrayList<String> list2 = new ArrayList<>(Arrays.asList("a", "a", "c"));
        System.out.println(checkDiffUserList((ArrayList<String>) list1, (ArrayList<String>) list2));
    }
}
