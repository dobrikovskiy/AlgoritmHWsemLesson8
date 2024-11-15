public class RedBlackTree {
    private Node root; // Корень дерева

    private class Node {
        int value;
        Node left;
        Node right;
        Color color;

        Node(int value, Color color) {
            this.value = value;
            this.color = color;
        }
    }

    private enum Color {
        BLACK,
        RED
    }

    // Метод для вставки нового значения в дерево
    public void insert(int value) {
        root = insert(root, value);
        if (root != null) {
            root.color = Color.BLACK;  // Корень всегда должен быть черным
        }
    }

    // Рекурсивный метод для вставки
    private Node insert(Node node, int value) {
        if (node == null) {
            return new Node(value, Color.RED);  // Новая нода всегда красная
        }

        if (value < node.value) {
            node.left = insert(node.left, value);
        } else if (value > node.value) {
            node.right = insert(node.right, value);
        }

        // Балансировка дерева
        if (isRed(node.right) && !isRed(node.left)) {
            node = leftRotate(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rightRotate(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            swapColors(node);
        }

        return node;
    }

    // Метод для поиска значения в дереве
    public Node find(int value) {
        return find(root, value);
    }

    private Node find(Node node, int value) {
        if (node == null) {
            return null;
        }
        if (value == node.value) {
            return node;
        }
        if (value < node.value) {
            return find(node.left, value);
        } else {
            return find(node.right, value);
        }
    }

    // Проверка, является ли узел красным
    private boolean isRed(Node node) {
        return node != null && node.color == Color.RED;
    }

    // Левый поворот
    private Node leftRotate(Node node) {
        Node temp = node.right;
        node.right = temp.left;
        temp.left = node;
        temp.color = node.color;
        node.color = Color.RED;
        return temp;
    }

    // Правый поворот
    private Node rightRotate(Node node) {
        Node temp = node.left;
        node.left = temp.right;
        temp.right = node;
        temp.color = node.color;
        node.color = Color.RED;
        return temp;
    }

    // Смена цветов узлов
    private void swapColors(Node node) {
        node.color = (node.color == Color.RED ? Color.BLACK : Color.RED);
        if (node.left != null) node.left.color = Color.BLACK;
        if (node.right != null) node.right.color = Color.BLACK;
    }

    // Метод для вывода дерева
    public void printTree() {
        printTree(root, "", true);
    }

    private void printTree(Node node, String prefix, boolean isTail) {
        if (node != null) {
            System.out.print(prefix + (isTail ? "└── " : "├── "));
            System.out.println(node.value + "(" + node.color + ")");
            printTree(node.left, prefix + (isTail ? "    " : "│   "), false);
            printTree(node.right, prefix + (isTail ? "    " : "│   "), true);
        }
    }

    // Метод main для тестирования
    public static void main(String[] args) {
        RedBlackTree rbt = new RedBlackTree();

        // Пример добавления элементов
        int[] values = {10, 20, 30, 15, 25, 5, 1};
        for (int value : values) {
            rbt.insert(value);
        }

        // Вывод дерева
        System.out.println("Red-Black Tree:");
        rbt.printTree();
    }
}