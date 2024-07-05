package mytree;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * MyTree class defines a recursive type called MyTree (for Binary Trees), and
 * provides constructor and getter methods.
 */
public class MyTree {

	/**
	 * MyTree class defines a recursive type called MyTree, and provides constructor
	 * and accessor methods.
	 */

	protected final boolean empty;
	protected final int value;
	protected final MyTree left, right;

	/**
	 * Creates a new tree with root value and left and right subtrees.
	 */
	public MyTree(int value, MyTree left, MyTree right) {
		this.empty = false;
		this.value = value;
		this.left = left;
		this.right = right;
	}

	/**
	 * Creates an empty tree
	 */
	public MyTree() {
		this.empty = true;
		this.value = 0;
		this.left = null;
		this.right = null;
	}

	/**
	 * Creates a tree with a single leaf node
	 */

	public MyTree(int x) {
		this.empty = false;
		this.value = x;
		this.left = new MyTree();
		this.right = new MyTree();

	}

	/**
	 * returns true if this tree is empty (e.g., nil)
	 */
	public boolean getEmpty() {
		return empty;
	}

	/**
	 * gets the root value of this tree
	 */
	public int getValue() {
		if (empty) {
			throw new IllegalStateException("Trying to access root of an empty tree");
		}
		return value;
	}

	/**
	 * gets the left subtree of this node
	 */
	public MyTree getLeft() {
		if (empty) {
			throw new IllegalStateException("Trying to access subtree of an empty tree");
		}
		return left;
	}

	/**
	 * gets the right subtree of this node
	 */
	public MyTree getRight() {
		if (empty) {
			throw new IllegalStateException("Trying to access subtree of an empty tree");
		}
		return right;
	}

	/**
	 * Creates a multiline String that represents this tree. The format looks
	 * like this <code>
	10
	   |
	   |- 14
	   |   |
	   |   |- 17
	   |   |
	   |   |- 13
	   |       |
	   |       | - [nil]
	   |       |
	   |       |- 12
	   |
	   |- 6
	</code> Where the bottom child is the left sub tree and the top child is the
	 * right sub tree. If both children are nil or the empty tree then they will
	 * not be printed. If only one child is nil then both children are printed to so
	 * it can be known which was the right child and which was the left child.
	 * 
	 * @param MyTree The tree, which may not be null
	 * @return A string containing the formatted tree
	 */
	@Override
	public String toString() {
		return format(this);
	}

	/**
	 * source (with modifications) http://www.connorgarvey.com/blog/?p=82 Print a
	 * formatted representation of the given tree. The format looks like this
	 * <code>
	10
	   |
	   |- 14
	   |   |
	   |   |- 17
	   |   |
	   |   |- 13
	   |       |
	   |       | - [nil]
	   |       |
	   |       |- 12
	   |
	   |- 6
	</code> Where the bottom child is the left sub tree and the top child is the
	 * right sub tree. If both children are nil or the empty tree then they will
	 * not be printed. If only one child is nil then both children are printed to so
	 * it can be known which was the right child and which was the left child.
	 * 
	 * @param MyTree The tree, which may not be null
	 * @return A string containing the formatted tree
	 */
	public static void print(MyTree MyTree) {
		System.out.print(format(MyTree));
	}

	public static String format(MyTree MyTree) {
		final StringBuilder buffer = new StringBuilder();
		return formatMyTreeHelper(MyTree, buffer, new LinkedList<Iterator<MyTree>>()).toString();
	}

	private static String formatMyTreeDrawLines(java.util.List<Iterator<MyTree>> parentIterators, boolean amLast) {
		StringBuilder result = new StringBuilder();
		Iterator<Iterator<MyTree>> it = parentIterators.iterator();
		while (it.hasNext()) {
			Iterator<MyTree> anIt = it.next();
			if (anIt.hasNext() || (!it.hasNext() && amLast)) {
				result.append("   |");
			} else {
				result.append("    ");
			}
		}
		return result.toString();
	}

	private static StringBuilder formatMyTreeHelper(MyTree t, StringBuilder buffer,
			java.util.List<Iterator<MyTree>> parentIterators) {

		if (!parentIterators.isEmpty()) {
			boolean amLast = !parentIterators.get(parentIterators.size() - 1).hasNext();
			String lines = formatMyTreeDrawLines(parentIterators, amLast);
			buffer.append("\n").append(lines).append("\n").append(lines).append("- ");
		}

		if (t.getEmpty()) {
			buffer.append("[nil]");
			return buffer;
		} else
			buffer.append(t.getValue());

		if (!(t.getLeft().getEmpty() && t.getRight().getEmpty())) {
			Iterator<MyTree> it = getChildrenIterator(t);
			parentIterators.add(it);
			while (it.hasNext()) {
				MyTree child = it.next();
				formatMyTreeHelper(child, buffer, parentIterators);
			}
			parentIterators.remove(it);
		}
		return buffer;
	}

	private static Iterator<MyTree> getChildrenIterator(MyTree t) {
		if (t.getEmpty())
			return Collections.<MyTree>emptyList().iterator();
		else
			return Arrays.asList(new MyTree[] { t.getRight(), t.getLeft() }).iterator();
	}

	@Override
	public boolean equals(Object o) {
		MyTree t = (MyTree) o;
		if (this.empty)
			return t.empty;
		else
			return !t.empty && this.value == t.value && this.left.equals(t.left) && this.right.equals(t.right);
	}

}
