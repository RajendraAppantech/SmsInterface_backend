package com.sms;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateSerializer extends StdSerializer<Date> {
   private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

   public DateSerializer() {
      this((Class)null);
   }

   public DateSerializer(Class<Date> t) {
      super(t);
   }

   public void serialize(Date value, JsonGenerator gen, SerializerProvider provider) throws IOException {
      gen.writeString(dateFormat.format(value));
   }
}
