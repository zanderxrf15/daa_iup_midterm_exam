package mytree;

// AVL Tree = Height-Balanced (HB) Tree

public class DAA2 extends DAA1 {

	// 4. isHeightBalanced() [10 points]
	public static boolean isHeightBalanced(MyTree t) {
	    // Check if current node is empty
	    if (t.getEmpty()) {
	        // If current node is empty, it is considered balanced
	        return true;
	    }

	    // Calculate height of left and right subtrees
	    int leftHeight = height(t.getLeft());
	    int rightHeight = height(t.getRight());

	    // Check if difference in heights between left and right subtrees is greater than 1
	    if (Math.abs(leftHeight - rightHeight) > 1) {
	        // If difference in heights is greater than 1, tree is unbalanced
	        return false;
	    }

	    // Recursively check if both left and right subtrees are height balanced
	    return isHeightBalanced(t.getLeft()) && isHeightBalanced(t.getRight());
	}

	
	// 5. insertHB() [10 points]
	public static MyTree insertHB(int n, MyTree t) {
	    if (t.getEmpty()) {
	        // If the tree is empty, insert the value as the root
	        return new MyTree(n, new MyTree(), new MyTree());
	    } 
	    else if (n < t.getValue()) {
	        // Insert the value into the left subtree
	        MyTree newLeftSubtree = insertHB(n, t.getLeft());
	        t = new MyTree(t.getValue(), newLeftSubtree, t.getRight());
	    } 
	    else if (n > t.getValue()) {
	        // Insert the value into the right subtree
	        MyTree newRightSubtree = insertHB(n, t.getRight());
	        t = new MyTree(t.getValue(), t.getLeft(), newRightSubtree);
	    }
	    
	    // Calculate the balance factor of the tree
	    int balanceFactor = balanceFactor(t);
	    
	    // Rebalance the tree if necessary
	    if (balanceFactor > 1 && n < t.getLeft().getValue()) {
	        return rebalanceForRight(t);
	    } 
	    else if (balanceFactor > 1 && n > t.getLeft().getValue()) {
	        t = new MyTree(t.getValue(), rebalanceForLeft(t.getLeft()), t.getRight());
	        return rebalanceForRight(t);
	    } 
	    else if (balanceFactor < -1 && n > t.getRight().getValue()) {
	        return rebalanceForLeft(t);
	    } 
	    else if (balanceFactor < -1 && n < t.getLeft().getValue()) {
	        t = new MyTree(t.getValue(), rebalanceForRight(t.getLeft()), t.getRight());
	        return rebalanceForLeft(t);
	    }
	    
	    // Return the updated tree
	    return t;
	}



	// rebalanceForLeft is called when the left subtree of t may have
	// grown taller by one notch.
	// If it is indeed taller than the right subtree by two notches,
	// return a height-balanced version of t using single or double rotations.
	// The subtrees of t are assumed to be already height-balanced and
	// no effort is made to rebalance them.
	//
	// Likewise, for the case of the right subtree -> rebalanceForRight
	// Both rebalanceForLeft & rebalanceForRight will be used by insertHB() and deleteHB()
	
	// 6. rebalanceForLeft() [15 points]	
	private static MyTree rebalanceForLeft(MyTree t) {
	    // Store right child of current node as new parent
	    MyTree newParent = t.getRight();
	    
	    // Update current node to become left child of new parent
	    t = new MyTree(t.getValue(), t.getLeft(), newParent.getLeft());
	    
	    // Update new parent to replace left child with updated current node
	    newParent = new MyTree(newParent.getValue(), t, newParent.getRight());
	    
	    // Return new parent after rebalancing
	    return newParent;
	}

	
	// 7. rebalanceForRight() [15 points]	
	private static MyTree rebalanceForRight(MyTree t) {
	    // Store left child of the current node as new parent
	    MyTree newParent = t.getLeft();
	    
	    // Update current node to become right child of new parent
	    t = new MyTree(t.getValue(), t.getLeft(), newParent.getRight());
	    
	    // Update new parent to replace right child with updated current node
	    newParent = new MyTree(newParent.getValue(), newParent.getLeft(), t);
	    
	    // Return new parent after rebalancing
	    return newParent;
	}

	
	// 8. deleteHB() [10 points]
	/**
	 * Deletes the value 'x' from the given tree, if it exists, and returns the new
	 * Tree.
	 *
	 * Otherwise, the original tree will be returned.
	 */

