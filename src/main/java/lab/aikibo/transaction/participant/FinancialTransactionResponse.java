package lab.aikibo.transaction.participant;

import org.jpos.transaction.AbortParticipant;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;
import org.jpos.transaction.Context;

import lab.aikibo.util.Constant;

import java.io.Serializable;

public class FinancialTransactionResponse implements AbortParticipant {

  @Override
  public void abort(long id, Serializable context) {}

  @Override
  public void commit(long id, Serializable context) {}

  @Override
  public int prepare(long id, Serializable context) {
    try {
      ISOMsg respMsg = (ISOMsg) ((Context)context).get(Constant.RESPONSE);
      ISOSource requester = (ISOSource) ((Context) context).get(Constant.ISOSOURCE);
      requester.send(respMsg);
      return PREPARED;
    } catch(Exception e) {
      e.printStackTrace();
      return ABORTED;
    }
  }

  @Override
  public int prepareForAbort(long id, Serializable context) {
    return ABORTED;
  }

}
