class ListNode<T> {
    next?: ListNode<T>;

    constructor(public value: T) {}
}

class LinkedList<T> {
    private root?: ListNode<T>;
    private tail?: ListNode<T>;
    private length = 0;

    add(value: T) {
        const node = new ListNode(value);
        if (!this.root || !this.tail) {
            this.root = node;
            this.tail = node;
        } else {
            this.tail.next = node;
            this.tail = node;
        }
        this.length++;
    }

    getNumberOfElements() {
        return this.length;
    }

    print() {
        console.log(`count ${this.getNumberOfElements()}`)
        let current = this.root;
        while (current) {
            console.log(current.value);
            current = current.next;
        }
    }
}

const numberList = new LinkedList<number>();
for (let i = 0; i < 10; i++) {
    numberList.add(i);
}
numberList.print();

const nameList = new LinkedList<string>();
