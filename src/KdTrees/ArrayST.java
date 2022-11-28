package KdTrees;

import dsa.BasicST;
import dsa.LinkedQueue;
import stdlib.StdIn;
import stdlib.StdOut;

/**
 * Implement a data type called ArrayST that uses an unordered array as the underlying
 * data structure to implement the basic symbol table API
 *
 * @author Lengqiang Lin
 * @date 11/28/2022
 */
public class ArrayST<Key, Value> implements BasicST<Key, Value> {
    private Key[] keys;     // keys in the symbol table
    private Value[] values; // the corresponding values
    private int n;          // number of key-value pairs

    /**
     * Constructs an empty symbol table.
     */
    public ArrayST() {
        this.keys = (Key[]) new Object[2];
        this.values = (Value[]) new Object[2];
    }

    /**
     * Returns true if this symbol table is empty, and false otherwise.
     *
     * @return true if array is empty, otherwise false
     */
    public boolean isEmpty() {
        return this.n == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the size of key-value pairs
     */
    public int size() {
        return this.n;
    }

    /**
     * Inserts the key and value pair into this symbol table.
     *
     * @param key   unique key
     * @param value value that pair with key
     */
    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        if (value == null) {
            throw new IllegalArgumentException("value is null");
        }

        // if current key is already exists, replace with new data
        if (contains(key)) {
            for (int i = 0; i < this.n; i++) {
                if (key.equals(this.keys[i])) {
                    this.values[i] = value;
                    return;
                }
            }
        }

        // if current insert value is greater than current field size, resize
        if (this.n >= this.values.length) {
            resize(this.n << 1);        // same as this.n * 2
        }

        // append new key and value to array
        this.keys[this.n] = key;
        this.values[this.n] = value;
        this.n++;
    }

    /**
     * Returns the value associated with key in this symbol table, or null.
     *
     * @param key key to find
     * @return the value that pair with search key
     */
    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        for (int i = 0; i < this.n; i++) {
            if (this.keys[i].equals(key)) {
                return this.values[i];
            }
        }
        return null;
    }

    /**
     * Returns true if this symbol table contains key, and false otherwise.
     *
     * @param key search key
     * @return if key is exists return true, otherwise false
     */
    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        return get(key) != null;
    }

    /**
     * Deletes key and the associated value from this symbol table.
     *
     * @param key key to delete
     */
    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        for (int i = 0; i < this.n; i++) {
            // if find the key, copy all data to front 1 index (convert delete data)
            if (key.equals(this.keys[i])) {
                for (int j = i; j < this.n - 1; j++) {
                    this.keys[j] = this.keys[j + 1];
                    this.values[j] = this.values[j + 1];
                }

                // set last element to be null
                this.keys[this.n - 1] = null;
                this.values[this.n - 1] = null;
                break;
            }
        }
        this.n--;

        // resize if size is quadrant than n
        if (this.n > 0 && this.keys.length / 4 == this.n) {
            resize(this.keys.length >> 1);  // same as this.keys.length / 2
        }
    }

    /**
     * Returns all the keys in this symbol table.
     *
     * @return the iterable iterator
     */
    public Iterable<Key> keys() {
        LinkedQueue<Key> q = new LinkedQueue<>();
        for (int i = 0; i < this.n; i++) {
            q.enqueue(this.keys[i]);
        }
        return q;
    }

    // Resizes the underlying arrays to capacity.
    private void resize(int capacity) {
        Key[] tempk = (Key[]) new Object[capacity];
        Value[] tempv = (Value[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            tempk[i] = keys[i];
            tempv[i] = values[i];
        }
        values = tempv;
        keys = tempk;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        ArrayST<String, Integer> st = new ArrayST<>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
    }
}
