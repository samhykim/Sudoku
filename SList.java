
public class SList {


    protected int size;
    protected SListNode head;
    protected SListNode tail;
    protected boolean occupied;

 
    protected SListNode newNode(int item, SListNode next) {
        return new SListNode(item, next);
    }

    /**
     *  SList() constructs for an empty SList.
     **/
    public SList() {
        head = null;
        tail = null;
        size = 0;
        occupied = false;
    }

    /**
     *  insertFront() inserts an item at the front of this SList.
     *
     *  @param item is the item to be inserted.
     *
     *  Performance:  runs in O(1) time.
     **/
    public void insertFront(int item) {
        head = newNode(item, head);
        if (size == 0) {
            tail = head;
        }
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int length() {
        return size;
    }
    /**
     *  insertBack() inserts an item at the back of this SList.
     *
     *  @param item is the item to be inserted.
     *
     *  Performance:  runs in O(1) time.
     **/
    public void insertBack(int item) {
        if (head == null) {
            head = newNode(item, null);
            tail = head;
        } else {
            tail.next = newNode(item, null);
            tail = tail.next;
        }
        size++;
    }

    public void removeEnd() {
        if (head != null)  {
            SListNode curr = head;
            if (head == tail) {
                head = null;
                tail = null;
            }
            else {
                while (curr.next != tail) {
                    curr = curr.next;
                }
                curr.next = null;
                tail = curr;
            }
        }
        size--;
    }

    /**
     *  front() returns the node at the front of this SList.  If the SList is
     *  empty, return an "invalid" node--a node with the property that any
     *  attempt to use it will cause an exception.
     *
     *  @return a ListNode at the front of this SList.
     *
     *  Performance:  runs in O(1) time.
     */
    public SListNode front() {
        if (head != null) {
            return head;
        }
        return null;
    }

    /**
     *  back() returns the node at the back of this SList.  If the SList is
     *  empty, return an "invalid" node--a node with the property that any
     *  attempt to use it will cause an exception.
     *
     *  @return a ListNode at the back of this SList.
     *
     *  Performance:  runs in O(1) time.
     */
    public SListNode back() {
        if (tail != null) {
            return tail;
        }
        return null;
    }

    /**
     *  toString() returns a String representation of this SList.
     *
     *  @return a String representation of this SList.
     *
     *  Performance:  runs in O(n) time, where n is the length of the list.
     */
    public String toString() {
        String result = "[  ";
        SListNode current = head;
        while (current != null) {
            result = result + current.item + "  ";
            current = current.next;
        }
        return result + "]";
    }

    /**
     * nth() returns the item at the specified position. If position < 1 or
     * position > this.length(), null is returned. Otherwise, the item at
     * position "position" is returned. The list does not change.
     * @param position the desired position, from 1 to length(), in the list.
     * @return the item at the given position in the list.
     **/
    public Object nth(int position) {
        SListNode currentNode;
        if ((position < 1) || (head == null)) {
            return null;
        } else {
            currentNode = head;
            while (position > 1) {
                currentNode = currentNode.next;
                if (currentNode == null) {
                    return null;
                }
                position--;
            }
            return currentNode.item;
        }
    }

    public void removeItem(int c) {
         SListNode curr = head;
        if (head.item() == c) {
            head = head.next;
            //curr.next = null;
        }
        else {
            while (curr.next != null) {
                //System.out.println(curr.next.item());

                if (curr.next.item() == c) {
                    SListNode remove = curr.next;
                    curr.next = curr.next.next;
                    remove.next = null;
                }
                else {
                 curr = curr.next;
                }
            }
        }
        size--;
    }

    public boolean contains(int c){
        SListNode curr = head;
        while(curr != null){
            if(curr.item() == c){
                return true;
            }
            curr = curr.next();
        }
        return false;
    }



    public static SList copy(SList s){
        SListNode curr = s.head;
        SList copied = new SList();
        while(curr != null){
            copied.insertBack(curr.item());
            curr = curr.next();
        }
        return copied;
    }

}
