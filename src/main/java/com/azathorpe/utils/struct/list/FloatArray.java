package com.azathorpe.utils.struct.list;

import java.util.Arrays;

public class FloatArray {
    private long[] data;
    private int size; // 当前元素个数
    private int capacity; // 当前数组容量

    // 默认初始容量
    private static final int DEFAULT_CAPACITY = 16;

    public FloatArray() {
        this(DEFAULT_CAPACITY);
    }

    public FloatArray(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Initial capacity cannot be negative: " + initialCapacity);
        }
        this.capacity = initialCapacity;
        this.data = new long[initialCapacity];
        this.size = 0;
    }

    /**
     * 添加元素
     * 如果容量不足，自动扩容（通常翻倍）
     */
    public void add(long value) {
        if (size == capacity) {
            resize();
        }
        data[size++] = value;
    }


    /**
     * 传统删除：保持顺序 O(n)
     * 仅在必须保持顺序时使用
     */
    public long removeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        long oldValue = data[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(data, index + 1, data, index, numMoved);
        }
        // 清除最后一个位置的引用，防止内存泄漏（如果是对象数组）
        // data[--size] = 0;
        size--;
        return oldValue;
    }

    /**
     * 获取元素
     */
    public long get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return data[index];
    }

    /**
     * 设置元素
     */
    public void set(int index, long value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        data[index] = value;
    }

    /**
     * 清空列表，但不释放底层数组，以便复用
     */
    public void clear() {
        size = 0;
        // 如果是对象数组，建议 Arrays.fill(data, 0, size, null);
    }

    /**
     * 预分配容量，避免多次扩容
     * 在批量添加前调用此方法可显著提升性能
     */
    public void ensureCapacity(int minCapacity) {
        if (minCapacity > capacity) {
            int newCapacity = Math.max(minCapacity, capacity * 2);
            resizeTo(newCapacity);
        }
    }

    /**
     * 内部扩容逻辑：通常翻倍
     */
    private void resize() {
        int newCapacity = capacity * 2;
        resizeTo(newCapacity);
    }

    private void resizeTo(int newCapacity) {
        this.capacity = newCapacity;
        this.data = Arrays.copyOf(this.data, newCapacity);
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }
}
