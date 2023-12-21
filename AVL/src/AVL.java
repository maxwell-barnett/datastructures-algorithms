import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author Maxwell Barnett
 * @version 1.0
 * @userid mbarnett42
 * @GTID 903683864
 *
 *       Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 *       Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Collection or item in the collection is null.");
        }
        for (T item : data) {
            add(item);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        root = addHelper(root, data);
    }

    /**
     * Recursive helper method for adding element to the tree.
     * 
     * @param curr current node
     * @param data data to add
     * @return root node
     */
    private AVLNode<T> addHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new AVLNode<T>(data);
        } else {
            if (data.compareTo(curr.getData()) < 0) {
                curr.setLeft(addHelper(curr.getLeft(), data));
            } else if (data.compareTo(curr.getData()) > 0) {
                curr.setRight(addHelper(curr.getRight(), data));
            }

            setHeightAndBF(curr);

            if (curr.getBalanceFactor() == 2 && curr.getLeft().getBalanceFactor() >= 0) {
                curr = rightRotation(curr);
            } else if (curr.getBalanceFactor() == 2) {
                curr = leftRightRotation(curr);
            } else if (curr.getBalanceFactor() == -2 && curr.getRight().getBalanceFactor() <= 0) {
                curr = leftRotation(curr);
            } else if (curr.getBalanceFactor() == -2) {
                curr = rightLeftRotation(curr);
            }
            return curr;
        }
    }

    /**
     * Helper method to perform a right rotation.
     * 
     * @param curr current node
     * @return new parent node
     */
    private AVLNode<T> rightRotation(AVLNode<T> curr) {
        AVLNode<T> temp = curr.getLeft();
        if (temp.getRight() != null) {
            curr.setLeft(temp.getRight());
        } else {
            curr.setLeft(null);
        }
        temp.setRight(curr);
        setHeightAndBF(curr);
        setHeightAndBF(temp);
        return temp;
    }

    /**
     * Helper method to perform a rightLeft rotation.
     * 
     * @param curr current node
     * @return new parent node
     */
    private AVLNode<T> rightLeftRotation(AVLNode<T> curr) {
        curr.setRight(rightRotation(curr.getRight()));
        return leftRotation(curr);
    }

    /**
     * Helper method to perform a left rotation.
     * 
     * @param curr current node
     * @return new parent node
     */
    private AVLNode<T> leftRotation(AVLNode<T> curr) {
        AVLNode<T> temp = curr.getRight();
        if (temp.getLeft() != null) {
            curr.setRight(temp.getLeft());
        } else {
            curr.setRight(null);
        }
        temp.setLeft(curr);
        setHeightAndBF(curr);
        setHeightAndBF(temp);
        return temp;
    }

    /**
     * Helper method to perform a leftRight rotation.
     * 
     * @param curr current node
     * @return new parent node
     */
    private AVLNode<T> leftRightRotation(AVLNode<T> curr) {
        curr.setLeft(leftRotation(curr.getLeft()));
        return rightRotation(curr);
    }

    /**
     * Helper method to set height and balance factor of current node.
     * 
     * @param curr current node
     */
    private void setHeightAndBF(AVLNode<T> curr) {
        int leftHeight;
        int rightHeight;

        if (curr.getLeft() == null) {
            leftHeight = -1;
        } else {
            leftHeight = curr.getLeft().getHeight();
        }

        if (curr.getRight() == null) {
            rightHeight = -1;
        } else {
            rightHeight = curr.getRight().getHeight();
        }

        curr.setHeight(Math.max(leftHeight, rightHeight) + 1);
        curr.setBalanceFactor(leftHeight - rightHeight);

    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Given data is null.");
        }
        if (!contains(data)) {
            throw new NoSuchElementException("Data was not found in the tree");
        }

        AVLNode<T> dummy = new AVLNode<T>(null);
        root = removeHelper(root, data, dummy);
        return dummy.getData();
    }

    /**
     * Recursive helper method for removing an element.
     * 
     * @param curr  current node
     * @param data  data to remove
     * @param dummy dummy node to hold data
     * @return node to remove
     */
    private AVLNode<T> removeHelper(AVLNode<T> curr, T data, AVLNode<T> dummy) {
        if (data.compareTo(curr.getData()) == 0) {
            dummy.setData(curr.getData());
            size--;
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getRight() != null && curr.getLeft() == null) {
                return curr.getRight();
            } else {
                AVLNode<T> dummy2 = new AVLNode<T>(null);
                curr.setLeft(removePredecessor(curr.getLeft(), dummy2));
                curr.setData(dummy2.getData());
            }
        } else {
            if (data.compareTo(curr.getData()) < 0) {
                curr.setLeft(removeHelper(curr.getLeft(), data, dummy));
            } else if (data.compareTo(curr.getData()) > 0) {
                curr.setRight(removeHelper(curr.getRight(), data, dummy));
            }
        }

        setHeightAndBF(curr);

        if (curr.getBalanceFactor() == 2 && curr.getLeft().getBalanceFactor() >= 0) {
            curr = rightRotation(curr);
        } else if (curr.getBalanceFactor() == 2) {
            curr = leftRightRotation(curr);
        } else if (curr.getBalanceFactor() == -2 && curr.getRight().getBalanceFactor() <= 0) {
            curr = leftRotation(curr);
        } else if (curr.getBalanceFactor() == -2) {
            curr = rightLeftRotation(curr);
        }
        return curr;
    }

    /**
     * Recursive helper method to remove the predecessor node of the curent node.
     * 
     * @param curr  current node
     * @param dummy dummy node to hold data
     * @return predecessor node removed
     */
    private AVLNode<T> removePredecessor(AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr.getRight() == null) {
            dummy.setData(curr.getData());
            return curr.getLeft();
        } else {
            curr.setRight(removePredecessor(curr.getRight(), dummy));
        }
        return curr;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Given data is null.");
        }
        return getHelper(data, root).getData();
    }

    /**
     * Recursive helper method to get an element.
     * 
     * @param data data to retrieve
     * @param curr current node
     * @return node with data requested
     */
    private AVLNode<T> getHelper(T data, AVLNode<T> curr) {
        if (curr == null) {
            throw new NoSuchElementException("Data was not found in the tree");
        } else if (data.compareTo(curr.getData()) == 0) {
            return curr;
        } else if (data.compareTo(curr.getData()) < 0) {
            return getHelper(data, curr.getLeft());
        } else if (data.compareTo(curr.getData()) > 0) {
            return getHelper(data, curr.getRight());
        }
        return curr;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     *         otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Given data is null.");
        }
        return get(data) != null;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return root.getHeight();
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * The predecessor is the largest node that is smaller than the current data.
     *
     * Should be recursive.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     * 76
     * / \
     * 34 90
     * \ /
     * 40 81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     *         one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Given data is null.");
        }
        if (!contains(data)) {
            throw new NoSuchElementException("Data was not found in the tree");
        }

        AVLNode<T> dataNode = getHelper(data, root);
        if (getHelper(data, root).getLeft() != null) {
            return predHelper(dataNode.getLeft()).getData();
        } else {
            return ancestor(root, dataNode).getData();
        }

    }

    /**
     * Recursive helper method to find the predecessor of the current node.
     * 
     * @param curr current node
     * @return predecessor node
     */
    private AVLNode<T> predHelper(AVLNode<T> curr) {
        if (curr == null) {
            return null;
        } else if (curr.getHeight() == 0) {
            return curr;
        } else if (curr.getRight() != null) {
            return predHelper(curr.getRight());
        }
        return curr;
    }

    /**
     * Recursive helper method to find the lowest ancestor of the current node.
     * 
     * @param curr     current working node, starting from root
     * @param dataNode node to find ancestor of
     * @return ancestor node
     */
    private AVLNode<T> ancestor(AVLNode<T> curr, AVLNode<T> dataNode) {
        if (curr == null) {
            return null;
        } else if (curr.getRight().equals(dataNode)) {
            return curr;
        } else if (curr.getRight().getLeft().equals(dataNode)) {
            return curr;
        } else if (dataNode.getData().compareTo(curr.getData()) < 0) {
            return ancestor(curr.getLeft(), dataNode);
        } else {
            return ancestor(curr.getRight(), dataNode);
        }

    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with
     * the deepest depth.
     *
     * Should be recursive.
     *
     * Must run in O(log n) for all cases.
     *
     * Example
     * Tree:
     * 2
     * / \
     * 0 3
     * \
     * 1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     * 2
     * / \
     * 0 4
     * \ /
     * 1 3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        return deepHelper(root).getData();
    }

    /**
     * Recursive helper method to find the maximum deepest node of an AVL
     * 
     * @param curr current node
     * @return deepest node
     */
    private AVLNode<T> deepHelper(AVLNode<T> curr) {
        if (curr == null) {
            return null;
        } else if (curr.getHeight() == 0) {
            return curr;
        } else if (curr.getBalanceFactor() > 0) {
            return deepHelper(curr.getLeft());
        } else {
            return deepHelper(curr.getRight());
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
