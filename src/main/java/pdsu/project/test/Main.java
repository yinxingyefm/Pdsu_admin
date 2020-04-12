package pdsu.project.test;

import pdsu.project.utils.Utils;

import java.util.List;

/**
 * @author ljk
 */
public class Main {
    public static void main(String[] args) {
        String  s="北京 昌平区";
        List<String> areea = Utils.Areea(s);
        System.out.println(areea);
//        int i = s.indexOf(" ");
//        String substring = s.substring(0, i);
//        System.out.println(substring);
//        String substring1 = s.substring(i + 1, s.length());
//        System.out.println(substring1);

    }


}
