package edu.umass.cs.gns.paxos;

import edu.umass.cs.gns.nio.InterfaceJSONNIOTransport;
import edu.umass.cs.gns.nsdesign.Replicable;
import edu.umass.cs.gns.paxos.paxospacket.RequestPacket;
import org.json.JSONException;

import java.io.IOException;

/**
 * We use this paxos interface object while running tests for paxos module.
 *
 * The main task of this module is to send response to client node after a request is executed.
 *
 
 * User: abhigyan
 * Date: 6/29/13
 * Time: 8:57 PM
 * @param <NodeIDType>
 */
public class DefaultPaxosInterface<NodeIDType> implements Replicable {
  
  // NOTE: We have hardcoded that nodeID = 0 will respond to client. Therefore, in our tests
  // nodeID = 0 must be a paxos replica and it must not be crashed.
  // THIS WILL BREAK, BTW... GUARANTEED.
  private static final String RESPONDING_NODE = "0";

  /**
   *
   */
  NodeIDType nodeID;

  /**
   * Transport object. It is needed to send responses to client.
   */
  InterfaceJSONNIOTransport<NodeIDType> nioServer;


  public DefaultPaxosInterface(NodeIDType nodeID, InterfaceJSONNIOTransport<NodeIDType> nioServer) {
    this.nodeID = nodeID;
    this.nioServer = nioServer;
  }

//  @Override
  public void handlePaxosDecision(String paxosID, RequestPacket<NodeIDType> requestPacket, boolean recovery) {
    // check
    if (nodeID.equals(RESPONDING_NODE))
      try {
        nioServer.sendToID(requestPacket.clientID, requestPacket.toJSONObject());
      } catch (JSONException e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      } catch (IOException e) {
        e.printStackTrace();
      }
  }


  @Override
  public boolean handleDecision(String paxosID, String value, boolean recovery) {
    // check
    // TODO fixme
//    if (nodeID == 0)
//      try {
//        nioServer.sendToID(requestPacket.clientID, requestPacket.toJSONObject());
//      } catch (JSONException e) {
//        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
	  return true;
  }

  @Override
  public String getState(String paxosID) {
    return "ABCD\nEFGH\nIJKL\nMNOP\n";
  }

  @Override
  public boolean updateState(String paxosID, String state) {
    // empty method becasue this only for running paxos tests independently
    return false;
  }

}
