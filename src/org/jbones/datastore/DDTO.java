package org.jbones.datastore;

import org.jbones.core.dao.*;

/**
   Class that represents a deletable data transfer object.
   DTO's represent simple/aggregated/composed data and may
   include meta-data related to the trasfer (source/date etc).
   A typical DTO use is transfering persistant entity values.
*/
public abstract class DDTO extends DTO implements Deletable {

}

