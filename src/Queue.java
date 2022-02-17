/**
 * Queue class to manage the Players that are created given new Nodes
 *
 * 
 * @author Frank Watring
 */
public class Queue {
  private class Node {

    private Player player;
    private Node next;

    /**
     * Default constructor
     */
    public Node() {
      player = null;
      next = null;
    }

    /**
     * Constructor that contains Player of a Queue
     * 
     * @param player Player being created
     * @param next   Node being created
     */
    public Node(Player player, Node next) {
      this.player = player;
      this.next = next;
    }

  }

  private Node head;
  private Node tail;
  private int size;

  /**
   * Default constructor -- just takes the default values
   */
  public Queue() {
    head = null;
    tail = null;
    size = 0;

  }

  /**
   * Checks the queue if it is empty through head = null
   * 
   * @return boolean true or false
   */
  public boolean isEmpty() {
    if (this.head != null) {
      return false;
    }

    return true;

  }

  /**
   * Adds a node to the end of the list
   * 
   * @param item Player Object
   */
  public void enqueue(Player item) {
    // Add at the end of the list
    Node temp = new Node(item, null);
    size++;
    if (head != null) {
      tail.next = temp;
      tail = temp;
    } else {
      head = temp;
      tail = temp;
    }
  }

  /**
   * Removes front node from queue and updates size
   * 
   */
  public void dequeue() {
    // Remove from the front of the list
    size--;
    if (head.next == null || size == 0) {
      head = null;
    } else {
      head = head.next;
    }
  }

  /**
   * Gets the front of the queue
   * 
   * @return Player Object
   */
  public Player front() {
    return head.player;

  }

  /**
   * Gets the size of the Queue
   * 
   * @return size
   */
  public int getSize() {
    return size;

  }

  @Override
  public boolean equals(Object queue) {
    Queue newQueue = (Queue) queue;
    Node curPtr1 = this.head;
    Node curPtr2 = newQueue.head;
    boolean stop = false;
    if (curPtr1 != null && curPtr2 != null && this.size == newQueue.size) {
      while (curPtr1.next != null && curPtr2.next != null || stop) {

        if (curPtr1.player.equals(curPtr2.player)) {
          curPtr1 = curPtr1.next;
          curPtr2 = curPtr2.next;
        } else {
          stop = true;
          return false;

        }
      }
    } else {
      stop = true;
      return false;
    }
    return true;
  }

  /**
   * Clears all of the queue
   * 
   */
  public void clear() {
    while (head != null) {
      this.dequeue();
    }
  }
}