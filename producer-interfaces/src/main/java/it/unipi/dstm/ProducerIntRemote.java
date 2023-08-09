package it.unipi.dstm;

import javax.ejb.Remote;

@Remote
public interface ProducerIntRemote {

    public void enqueue(final String text);
}
