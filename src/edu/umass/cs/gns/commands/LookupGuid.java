/*
 * Copyright (C) 2014
 * University of Massachusetts
 * All Rights Reserved 
 *
 * Initial developer(s): Westy.
 */
package edu.umass.cs.gns.commands;

import static edu.umass.cs.gns.clientprotocol.Defs.*;
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
public class LookupGuid extends GnsCommand {

  public LookupGuid(CommandModule module) {
    super(module);
  }

  @Override
  public String[] getCommandParameters() {
    return new String[]{NAME};
  }

  @Override
  public String getCommandName() {
    return LOOKUPGUID;
  }

  @Override
  public String execute(JSONObject json) throws JSONException {
    String name = json.getString(NAME);
    // look for an account guid
    String result = accountAccess.lookupGuid(name);
    if (result != null) {
      return result;
    } else {
      return BADRESPONSE + " " + BADACCOUNT;
    }
  }

  @Override
  public String getCommandDescription() {
    return "Returns the guid associated with for the human readable name. "
            + "Returns " + BADACCOUNT + " if the GUID has not been registered.";
  }
}
