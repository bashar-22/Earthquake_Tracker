package it.unipi.dstm;

import javax.ejb.Stateless;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Stateless(name = "PublisherEJB")
public class ProducerBean implements ProducerIntRemote {
    static final String QC_FACTORY_NAME = "jms/__defaultConnectionFactory";
    static final String QUEUE_NAME = "jms/myQueue2";

    Queue myQueue; //initialized in constructor
    JMSContext myJMSContext;
    JMSProducer myJMSProducer;

    public ProducerBean() {
        try{
            Context ic = new InitialContext();
            myQueue= (Queue)ic.lookup(QUEUE_NAME);
            QueueConnectionFactory qcf = (QueueConnectionFactory)ic.lookup(QC_FACTORY_NAME);
            myJMSContext = qcf.createContext();
            myJMSProducer = myJMSContext.createProducer();
        }
        catch (NamingException e) {
            System.err.println("OUTCH! PUBLISHING PROBLEMS!");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void enqueue(final String text) {
        TextMessage myMsg = myJMSContext.createTextMessage(text);
        myJMSProducer.send(myQueue,myMsg);
    }
}

