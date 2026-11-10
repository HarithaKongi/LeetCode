class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {

        // Both are empty
        if (p == null && q == null) {
            return true;
        }

        // One is empty
        if (p == null || q == null) {
            return false;
        }

        // Values are different
        if (p.val != q.val) {
            return false;
        }

        // Check left and right sides
        return isSameTree(p.left, q.left) &&
               isSameTree(p.right, q.right);
    }
}
