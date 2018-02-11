/**
 * Copyright Warwick Hunter 2014. All rights reserved.
 */
package java8;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Warwick Hunter (w.hunter@computer.org)
 * @date   2014-03-30
 */
public class Java8Streams {

    private static class Transaction {
        
        public enum Type { AUTOMOTIVE, PET };
        
        private final String m_id;
        private final Type   m_type;
        private final float  m_value;

        public Transaction(String id, Type type, float value) {
            m_id = id;
            m_type = type;
            m_value = value;
        }
        
        public String getId() {
            return m_id;
        }
        
        public Type getType() {
            return m_type;
        }

        public Float getValue() {
            return m_value;
        }
    }

    private final List<Transaction> m_transactions = new ArrayList<>();
    
    private void run() {
        buildTransactions();
        
        List<String> transactionIds = m_transactions.stream()
                                        .filter(txn -> txn.getType() == Transaction.Type.AUTOMOTIVE)
                                        .sorted(comparing(Transaction::getValue).reversed())
                                        .map(Transaction::getId)
                                        .collect(toList());
    }
    
    private void buildTransactions() {
        m_transactions.add(new Transaction("Dog", Transaction.Type.PET, 250.0f));
        m_transactions.add(new Transaction("Car", Transaction.Type.AUTOMOTIVE, 25000.0f));
        m_transactions.add(new Transaction("Bike", Transaction.Type.AUTOMOTIVE, 32000.0f));
    }
    
    public static void main(String... args) {
        new Java8Streams().run();
    }
}
