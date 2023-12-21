import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        for (T item : data) {
            if (item == null) {
                throw new IllegalArgumentException("One or more elements in the list are null.");
            } else {
                add(item);
            }
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Inserted data cannot be null.");
        }
        root = addHelper(root, data);
    }

    /**
     * Recursive helper method for adding items to BST
     * 
     * @param curr the current node
     * @param data the data to add and compare to
     * @return the newly created node or existing node with same data.
     */
    private BSTNode<T> addHelper(BSTNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new BSTNode<T>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addHelper(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addHelper(curr.getRight(), data));
        }
        return curr;

    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Inputted data is not valid.");
        }
        BSTNode<T> dummy = new BSTNode<T>(null);
        root = removeHelper(root, data, dummy);
        return dummy.getData();
    }

    /**
     * Recursive helper method for removing data from BST.
     * 
     * @param curr  current node
     * @param data  data to remove and compare to
     * @param dummy null node to store data
     * @return the removed node
     */
    private BSTNode<T> removeHelper(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("Data was not found in the tree");
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeHelper(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeHelper(curr.getRight(), data, dummy));
        } else {
            dummy.setData(curr.getData());
            size--;
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getRight() != null && curr.getLeft() == null) {
                return curr.getRight();
            } else {
                BSTNode<T> dummy2 = new BSTNode<T>(null);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        return curr;
    }

    /**
     * Recursive helper method to find the successor for the removeHelper method.
     * 
     * @param curr  current node
     * @param dummy null node to store data
     * @return the successor node
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy));
        }
        return curr;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data to search for is not valid.");
        }

        if (searchHelper(root, data) == null) {
            throw new NoSuchElementException("Data was not found in the tree.");
        }
        return searchHelper(root, data).getData();
    }

    /**
     * Recursive helper method to search through a BST.
     * 
     * @param curr current node
     * @param data data to find and compare to
     * @return data found or null
     */
    private BSTNode<T> searchHelper(BSTNode<T> curr, T data) {
        if (curr == null) {
            return null;
        }
        if (curr.getData().compareTo(data) == 0) {
            return curr;
        } else if (data.compareTo(curr.getData()) < 0) {
            return searchHelper(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return searchHelper(curr.getRight(), data);
        }
        return curr;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     *         otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data is not valid.");
        }
        return searchHelper(root, data) != null;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new LinkedList<>();
        preorderHelper(root, list);
        return list;
    }

    /**
     * Recursive helper method for preorder traversal of BST.
     * 
     * @param curr current node
     * @param list list to add nodes to
     */
    private void preorderHelper(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        }
        list.add(curr.getData());
        preorderHelper(curr.getLeft(), list);
        preorderHelper(curr.getRight(), list);
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new LinkedList<>();
        inorderHelper(root, list);
        return list;
    }

    /**
     * Recursive helper method for inorder traversal of BST.
     * 
     * @param curr current node
     * @param list list to add nodes to
     */
    private void inorderHelper(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        }
        inorderHelper(curr.getLeft(), list);
        list.add(curr.getData());
        inorderHelper(curr.getRight(), list);
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new LinkedList<>();
        postorderHelper(root, list);
        return list;
    }

    private void postorderHelper(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        }
        postorderHelper(curr.getLeft(), list);
        postorderHelper(curr.getRight(), list);
        list.add(curr.getData());
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> q = new LinkedList<>();
        List<T> dataList = new LinkedList<>();
        if (size == 0) {
            return dataList;
        }
        q.add(root);
        dataList.add(root.getData());
        while (!q.isEmpty()) {
            BSTNode<T> curr = q.remove();
            if (curr != null) {
                if (curr.getLeft() != null) {
                    q.add(curr.getLeft());
                    dataList.add(curr.getLeft().getData());
                }
                if (curr.getRight() != null) {
                    q.add(curr.getRight());
                    dataList.add(curr.getRight().getData());
                }
            }
        }
        return dataList;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }

    /**
     * Recursive helper method for finding the height of a tree.
     * 
     * @param curr current node
     * @return the height of the tree
     */
    private int heightHelper(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            int lheight = heightHelper(curr.getLeft());
            int rheight = heightHelper(curr.getRight());

            return Math.max(lheight, rheight) + 1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * This must be done recursively.
     * 
     * A good way to start is by finding the deepest common ancestor (DCA) of both
     * data
     * and add it to the list. You will most likely have to split off and
     * traverse the tree for each piece of data adding to the list in such a
     * way that it will return the path in the correct order without requiring any
     * list manipulation later. One way to accomplish this (after adding the DCA
     * to the list) is to then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list.
     *
     * Please note that there is no relationship between the data parameters
     * in that they may not belong to the same branch.
     * 
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use considering the Big-O efficiency of the list
     * operations.
     *
     * This method only needs to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     * 
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     * 50
     * / \
     * 25 75
     * / \
     * 12 37
     * / \ \
     * 11 15 40
     * /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("One or more data inputs are not valid.");
        }
        if (size == 0) {
            throw new NoSuchElementException("Tree is empty.");
        }
        LinkedList<T> list = new LinkedList<>();
        BSTNode<T> dca = dcaFinder(data1, data2, root);
        list.add(dca.getData());
        if (data1.equals(data2)) {
            return list;
        }
        list = findPathAddToListD1(data1, list, dca, dca);
        list = findPathAddToListD2(data2, list, dca, dca);
        return list;
    }

    /**
     * Helper method to find deepest common ancestor.
     * 
     * @param data1 first data to be in subtree
     * @param data2 second data to be in subtree
     * @param curr  current node
     * @return deepest common ancestor node
     */
    private BSTNode<T> dcaFinder(T data1, T data2, BSTNode<T> curr) {
        if (data1.compareTo(curr.getData()) > 0 && data2.compareTo(curr.getData()) > 0) {
            dcaFinder(data1, data2, curr.getRight());
        } else if (data1.compareTo(curr.getData()) < 0 && data2.compareTo(curr.getData()) < 0) {
            dcaFinder(data1, data2, curr.getLeft());
        }
        return curr;
    }

    /**
     * Helper method to add data1's nodes to the list.
     * 
     * @param data1 data to recurse down to
     * @param list  list to add data to.
     * @param curr  current node
     * @param dca   deepest common ancestor
     * @return list with data1's nodes connecting it to the dca
     */
    private LinkedList<T> findPathAddToListD1(T data1, LinkedList<T> list, BSTNode<T> curr, BSTNode<T> dca) {
        if (curr == null) {
            throw new NoSuchElementException("Data 1 was not found in the tree.");
        }
        if (data1.compareTo(curr.getData()) == 0) {
            if (curr != dca) {
                list.addFirst(curr.getData());
            }
            return list;
        } else if (data1.compareTo(curr.getData()) < 0) {
            if (curr != dca) {
                list.addFirst(curr.getData());
            }
            findPathAddToListD1(data1, list, curr.getLeft(), dca);
        } else if (data1.compareTo(curr.getData()) > 0) {
            if (curr != dca) {
                list.addLast(curr.getData());
            }
            findPathAddToListD1(data1, list, curr.getRight(), dca);
        }
        return list;
    }

    /**
     * Helper method to add data2's nodes to the list.
     * 
     * @param data2 data to recurse down to
     * @param list  list to add data to.
     * @param curr  current node
     * @param dca   deepest common ancestor
     * @return list with data2's nodes connecting it to the dca
     */
    private LinkedList<T> findPathAddToListD2(T data2, LinkedList<T> list, BSTNode<T> curr, BSTNode<T> dca) {
        if (curr == null) {
            throw new NoSuchElementException("Data 2 was not found in the tree.");
        }
        if (data2.compareTo(curr.getData()) == 0) {
            if (curr != dca) {
                list.addLast(curr.getData());
            }
            return list;
        } else if (data2.compareTo(curr.getData()) > 0) {
            if (curr != dca) {
                list.addLast(curr.getData());
            }
            findPathAddToListD2(data2, list, curr.getRight(), dca);
        } else if (data2.compareTo(curr.getData()) < 0) {
            if (curr != dca) {
                list.addFirst(curr.getData());
            }
            findPathAddToListD2(data2, list, curr.getLeft(), dca);
        }
        return list;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
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
