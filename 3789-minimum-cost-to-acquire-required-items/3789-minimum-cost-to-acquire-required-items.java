
class Solution {
    public long minimumCost(int cost1, int cost2, int costBoth, int need1, int need2) {

        // Option 1: Buy only separate items
        long option1 = (long) need1 * cost1 + (long) need2 * cost2;

        // Option 2: Buy only type 3 items
        long option2 = (long) Math.max(need1, need2) * costBoth;

        // Option 3: Buy type 3 for common requirement
        // and separate items for remaining requirement
        long option3 = (long) Math.min(need1, need2) * costBoth;

        if (need1 > need2) {
            option3 += (long) (need1 - need2) * cost1;
        } else {
            option3 += (long) (need2 - need1) * cost2;
        }

        return Math.min(option1, Math.min(option2, option3));
    }
}
