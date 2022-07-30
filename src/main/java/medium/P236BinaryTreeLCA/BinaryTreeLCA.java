package medium.P236BinaryTreeLCA;

import java.util.*;

/**
 * Lowest ancestor of given two nodes in a binary tree.
 * <p>
 * Author: Ruifeng Ma
 * Date: 2022-Jul-27
 */

public class BinaryTreeLCA {
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode( int val ) {
            this.val = val;
        }
    }

    /**
     * Perform depth first search on node p and q separately in the tree via recursion.
     * For each tracing, record the path into a queue.
     * Traverse the two queues together and look for the first node where
     * the two paths start diverting.
     * That node is the LCA of p and q.
     */
    private static TreeNode lowestCommonAncestorDPS( TreeNode root, TreeNode p, TreeNode q ) {
        Queue<TreeNode> pPath = findNodePath(root, p);
        Queue<TreeNode> qPath = findNodePath(root, q);

        TreeNode lca;
        do {
            lca = pPath.poll();
            qPath.poll();
        } while (pPath.peek() == qPath.peek());

        return lca;
    }

    private static Queue<TreeNode> findNodePath( TreeNode root, TreeNode node ) {
        if (root == null) return null;

        Queue<TreeNode> path = new LinkedList<>();
        path.add(root);

        if (root != node) {
            Queue<TreeNode> subPath = findNodePath(root.left, node);
            if (subPath == null) {
                subPath = findNodePath(root.right, node);
            }

            if (subPath == null) return null;

            path.addAll(subPath);
        }

        return path;
    }

    /**
     * Simplified recursion.
     * 1. Look for LCA in left sub tree as if p and q were in the left sub tree.
     * 2. Look for LCA in right sub tree as if p and q were in the right sub tree.
     * 3. LCA is root if p or q equals to root, or p & q are found in opposite trees.
     */
    private static TreeNode lowestCommonAncestorRecur(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode leftLCA = lowestCommonAncestorRecur(root.left, p, q);
        TreeNode rightLCA = lowestCommonAncestorRecur(root.right, p, q);
        return leftLCA == null ? rightLCA : rightLCA == null ? leftLCA : root;

        /* Breakdown of one-liner */
        // if ( leftLCA == null && rightLCA == null) return root; // p & q found in opposite trees
        // if ( leftLCA == null ) return rightLCA; // p & q found in right sub tree
        // if ( rightLCA == null ) return leftLCA; // p & q found in left sub tree
        // return null;
    }


    /**
     * Recursion.
     * Check below scenarios.
     * > If root equals p or q, return root as LCA.
     * > Else
     * >> If p is found in left child tree, and q is found in right child tree, return root as LCA.
     * >> If q is found in left child tree, and p is found in right child tree, return root as LCA.
     * >> If p and q are both found in left child tree, recur in left child tree.
     * >> If p and q are both found in right child tree, recur in right child tree.
     */
    private static TreeNode lowestCommonAncestor( TreeNode root, TreeNode p, TreeNode q ) {
        if (root == p || root == q) return root;

        if (nodePresent(root.left, p)) {
            if (nodePresent(root.right, q)) return root;
            else return lowestCommonAncestor(root.left, p, q);
        } else {
            if (nodePresent(root.left, q)) return root;
            else return lowestCommonAncestor(root.right, p, q);
        }
    }

    private static boolean nodePresent( TreeNode root, TreeNode node ) {
        if (root == null) return false;
        if (root == node) {
            return true;
        }
        return nodePresent(root.left, node) || nodePresent(root.right, node);
    }

    private static void levelOrderTraverse( TreeNode root ) {
        List<TreeNode> nodeList = new ArrayList<>();
        nodeList.add(root);
        levelOrderTraverse(nodeList);
    }

    private static void levelOrderTraverse( List<TreeNode> list ) {
        List<TreeNode> nextList = new ArrayList<>();

        for (TreeNode n : list) {
            System.out.print(n.val + " ");
            if (n.left != null) nextList.add(n.left);
            if (n.right != null) nextList.add(n.right);
        }
        System.out.println();

        if (!nextList.isEmpty()) levelOrderTraverse(nextList);
    }

    public static void main( String[] args ) {
        System.out.println("Welcome to the rabbit hole of lowest common ancestor of a binary tree.");

        TreeNode p = new TreeNode(5);
        TreeNode q = new TreeNode(4);

        TreeNode root = new TreeNode(3);
        root.left = p;
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        root.left.right.left = new TreeNode(7);
        root.left.right.right = q;
        root.right = new TreeNode(1);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);

        System.out.println("Binary tree:");
        levelOrderTraverse(root);
        System.out.println("p: " + p.val);
        System.out.println("q: " + q.val);

        System.out.println("LCA by recursion:");
        System.out.println(Objects.requireNonNull(lowestCommonAncestor(root, p, q)).val);

        System.out.println("LCA by simplified recursion:");
        System.out.println(Objects.requireNonNull(lowestCommonAncestorRecur(root, p, q)).val);

        System.out.println("LCA by DPS:");
        System.out.println(Objects.requireNonNull(lowestCommonAncestorDPS(root, p, q)).val);

        System.out.println("All rabbits gone.");
    }
}
