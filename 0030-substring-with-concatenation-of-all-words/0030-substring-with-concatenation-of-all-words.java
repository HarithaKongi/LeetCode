import java.util.*;

class Solution {
    public List<Integer> findSubstring(String s, String[] words) {

        List<Integer> ans = new ArrayList<>();

        int n = s.length();
        int wordLen = words[0].length();
        int wordCount = words.length;
        int totalLen = wordLen * wordCount;

        if (n < totalLen) {
            return ans;
        }

        // Store frequency of each word
        HashMap<String, Integer> map = new HashMap<>();

        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        // Try every possible starting offset
        for (int i = 0; i < wordLen; i++) {

            int left = i;
            int count = 0;

            HashMap<String, Integer> seen = new HashMap<>();

            for (int right = i; right + wordLen <= n; right += wordLen) {

                String word = s.substring(right, right + wordLen);

                // Word is not present in words
                if (!map.containsKey(word)) {
                    seen.clear();
                    count = 0;
                    left = right + wordLen;
                    continue;
                }

                seen.put(word, seen.getOrDefault(word, 0) + 1);
                count++;

                // Too many occurrences of this word
                while (seen.get(word) > map.get(word)) {

                    String leftWord = s.substring(left, left + wordLen);

                    seen.put(leftWord, seen.get(leftWord) - 1);

                    left += wordLen;
                    count--;
                }

                // All words matched
                if (count == wordCount) {
                    ans.add(left);

                    String leftWord = s.substring(left, left + wordLen);

                    seen.put(leftWord, seen.get(leftWord) - 1);

                    left += wordLen;
                    count--;
                }
            }
        }

        return ans;
    }
}
