import static org.junit.Assert.*;
import org.junit.Test;

public class QueueTest {

  // Testing the Constructor Declaration
  @Test
  public void QueueConstructorTest() {
    Queue myQueue1 = new Queue();
    Player newPlayer = new Player(78253, "Healer", 20);

    myQueue1.enqueue(newPlayer);

    assertEquals("ConstructorTest fails", newPlayer, myQueue1.front());
  }

  // Testing the isEmpty Method if true
  @Test
  public void QueueisEmptyTrueTest() {
    Queue myQueue = new Queue();
    assertEquals("isEmptyTrueTest fails", true, myQueue.isEmpty());

  }

  // Testing the enqueue Method
  @Test
  public void QueueEnqueueTest() {
    Queue myQueue1 = new Queue();
    Player newPlayer = new Player(78253, "Healer", 20);

    myQueue1.enqueue(newPlayer);

    assertEquals("enqueueTest fails", newPlayer, myQueue1.front());

  }

  // Testing the enqueue Method
  @Test
  public void QueueEnqueueMultipleTest() {
    Queue myQueue1 = new Queue();
    Player newPlayer = new Player(78253, "Healer", 20);
    Player newPlayer2 = new Player(69032, "DPS", 20);

    myQueue1.enqueue(newPlayer);
    myQueue1.enqueue(newPlayer2);

    assertEquals("enqueueMultipleTest fails", newPlayer, myQueue1.front());

  }

  // Testing the dequeue Method
  @Test
  public void QueueDequeueTest() {
    Queue myQueue1 = new Queue();
    Player newPlayer = new Player(78253, "Healer", 20);

    myQueue1.enqueue(newPlayer);
    myQueue1.dequeue();

    assertEquals("dequeueTest fails", true, myQueue1.isEmpty());

  }

  // Testing the dequeue Method
  @Test
  public void QueueDequeueMultipleTest() {
    Queue myQueue1 = new Queue();
    Player newPlayer = new Player(78253, "Healer", 20);
    Player newPlayer2 = new Player(69032, "DPS", 20);

    myQueue1.enqueue(newPlayer);
    myQueue1.enqueue(newPlayer2);
    myQueue1.dequeue();
    myQueue1.dequeue();

    assertEquals("dequeueMultipleTest fails", true, myQueue1.isEmpty());

  }

  // Testing the front Method
  @Test
  public void QueueFrontTest() {
    Queue myQueue1 = new Queue();
    Player newPlayer = new Player(78253, "Healer", 20);

    myQueue1.enqueue(newPlayer);

    assertEquals("frontTest fails", newPlayer, myQueue1.front());

  }

  // Testing the getSize Method
  @Test
  public void QueueGetSizeTest() {
    Queue myQueue1 = new Queue();
    Player newPlayer1 = new Player(78253, "Healer", 20);
    Player newPlayer2 = new Player(78253, "Healer", 20);
    Player newPlayer3 = new Player(78253, "Healer", 20);

    myQueue1.enqueue(newPlayer1);
    myQueue1.enqueue(newPlayer2);
    myQueue1.enqueue(newPlayer3);

    assertEquals("getSizeTest fails", 3, myQueue1.getSize());

  }

  // Testing the equals Method
  @Test
  public void QueueEqualsTrueTest() {
    Queue myQueue1 = new Queue();
    Player newPlayer1 = new Player(78253, "Healer", 20);
    Player newPlayer2 = new Player(78253, "Healer", 20);
    Player newPlayer3 = new Player(78253, "Healer", 20);

    myQueue1.enqueue(newPlayer1);
    myQueue1.enqueue(newPlayer2);
    myQueue1.enqueue(newPlayer3);

    Queue myQueue2 = new Queue();
    Player newPlayer4 = new Player(78253, "Healer", 20);
    Player newPlayer5 = new Player(78253, "Healer", 20);
    Player newPlayer6 = new Player(78253, "Healer", 20);

    myQueue2.enqueue(newPlayer4);
    myQueue2.enqueue(newPlayer5);
    myQueue2.enqueue(newPlayer6);

    assertEquals("equalsTest fails", true, myQueue1.equals(myQueue2));

  }

  // Testing the equals Method
  @Test
  public void QueueEqualsFailTest() {
    Queue myQueue1 = new Queue();
    Player newPlayer1 = new Player(78253, "Healer", 20);
    Player newPlayer2 = new Player(78253, "Healer", 20);
    Player newPlayer3 = new Player(78252, "Healer", 20);

    myQueue1.enqueue(newPlayer1);
    myQueue1.enqueue(newPlayer2);
    myQueue1.enqueue(newPlayer3);

    Queue myQueue2 = new Queue();
    Player newPlayer4 = new Player(78253, "Healer", 20);
    Player newPlayer5 = new Player(78253, "Healer", 20);
    Player newPlayer6 = new Player(78253, "Healer", 20);

    myQueue2.enqueue(newPlayer4);
    myQueue2.enqueue(newPlayer5);
    myQueue2.enqueue(newPlayer6);

    assertEquals("equalsTest fails", false, myQueue1.equals(myQueue2));

  }

  @Test
  public void QueueClearTest() {
    Queue myQueue1 = new Queue();
    Player newPlayer1 = new Player(78253, "Healer", 20);
    Player newPlayer2 = new Player(78253, "Healer", 20);
    Player newPlayer3 = new Player(78253, "Healer", 20);

    myQueue1.enqueue(newPlayer1);
    myQueue1.enqueue(newPlayer2);
    myQueue1.enqueue(newPlayer3);

    myQueue1.clear();

    assertEquals("clearTest fails", true, myQueue1.isEmpty());

  }
}
