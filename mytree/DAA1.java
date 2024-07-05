package mytree;

public class DAA1 extends MyTree {

	// Binary Search Tree (BST)
	// 1. isBST() [20 points]
	public static boolean isBST(MyTree t) {
		// Call the helper function with the entire range of integers as the bounds
        return isBST(t, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	// Helper function for isBST
	// Get a boolean value to know whether 't' is BST (Binary Search Tree)
	// whose values are within the range between lowerBound and upperBound
	private static boolean isBST(MyTree t, int lowerBound, int upperBound){
		 // If tree is null, it is a BST
        if (t == null) {
            return true;
        }

        // Check if current node's value is within the bounds
        if (t.value < lowerBound || t.value > upperBound) {
            return false;
        }

        // Recursively check left and right subtrees with updated bounds
        return isBST(t.left, lowerBound, t.value - 1) && isBST(t.right, t.value + 1, upperBound);
    }

	// 2. printDescending() [10 points]
	public static void printDescending(MyTree t) {
		 // If tree is null, return without printing
	    if (t == null) {
	        return;
	    }

	    // Visit right subtree (descending order)
	    printDescending(t.right);

	    // Print current node's value
	    if (t.value == Integer.MIN_VALUE) {
	        System.out.print("0 ");
	    } 
	    else {
	        System.out.print(t.value + " ");
	    }

	    // Visit left subtree (descending order)
	    printDescending(t.left);
	}
	
	// 3. max() [10 points]
	/**
	 * You have to: - handle empty trees - never look at left - never compares
	 * values, i.e., the value of t and the right, because it's not necessary if
	 * it's BST. - returns the right value as soon as found
	 *
	 * @param t is the tree being searched for the max value
	 * @return the max value of tree t
	 */
	 public static int max(MyTree t) {
		    // if tree is null, return sentinel value indicating no maximum found
		    if (t == null) {
		        return Integer.MIN_VALUE; // Using smallest integer value as sentinel
		    }

		    // First, find maximum value in the right subtree
		    int maxInRightSubtree = max(t.right);

		    // If the current node has a right child and the maximum value in the right subtree is greater than the current node's value,
		    // maximum value is in right subtree
		    if (t.right != null && maxInRightSubtree > t.value) {
		        return maxInRightSubtree;
		    }

		    // If no right child exists or the current node's value is greater than or equal to the maximum value in the right subtree,
		    // current node is the maximum value
		    return t.value;
		}
}