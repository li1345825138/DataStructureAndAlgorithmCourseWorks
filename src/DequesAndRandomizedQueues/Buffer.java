package DequesAndRandomizedQueues;

import dsa.LinkedStack;
import stdlib.StdOut;

/**
 * Implement a data type called Buffer to represent a buffer in a text editor.
 *
 * @author Lengqiang Lin
 * @date 10/16/2022
 */
public class Buffer {
    protected LinkedStack<Character> left;  // chars left of cursor
    protected LinkedStack<Character> right; // chars right of cursor

    /**
     * Creates an empty buffer.
     * Set up left and right
     */
    public Buffer() {
        this.left = new LinkedStack<>();
        this.right = new LinkedStack<>();
    }

    /**
     * Inserts c at the cursor position.
     * @param c push current Character into left stack
     */
    public void insert(char c) {
        this.left.push(c);
    }

    /**
     * Deletes and returns the character immediately ahead of the cursor.
     * @return return current delete element
     */
    public char delete() {
        return this.right.isEmpty() ? '\0' : this.right.pop();
    }

    /**
     * Moves the cursor k positions to the left.
     * @param k move k positions to the left
     */
    public void left(int k) {
        for (int i = k; !this.left.isEmpty() && i > 0; i--) {
            this.right.push(this.left.pop());
        }
    }

    /**
     * Moves the cursor k positions to the right.
     * @param k move k positions to the right
     */
    public void right(int k) {
        for (int i = k; !this.right.isEmpty() && i > 0; i--) {
            this.left.push(this.right.pop());
        }
    }

    /**
     * Returns the number of characters in the buffer.
     * @return return the number of characters in the buffer
     */
    public int size() {
        return this.left.size() + this.right.size();
    }

    /**
     * Returns a string representation of the buffer with a "|" character (not part of the buffer)
     * at the cursor position.
     *
     * @return return a string representation of the buffer
     */
    public String toString() {
        // A buffer to store the string representation.
        StringBuilder sb = new StringBuilder();

        // Push chars from left into a temporary stack.
        LinkedStack<Character> tempStack = new LinkedStack<>();
        while (!this.left.isEmpty()) {
            tempStack.push(this.left.pop());
        }

        // Append chars from temporary stack to sb.
        while (!tempStack.isEmpty()) {
            char tmpChar = tempStack.pop();
            sb.append(tmpChar);
            this.left.push(tmpChar);        // restore back into left stack
        }

        // Append "|" to sb.
        sb.append("|");

        // Append chars from right to sb.
        while (!this.right.isEmpty()) {
            char tmpChar = this.right.pop();
            sb.append(tmpChar);
            tempStack.push(tmpChar);
        }

        // restore back to right stack
        while (!tempStack.isEmpty()) {
            this.right.push(tempStack.pop());
        }

        // Return the string from sb.
        return sb.toString();
    }

    // Unit tests the data type (DO NOT EDIT).
    public static void main(String[] args) {
        Buffer buf = new Buffer();
        String s = "There is grandeur in this view of life, with its several powers, having been " +
                "originally breathed into a few forms or into one; and that, whilst this planet " +
                "has gone cycling on according to the fixed law of gravity, from so simple a " +
                "beginning endless forms most beautiful and most wonderful have been, and are " +
                "being, evolved. ~ Charles Darwin, The Origin of Species";
        for (int i = 0; i < s.length(); i++) {
            buf.insert(s.charAt(i));
        }
        buf.left(buf.size());
        buf.right(97);
        s = "by the Creator ";
        for (int i = 0; i < s.length(); i++) {
            buf.insert(s.charAt(i));
        }
        buf.right(228);
        buf.delete();
        buf.insert('-');
        buf.insert('-');
        buf.left(342);
        StdOut.println(buf);
    }
}
