package com.example.derongliu.authority;

public class Tree {

    public static void main(String[] args) {

    }


    static class BinaryTree {

        TreeNode rootNode;

        BinaryTree() {
            rootNode = new TreeNode();
        }

        BinaryTree(TreeNode rootNode) {
            this.rootNode = rootNode;
        }

        TreeNode addNode(TreeNode parentNode, int val, boolean isLeft) {
            if (parentNode == null) {
                throw new RuntimeException("parent must bo nonNull");
            }
            if (isLeft && parentNode.leftNode != null) {
                throw new RuntimeException("left node is added");
            }

            if (!isLeft && parentNode.rightNode != null) {
                throw new RuntimeException("right node is added");
            }

            TreeNode node = new TreeNode(val);
            if (isLeft) {
                parentNode.leftNode = node;
            } else {
                parentNode.rightNode = node;
            }
            return node;
        }

        boolean isEmpty() {
            return rootNode == null;
        }

        TreeNode getRootNode() {
            return rootNode;
        }

        TreeNode getParent(TreeNode node){

            return null;
        }



    }


    static class TreeNode {
        int value;
        TreeNode leftNode;
        TreeNode rightNode;

        TreeNode() {
        }

        TreeNode(int value) {
            this.value = value;
        }

        TreeNode(int value, TreeNode leftNode, TreeNode rightNode) {
            this.value = value;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }
    }
}
