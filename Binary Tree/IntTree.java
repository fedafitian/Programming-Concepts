import java.util.*;

class IntTree {
	private IntTreeNode overallRoot;

	public IntTree(int[] arr) {
		if (arr.length == 0)
			throw new IllegalArgumentException("empty array");
		overallRoot = builTreeArray(1, arr);
	}

	private IntTreeNode builTreeArray(int n, int[] arr) {
		if (n > arr.length) {
			return null;
		} else {
			if (arr[n - 1] != -1) {
				return new IntTreeNode(arr[n - 1], builTreeArray(2 * n, arr), builTreeArray(2 * n + 1, arr));
			} else {
				return null;
			}
		}
	}

	// pre: max >= 0 (throws IllegalArgumentException if not)
	// post: constructs a sequential tree with given number of nodes
	public IntTree(int max) {
		if (max < 0) {
			throw new IllegalArgumentException("max: " + max);
		}
		overallRoot = buildTree(1, max);
	}

	// post: returns a sequential tree with n as its root unless n is greater than
	// max, in which case it
	// returns an empty tree
	private IntTreeNode buildTree(int n, int max) {
		if (n > max) {
			return null;
		} else {
			return new IntTreeNode(n, buildTree(2 * n, max), buildTree(2 * n + 1, max));
		}
	}

	public String getInorder() {
		return getInorder(overallRoot);
	}

	private String getInorder(IntTreeNode root) {
		if (root != null) {
			return (getInorder(root.left) + " " + root.data + " " + getInorder(root.right));
		} else {
			return "";
		}
	}

	// post: prints the tree contents using an inorder traversal
	public void printInorder() {
		System.out.print("inorder:");
		printInorder(overallRoot);
		System.out.println();
	}

	// post: prints in inorder the tree with given root
	private void printInorder(IntTreeNode root) {
		if (root != null) {
			printInorder(root.left);
			System.out.print(" " + root.data);
			printInorder(root.right);
		}
	}

	// post: prints the tree contents, one per line, following an inorder traversal
	// and using indentation to
	// indicate node depth; prints right to left so that it looks correct when the
	// output is rotated.
	public void printSideways() {
		printSideways(overallRoot, 0);
	}

	// post: prints in reversed preorder the tree with given root, indenting each
	// line to the given level
	private void printSideways(IntTreeNode root, int level) {
		if (root != null) {
			printSideways(root.right, level + 1);
			for (int i = 0; i < level; i++) {
				System.out.print("       ");
			}
			System.out.println(root.data);
			printSideways(root.left, level + 1);
		}
	}

	// ==================================================
	// Write a method called printLevel that accepts an integer parameter n and
	// returns the values at level n
	// as a string, from left to right, one per line . We will use the convention
	// that the overall root is at level 1, its
	// children are at level 2, and so on. If there are no values at the level, your
	// method should return an
	// empty string. Your method should return an empty string if it is passed a
	// value for a level that
	// is less than 1. See the instruction for example output.
	public String getLevel(int level) {
		if (this.overallRoot == null) {
			return "";
		}
		Queue<IntTreeNode> queue = new LinkedList<IntTreeNode>();
		int currentLevel = 1;
		queue.add(this.overallRoot);
		int i = queue.size();
		while (currentLevel < level && !queue.isEmpty()) {
			IntTreeNode temp = queue.remove();
			// System.out.print(temp.data);
			if (temp.left != null) {
				queue.add(temp.left);
			}
			if (temp.right != null) {
				queue.add(temp.right);
			}
			i--;
			if (i < 1) {
				i = queue.size();
				currentLevel++;
				// System.out.println();
			}
		}
		String str = "";
		while (!queue.isEmpty()) {
			str += queue.remove().data + "\n";
		}
		return str;
	}

	// Write a method called countEmpty that returns the number of empty branches in
	// a tree. An empty tree is
	// considered to have one empty branch (the tree itself). For nonempty trees,
	// your methods should count the
	// total number of empty branches among the nodes of the tree. A leaf node has
	// two empty branches, a node
	// with one nonempty child has one empty branch, and a node with two nonempty
	// children has no empty branches.
	// See the instruction for example output.
	public int countEmpty() {
		return countEmptyBranches(this.overallRoot);
	}

	public int countEmptyBranches(IntTreeNode node) {
		if (node == null) {
			return 1;
		}
		int count = 0;
		if (node.left != null) {
			count += countEmptyBranches(node.left);
		} else {
			count++;
		}
		if (node.right != null) {
			count += countEmptyBranches(node.right);
		} else {
			count++;
		}
		return count;
	}

	// write a toString method for a binary tree of integers. The method should
	// return "empty" for an empty tree.
	// For a leaf node, it should return the data in the node as a string. For a
	// branch node, it should return a
	// parenthesized String that has three elements separately by commas: the data
	// at the root, a string
	// representation of the left subtree, and then a string representation of the
	// right subtree.
	// See the instruction for example output.

	public String dfs(IntTreeNode node) {
		if (node == null) {
			return "empty";
		} else if (node.left == null && node.right == null) {
			return "" + node.data;
		}
		String str = "(" + node.data;
		str += ", " + dfs(node.left);
		str += ", " + dfs(node.right);
		return str + ")";
	}

	public String toString() {
		if (this.overallRoot == null)
			return "";
		return dfs(this.overallRoot);
	}

	// Write a method called makePerfect that adds nodes until the binary tree is a
	// perfect tree. A perfect
	// binary tree is one where all leaves are at the same level. Another way of
	// thinking of it is that you
	// are adding dummy nodes to the tree until every path from the root to a leaf
	// is the same length. A
	// perfect tree's shape is triangular and every branch node has exactly two
	// children, and all of the
	// leaves are at the same level. Each new node you add to the tree should store
	// the value 0.
	public void makePerfect() {
		if (this.overallRoot == null) {
			return;
		}
		Queue<IntTreeNode> queue = new LinkedList<IntTreeNode>();
		int depth = 0;
		queue.add(this.overallRoot);
		int i = queue.size();
		while (!queue.isEmpty()) {
			IntTreeNode temp = queue.remove();
			// System.out.print(temp.data);
			if (temp.left != null) {
				queue.add(temp.left);
			}
			if (temp.right != null) {
				queue.add(temp.right);
			}
			i--;
			if (i < 1) {
				i = queue.size();
				depth++;
				// System.out.println();
			}
		}

		queue = new LinkedList<IntTreeNode>();
		queue.add(this.overallRoot);
		i = queue.size();
		int currentLevel = 1;
		while (currentLevel < depth && !queue.isEmpty()) {
			IntTreeNode temp = queue.remove();
			// System.out.print(temp.data);
			if (temp.left != null) {
				queue.add(temp.left);
			} else {
				temp.left = new IntTreeNode(0);
				queue.add(temp.left);
			}
			if (temp.right != null) {
				queue.add(temp.right);
			} else {
				temp.right = new IntTreeNode(0);
				queue.add(temp.right);
			}
			i--;
			if (i < 1) {
				i = queue.size();
				currentLevel++;
				// System.out.println();
			}
		}
	}
}