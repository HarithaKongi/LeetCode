
class Solution {
    public int minMoves(int[] nums) {
        int max = nums[0];
        int moves = 0;

        // Find maximum element
        for (int i = 1; i < nums.length; i++) {
            max = Math.max(max, nums[i]);
        }

        // Calculate total moves
        for (int i = 0; i < nums.length; i++) {
            moves += max - nums[i];
        }

        return moves;
    }
}
