package lab.aikibo.transaction.participant;

import org.jpos.transaction.TransactionParticipant;
import org.jpos.iso.ISOSource;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOException;
import org.jpos.transaction.Context;

import java.io.IOException;
import java.io.Serializable;

import lab.aikibo.util.Constant;

public class FinancialTransactionValidateMessage implements TransactionParticipant {

  @Override
  public void abort(long id, Serializable context) {
    try {
      ISOSource requester = (ISOSource) ((Context)context).get(Constant.ISOSOURCE);
      ISOMsg reqMsg = (ISOMsg) ((Context) context).get(Constant.REQUEST);
      ISOMsg respMsg = (ISOMsg) reqMsg.clone();
      respMsg.setResponseMTI();
      respMsg.set(39,"01");
      requester.send(respMsg);
    } catch(ISOException isoe) {
      isoe.printStackTrace();
    } catch(IOException ioe) {
      ioe.printStackTrace(); 
    }
  }

  @Override
  public void commit(long id, Serializable context) {}

  @Override
  public int prepare(long id, Serializable context) {
    try {
      ISOMsg reqMsg = (ISOMsg) ((Context)context).get(Constant.REQUEST);
      String bit124 = reqMsg.getString(124);
      String custId = bit124.substring(0,5).trim();
      if(custId != null && custId.length() > 0) {
        return PREPARED;
      }
    } catch(Exception e) {
      return ABORTED;
    }
    return ABORTED;
  }

}
