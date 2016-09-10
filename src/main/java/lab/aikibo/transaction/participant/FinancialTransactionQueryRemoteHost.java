package lab.aikibo.transaction.participant;

import org.jpos.transaction.AbortParticipant;
import org.jpos.util.NameRegistrar;
import org.jpos.iso.ISOMsg;
import org.jpos.transaction.Context;

import lab.aikibo.channel.ChannelManager;
import lab.aikibo.util.Constant;

import java.io.Serializable;

public class FinancialTransactionQueryRemoteHost implements AbortParticipant {

  private ChannelManager channelManager;

  @Override
  public void abort(long id, Serializable context) {}

  @Override
  public void commit(long id, Serializable context) {}

  @Override
  public int prepare(long id, Serializable context) {
    try {
      channelManager = ((ChannelManager) NameRegistrar.get("channel-manager"));
      ISOMsg reqMsg = (ISOMsg) ((Context)context).get(Constant.REQUEST);
      ISOMsg respMsg = channelManager.sendMsg(reqMsg);
      ((Context)context).put(Constant.RESPONSE, respMsg);
      return PREPARED;
    } catch(NameRegistrar.NotFoundException nfe) {
      nfe.printStackTrace();
      return ABORTED;
    } catch(Throwable t) {
      t.printStackTrace();
      return ABORTED;
    }
  }

  @Override
  public int prepareForAbort(long id, Serializable context) {
    return ABORTED;
  }

}
