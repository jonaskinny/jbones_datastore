package org.jbones.datastore;

import java.io.Serializable;
import org.jbones.core.dao.*;

/**
   Class that represents a archivable data transfer object.
   DTO's represent simple/aggregated/composed data and may
   include meta-data related to the trasfer (source/date etc).
   A typical DTO use is transfering persistant entity values.
*/
public abstract class ADTO extends DTO implements Archivable {
   // use this value to initialize the long
   // then we can check to see if the archiveid
   // is 'invalid' which means the dto is not an 
   // archived record ... if it is then we need
   // to indicate that on the ui.
   // this way we can have composite objects fetch
   // their constituent parts from archive if the
   // part is not in the entity table
   // but indicate that it is an archive.
   // some parts may have been archived whereas
   // the composite record is not archived.
   private static long INVALID_VALUE_LONG = 0L;
   private long archiveId = INVALID_VALUE_LONG;

   public long getArchiveId() {
      return archiveId;
   }
   public void setArchiveId(long archiveId) {
      this.archiveId = archiveId;
   }
   public boolean isArchive() {
      return this.archiveId > ADTO.INVALID_VALUE_LONG;
   }
}

