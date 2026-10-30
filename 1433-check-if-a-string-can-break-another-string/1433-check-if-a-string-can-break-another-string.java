import java.util.Arrays;

class Solution {
    public boolean checkIfCanBreak(String s1, String s2) {

        char[] a = s1.toCharArray();
        char[] b = s2.toCharArray();

        Arrays.sort(a);
        Arrays.sort(b);

        boolean aBreaksB = true;
        boolean bBreaksA = true;

        for (int i = 0; i < a.length; i++) {

            if (a[i] < b[i]) {
                aBreaksB = false;
            }

            if (b[i] < a[i]) {
                bBreaksA = false;
            }
        }

        return aBreaksB || bBreaksA;
    }
}