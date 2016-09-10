package lab.aikibo.transaction.participant;

import org.jpos.transaction.TransactionParticipant;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;
import org.jpos.transaction.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import lab.aikibo.util.Constant;

public class NetworkManagementResponse implements TransactionParticipant {

  @Override
  public int prepare(long id, Serializable context) {
    try {
      ISOMsg isoMsg = (ISOMsg) ((Context) context).get(Constant.REQUEST);
      ISOSource isoSrc = (ISOSource) ((Context) context).get(Constant.ISOSOURCE);
      ISOMsg resp = (ISOMsg) isoMsg.clone();
      resp.set(7, new SimpleDateFormat("MMddHHmmss").format(new Date()));
      resp.set(39, "0");
      isoSrc.send(resp);
      return PREPARED;
    } catch(Exception e) {
      e.printStackTrace();
      return ABORTED;
    }
  }

  @Override
  public void commit(long id, Serializable context) {}

  @Override
  public void abort(long id, Serializable context) {}

}
