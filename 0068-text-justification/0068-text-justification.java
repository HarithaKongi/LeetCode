class Solution {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        int i = 0;
        int n = words.length;
        while (i < n) {
            int j = i;
            int letters = 0;
            // Find how many words can fit in one line
            while (j < n && letters + words[j].length() + (j - i) <= maxWidth) {
                letters += words[j].length();
                j++;
            }
            int spaces = maxWidth - letters;
            int gaps = j - i - 1;
            StringBuilder line = new StringBuilder();
            // Last line or only one word
            if (j == n || gaps == 0) {
                for (int k = i; k < j; k++) {
                    line.append(words[k]);
                    if (k < j - 1) {
                        line.append(" ");
                    }
                }
                while (line.length() < maxWidth) {
                    line.append(" ");
                }
            }
            // Fully justify the line
            else {
                int eachSpace = spaces / gaps;
                int extraSpace = spaces % gaps;
                for (int k = i; k < j; k++) {
                    line.append(words[k]);
                    if (k < j - 1) {
                        int count = eachSpace;
                        if (extraSpace > 0) {
                            count++;
                            extraSpace--;
                        }
                        for (int x = 0; x < count; x++) {
                            line.append(" ");
                        }
                    }
                }
            }
            result.add(line.toString());
            i = j;
        }
        return result;
    }
}
