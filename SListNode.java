
public class SListNode {

  protected SListNode next;
  protected int item;


  SListNode(int i, SListNode n) {
    item = i;
    next = n;
  }


  public SListNode next(){
    return next;
  }

  public int item() {
      return item;
  }
}