	public static MyTree deleteHB(MyTree t, int x) {
	    // If current node is empty, return it
	    if (t.getEmpty()) {
	        return t;
	    }

	    // If value to delete is greater than current node's value, delete from right subtree
	    if (x > t.getValue()) {
	        t = new MyTree(t.getValue(), t.getLeft(), deleteHB(t.getRight(), x));
	    } 
	    // If value to delete is less than current node's value, delete from left subtree
	    else if (x < t.getValue()) {
	        t = new MyTree(t.getValue(), deleteHB(t.getLeft(), x), t.getRight());
	    } 
	    // If value to delete is found at current node
	    else {
	        // If left child is empty, replace current node with right child
	        if (t.getLeft().getEmpty()) {
	            t = t.getRight();
	        } 
	        // If right child is empty, replace current node with left child
	        else if (t.getRight().getEmpty()) {
	            t = t.getLeft();
	        } 
	        // If both children are present
	        else {
	            // Find node with smallest value in right subtree (most left)
	            MyTree mostLeft = findMostLeft(t.getRight());
	            // Replace current node with most left node, and delete it from right subtree
	            t = new MyTree(mostLeft.getValue(), t.getLeft(), deleteHB(t.getRight(), mostLeft.getValue()));
	        }
	    }

	    // If tree becomes empty after deletion, return it
	    if (t.getEmpty()) {
	        return t;
	    }

	    // Calculate balance factor of the tree after deletion
	    int balance = balanceFactor(t);

	    // Rebalance tree based on balance factor
	    if (balance > 1 && balanceFactor(t.getLeft()) >= 0) {
	        return rebalanceForRight(t);
	    }
	    if (balance > 1 && balanceFactor(t.getLeft()) < 0) {
	        t = new MyTree(t.getValue(), rebalanceForLeft(t.getLeft()), t.getRight());
	        return rebalanceForRight(t);
	    }
	    if (balance < -1 && balanceFactor(t.getRight()) <= 0) {
	        return rebalanceForLeft(t);
	    }
	    if (balance < -1 && balanceFactor(t.getRight()) > 0) {
	        t = new MyTree(t.getValue(), t.getLeft(), rebalanceForRight(t.getRight())); //right left
	        return rebalanceForLeft(t);
	    }

	    // Return tree after deletion and rebalancing
	    return t;
	}

	private static int height(MyTree t) {
	    // Check if current node is empty
	    if (t.getEmpty()) {
	        // If current node is empty, return 0 indicating a height of 0
	        return 0;
	    }

	    // Recursively calculate height of subtree
	    // Height of subtree is 1 + maximum height of left and right subtrees
	    return 1 + Math.max(height(t.getLeft()), height(t.getRight()));
	}


	private static int balanceFactor(MyTree t) {
	    // Check if current node is empty
	    if (t.getEmpty()) {
	        // If current node is empty, return 0 indicating balanced
	        return 0;
	    }

	    // Calculate balance factor by subtracting height of right subtree from height of left subtree
	    return height(t.getLeft()) - height(t.getRight());
	}

	private static MyTree findMostLeft(MyTree t) {
	    // Initialize currentNode with given tree
	    MyTree currentNode = t;

	    // Traverse left subtree until leftmost node is found
	    while (!currentNode.getLeft().getEmpty()) {
	        // Update currentNode to left child, keeping its value and right child intact
	        currentNode = new MyTree(currentNode.getLeft().getValue(), currentNode.getLeft().getLeft(), currentNode.getLeft().getRight());
	    }

	    // Return leftmost node found
	    return currentNode;
	}
}