/*
 * Copyright (C) 2014
 * University of Massachusetts
 * All Rights Reserved 
 *
 * Initial developer(s): Westy.
 */
package edu.umass.cs.gns.commands.group;

import static edu.umass.cs.gns.clientsupport.Defs.*;
import edu.umass.cs.gns.clientsupport.GroupAccess;
import edu.umass.cs.gns.commands.data.CommandModule;
import edu.umass.cs.gns.commands.data.GnsCommand;
import edu.umass.cs.gns.packet.NSResponseCode;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author westy
 */
public class RemoveFromGroup extends GnsCommand {

  public RemoveFromGroup(CommandModule module) {
    super(module);
  }

  @Override
  public String[] getCommandParameters() {
    return new String[]{GUID, MEMBER, WRITER, SIGNATURE, SIGNATUREFULLMESSAGE};
  }

  @Override
  public String getCommandName() {
    return REMOVEFROMGROUP;
  }

  @Override
  public String execute(JSONObject json) throws InvalidKeyException, InvalidKeySpecException,
          JSONException, NoSuchAlgorithmException, SignatureException {
    String guid = json.getString(GUID);
    String member = json.getString(MEMBER);
    // writer might be same as guid
    String writer = json.optString(WRITER, guid);
    // signature and message can be empty for unsigned cases
    String signature = json.optString(SIGNATURE, null);
    String message = json.optString(SIGNATUREFULLMESSAGE, null);
    NSResponseCode responseCode;
    if (!(responseCode = GroupAccess.removeFromGroup(guid, member, writer, signature, message)).isAnError()) {
      return OKRESPONSE;
    } else {
      return BADRESPONSE + " " + responseCode.getProtocolCode();
    }
//    GuidInfo guidInfo, writerInfo;
//    if ((guidInfo = AccountAccess.lookupGuidInfo(guid)) == null) {
//      return BADRESPONSE + " " + BADGUID + " " + guid;
//    }
//    if (writer.equals(guid)) {
//      writerInfo = guidInfo;
//    } else if ((writerInfo = AccountAccess.lookupGuidInfo(writer)) == null) {
//      return BADRESPONSE + " " + BADWRITERGUID + " " + writer;
//    }
//    if (!AccessSupport.verifySignature(writerInfo, signature, message)) {
//      return BADRESPONSE + " " + BADSIGNATURE;
//    } else if (!AccessSupport.verifyAccess(MetaDataTypeName.WRITE_WHITELIST, guidInfo, GROUP_ACL, writerInfo)) {
//      return BADRESPONSE + " " + ACCESSDENIED;
//    } else if (!GroupAccess.removeFromGroup(guid, member).isAnError()) {
//      return OKRESPONSE;
//    } else {
//      return BADRESPONSE + " " + GENERICEERROR;
//    }
  }

  @Override
  public String getCommandDescription() {
    return "Removes the member guid from the group specified by guid. Writer guid needs to have write access and sign the command.";
  }
}
