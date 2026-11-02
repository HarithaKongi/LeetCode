
class Solution {
    public int minMoves2(int[] nums) {

        // Step 1: Sort the array
        Arrays.sort(nums);

        // Step 2: Find the median
        int median = nums[nums.length / 2];

        // Step 3: Calculate total moves
        int moves = 0;

        for (int i = 0; i < nums.length; i++) {
            moves += Math.abs(nums[i] - median);
        }

        return moves;
    }
}
