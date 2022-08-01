package medium.P002AddListNums;

public class AddTwoNums {
    /**
     * Definition for singly-linked list.
     */
    private static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode( int val ) {
            this.val = val;
        }

        ListNode( int val, ListNode next ) {
            this.val = val;
            this.next = next;
        }
    }

    public static ListNode addTwoNumbersV2( ListNode l1, ListNode l2 ) {
        ListNode dummy = new ListNode(0); // dummy head to form the result list
        ListNode next = dummy;

        int sum = 0;

        while (l1 != null || l2 != null || sum != 0) {
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }

            next.next = new ListNode(sum % 10);
            sum = sum < 10 ? 0 : 1;
            next = next.next;
        }

        return dummy.next;
    }

    public static ListNode addTwoNumbers( ListNode l1, ListNode l2 ) {
        ListNode res = new ListNode();
        ListNode next = res;

        while (l1 != null || l2 != null) {
            int sum = ( l1 == null ? 0 : l1.val ) + ( l2 == null ? 0 : l2.val ) + next.val;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;

            if (sum < 10) {
                next.val = sum;
                if (l1 != null || l2 != null) next.next = new ListNode();
            } else {
                next.val = sum % 10;
                next.next = new ListNode(1);
            }

            next = next.next;
        }

        return res;
    }

    public static void printList( ListNode node ) {
        StringBuilder sb = new StringBuilder();

        sb.append(node.val);
        ListNode next = node.next;
        while (next != null) {
            sb.append(" -> ");
            sb.append(next.val);
            next = next.next;
        }

        System.out.println(sb.toString());
    }

    public static void main( String[] args ) {
        System.out.println("Welcome to the rabbit hole of numbers represented in lists.");

        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        ListNode l3 = new ListNode(9);
        l3.next = new ListNode(9);
        l3.next.next = new ListNode(9);
        l3.next.next.next = new ListNode(9);

        ListNode l4 = new ListNode(9);
        l4.next = new ListNode(9);
        l4.next.next = new ListNode(9);

        System.out.println("# Test case 1");
        System.out.print("Number one: ");
        printList(l1);
        System.out.print("Number two: ");
        printList(l2);
        System.out.println("Result: ");
        printList(addTwoNumbers(l1, l2));

        System.out.println("# Test case 2");
        System.out.print("Number one: ");
        printList(l3);
        System.out.print("Number two: ");
        printList(l4);
        System.out.println("Result: ");
        printList(addTwoNumbersV2(l3, l4));

        System.out.println("All rabbits gone.");
    }

}
